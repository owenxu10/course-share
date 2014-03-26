package org.tjsse.courseshare.service.impl;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tjsse.courseshare.bean.DSPicture;
import org.tjsse.courseshare.bean.Problem;
import org.tjsse.courseshare.bean.ProblemResource;
import org.tjsse.courseshare.dao.ProblemDao;
import org.tjsse.courseshare.dao.ProblemResourceDao;
import org.tjsse.courseshare.service.ProblemsetService;
import org.tjsse.courseshare.util.Config;
import org.tjsse.courseshare.util.ProblemType;
import org.w3c.dom.Document;

@Service
public class ImplProblemsetService implements ProblemsetService {

  // public static final String ROOT_PATH = Config.ROOT_PATH;
  public static final String PRES_PATH = Config.PROBLEM_RES_PATH;
  public static final String PRES_URL = Config.ROOT_URI
      + "/problemset/resource/";

  public static final String WORD_ENCODING = "utf-8";
  public static final String HTML_ENCODING = "utf-8";
  public static final String COMMENT_SYMBOL = "//";
  public static final String PROBLEM_TYPE_SYMBOL = "$";
  public static final String DIFFICULTY_SYMBOL = "&";
  public static final String PROBLEM_CONTENT_SYMBOL = "@1";
  public static final String KEY_CONTENT_SYMBOL = "@2";
  public static final String KNOWLEDGE_SYMBOL = "@3";

  public static final String TITLE = "计算机系统结构试卷";

  @Autowired
  private ProblemResourceDao problemResourceDao;
  @Autowired
  private ProblemDao problemDao;

  /**
   * Data structure for problem detail.
   * 
   * @return Problem object.
   */
  private class ProblemInfo {

    public String currSymbol = COMMENT_SYMBOL;
    public String problemType = "";
    public int difficulty = 0;
    public StringBuffer problemContent = new StringBuffer();
    public StringBuffer keyContent = new StringBuffer();
    public String knowledge = "";

    public Problem toProblem() {
      if (problemContent.length() == 0)
        return null;
      Problem problem = new Problem();
      problem.setDifficulty(difficulty);
      problem.setProblemType(problemType);
      problem.setProblemContent(problemContent.toString());
      problem.setKeyContent(keyContent.toString());
      problem.setKnowledge(knowledge);
      return problem;
    }
  }

  private boolean writeFile(byte[] content, String path) {
    FileOutputStream fos = null;
    boolean success = true;
    try {
      fos = new FileOutputStream(new File(path));
      fos.write(content);
    } catch (IOException e) {
      success = false;
    } finally {
      try {
        if (fos != null) {
          fos.close();
        }
      } catch (IOException e) {
        return false;
      }
    }
    return success;
  }

  private boolean writeFile(String content, String path, String encoding) {
    FileOutputStream fos = null;
    OutputStreamWriter osw = null;
    boolean success = true;
    try {
      fos = new FileOutputStream(path);
      osw = new OutputStreamWriter(fos, encoding);
      osw.write(content);
    } catch (IOException e) {
      e.printStackTrace();
      success = false;
    } finally {
      try {
        if (osw != null)
          osw.close();
        if (fos != null)
          fos.close();
      } catch (IOException ie) {
        return false;
      }
    }
    return success;
  }


  /**
   * Convert doc file to HTML file.
   * 
   * @param docPath
   *          Full path to doc file.
   * @param htmlPath
   *          Full path to HTML file.
   * @return True if convert successfully.
   */
  @Override
  public boolean convertDoc2Html(String docPath, String htmlPath) {

    // Initialize POI HWPF to do conversion.

    HWPFDocument wordDoc = null;
    WordToHtmlConverter converter = null;
    try {
      wordDoc = new HWPFDocument(new FileInputStream(docPath));
      converter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance()
          .newDocumentBuilder().newDocument());
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    // Set PicturesManager to save pictures in DB and FS.

    converter.setPicturesManager(new PicturesManager() {
      @Override
      public String savePicture(byte[] content, PictureType pictureType,
          String suggestedName, float widthInches, float heightInches) {
        ProblemResource pr = new ProblemResource();
        pr.setType(pictureType.getExtension().toLowerCase());
        pr.setUri(PRES_PATH);
        pr = problemResourceDao.save(pr);
        if (pr == null) {
          return "";
        }
        // Absolute path of problem resources on disk.
        String presPath = String.format("%s/%d.%s", PRES_PATH, pr.getId(),
            pr.getType());
        // Web URI to access problem resources.
        String presUrl = String.format("%s%d", PRES_URL, pr.getId());
        return writeFile(content, presPath) ? presUrl : "";
      }
    });
    converter.processDocument(wordDoc);

    // Generate HTML file.

    Document htmlDoc = converter.getDocument();
    DOMSource domSource = new DOMSource(htmlDoc);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    StreamResult streamResult = new StreamResult(out);
    try {
      Transformer serializer = TransformerFactory.newInstance()
          .newTransformer();
      serializer.setOutputProperty(OutputKeys.ENCODING, WORD_ENCODING);
      serializer.setOutputProperty(OutputKeys.INDENT, "yes");
      serializer.setOutputProperty(OutputKeys.METHOD, "html");
      serializer.transform(domSource, streamResult);
      writeFile(out.toString(WORD_ENCODING), htmlPath, HTML_ENCODING);
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Split HTML file into several pieces.
   * 
   * @param htmlPath
   *          Full path to html file.
   * @return Number of splitted problems or -1 means failed.
   */
  @Override
  public int splitProblem(String htmlPath) {
    org.jsoup.nodes.Document html = null;
    try {
      html = Jsoup.parse(new File(htmlPath), HTML_ENCODING);
    } catch (IOException e) {
      return -1;
    }
    mergeCss(html);
    Element body = html.getElementsByTag("body").first();
    ProblemInfo pi = new ProblemInfo();
    int count = 0;

    // Traverse each line of doc file
    for (Element e : body.children()) {

      // Save element directly if not p
      if (!e.tagName().equals("p")) {
        saveElement(e, pi);
        continue;
      }

      // Skip empty line
      if (e.childNodeSize() == 0) {
        continue;
      }

      // Save element if first child is not span
      Element span = e.child(0);
      if (!span.tagName().equals("span")) {
        saveElement(e, pi);
        continue;
      }

      // Process for different symbols
      String text = extractText(e);
      // System.out.println(text);
      if (text.isEmpty() || text.startsWith(COMMENT_SYMBOL)) {
        continue;
      }
      switch (text) {
      case PROBLEM_TYPE_SYMBOL:
      case DIFFICULTY_SYMBOL:
      case PROBLEM_CONTENT_SYMBOL:
        if (pi.problemContent.length() != 0) {
          count += problemDao.save(pi.toProblem()) == null ? 0 : 1;
        }
        pi.problemContent = new StringBuffer();
        pi.keyContent = new StringBuffer();
        pi.currSymbol = text;
        break;
      case KEY_CONTENT_SYMBOL:
      case KNOWLEDGE_SYMBOL:
        pi.currSymbol = text;
        break;
      default:
        saveElement(e, pi);
      }
    }
    if (pi.problemContent.length() != 0) {
      count += problemDao.save(pi.toProblem()) == null ? 0 : 1;
    }
    return count;
  }

  // @Override
  // public List<Problem> findProblems() {
  // List<Problem> problems = problemDao.find();
  // return problems;
  // }

  // @Override
  // public List<Problem> findProblems(String[] contents) {
  // if (contents == null || contents.length == 0) {
  // return findProblems();
  // }
  // StringBuffer condition = new StringBuffer();
  // for (int i = 0; i < contents.length; i++) {
  // if (contents[i] == null || contents[i].isEmpty())
  // continue;
  // if (condition.length() != 0) {
  // condition.append(" AND ");
  // }
  // condition.append(String.format(
  // "(problem_content LIKE '%%%s%%' OR knowledge LIKE '%%%s%%')",
  // contents[i], contents[i]));
  // }
  // return problemDao.find(condition.toString());
  // }

  @Override
  public List<Problem> findProblems(String[] types, Integer[] diffs,
      String[] contents, String[] knows, int offset) {

    StringBuffer typeCondition = new StringBuffer();
    if (types != null && types.length > 0) {
      for (String t : types) {
        if (t == null || t.isEmpty())
          continue;
        if (typeCondition.length() > 0)
          typeCondition.append(" OR ");
        typeCondition.append(String.format("(problem_type='%s')", t));
      }
    }

    StringBuffer diffCondition = new StringBuffer();
    if (diffs != null && diffs.length > 0) {
      for (Integer d : diffs) {
        if (d == null)
          continue;
        if (diffCondition.length() > 0)
          diffCondition.append(" OR ");
        diffCondition.append(String.format("(difficulty=%d)", d));
      }
    }

    StringBuffer contentCondition = new StringBuffer();
    if (contents != null && contents.length > 0) {
      for (String c : contents) {
        if (c == null || c.isEmpty())
          continue;
        if (contentCondition.length() > 0) {
          contentCondition.append(" AND ");
        }
        contentCondition
            .append(String.format(
                "(problem_content LIKE '%%%s%%' OR knowledge LIKE '%%%s%%')",
                c, c));
      }
    }

    StringBuffer knowCondition = new StringBuffer();
    if (knows != null && knows.length > 0) {
      for (String k : knows) {
        if (k == null || k.isEmpty())
          continue;
        if (knowCondition.length() != 0) {
          knowCondition.append(" AND ");
        }
        knowCondition.append(String.format("(knowledge LIKE '%%%s%%')", k));
      }
    }

    StringBuffer condition = new StringBuffer();
    if (typeCondition.length() > 0)
      condition.append("(" + typeCondition + ")AND");
    if (diffCondition.length() > 0)
      condition.append("(" + diffCondition + ")AND");
    if (contentCondition.length() > 0)
      condition.append("(" + contentCondition + ")AND");
    if (knowCondition.length() > 0)
      condition.append("(" + knowCondition + ")AND");
    String cond = condition.toString();
    if (cond.endsWith("AND"))
      cond = cond.substring(0, condition.lastIndexOf("AND"));
    return problemDao.find(cond, offset);
  }

  // @Override
  // public List<Problem> findProblemsByTypes(String[] types) {
  // if (types == null || types.length == 0) {
  // return findProblems();
  // }
  // StringBuffer condition = new StringBuffer();
  // for (int i = 0; i < types.length; i++) {
  // if (condition.length() != 0)
  // condition.append(" OR ");
  // condition.append(String.format("problem_type='%s'", types[i]));
  // }
  // return problemDao.find(condition.toString());
  // }

  /**
   * Read picture file on disk.
   * 
   * @param prId
   *          Id for ProblemResource object
   * @return Byte array for picture content
   */
  @Override
  public DSPicture readPicture(int prId) {
    ProblemResource pr = problemResourceDao.read(prId);
    if (pr == null) {
      return null;
    }
    String path = String.format("%s/%d.%s", pr.getUri(), pr.getId(),
        pr.getType());
    File pic = new File(path);
    if (!pic.exists()) {
      return null;
    }
    byte[] data = null;
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(pic);
      data = new byte[(int) pic.length()];
      fis.read(data);
    } catch (IOException e) {
    } finally {
      try {
        if (fis != null)
          fis.close();
      } catch (IOException e) {
        return null;
      }
    }
    DSPicture result = new DSPicture();
    result.setMediaType(pr.getType());
    result.setContent(data);
    return result;
  }

  /**
   * Merge CSS inside style tag into specific elements.
   * 
   * @param html
   *          Document object of full html.
   */
  private void mergeCss(org.jsoup.nodes.Document html) {
    Element style = html.select("style").first();
    String css = style.data().replaceAll("\n", "").trim();
    StringTokenizer st = new StringTokenizer(css, "{}");
    while (st.countTokens() > 1) {
      String selector = st.nextToken();
      String property = st.nextToken();
      Elements elements = html.select(selector);
      for (Element e : elements) {
        e.attr("style", property);
      }
    }
  }

  /**
   * Save element html of current problem to ProblemInfo structure.
   * 
   * @param element
   *          Element object.
   * @param pi
   *          ProblemInfo structure object.
   */
  private void saveElement(Element element, ProblemInfo pi) {
    if (element == null || pi == null || pi.currSymbol == null)
      return;
    switch (pi.currSymbol) {
    case PROBLEM_TYPE_SYMBOL:
      pi.problemType = extractText(element);
      break;
    case DIFFICULTY_SYMBOL:
      pi.difficulty = Integer.parseInt(extractText(element));
      break;
    case PROBLEM_CONTENT_SYMBOL:
      if (pi.problemContent == null)
        pi.problemContent = new StringBuffer();
      pi.problemContent.append(element.outerHtml());
      break;
    case KEY_CONTENT_SYMBOL:
      if (pi.keyContent == null)
        pi.keyContent = new StringBuffer();
      pi.keyContent.append(element.outerHtml());
      break;
    case KNOWLEDGE_SYMBOL:
      pi.knowledge = extractText(element);
      break;
    }
  }

  /**
   * Extract all text of specific element.
   * 
   * @param element
   * @return
   */
  private String extractText(Element element) {
    Elements elements = element.children();
    StringBuffer sb = new StringBuffer();
    for (Element e : elements) {
      sb.append(e.text().trim());
    }
    return sb.toString();
  }

  @Override
  public byte[] makePaper(Integer[] pids) {
    List<Problem> problems = problemDao.read(pids);
    String content = makeHtml(problems);
    ByteArrayInputStream bais = null;
    try {
      bais = new ByteArrayInputStream(content.getBytes("unicode"));
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
      return null;
    }

    String path = String.format("%s/%d.doc", Config.TMP_PATH, System.currentTimeMillis());
    FileOutputStream fos = null;
    try {
      POIFSFileSystem poifs = new POIFSFileSystem();
      DirectoryEntry directory = poifs.getRoot();
      DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
      fos = new FileOutputStream(path);
      poifs.writeFilesystem(fos);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (fos != null)
          fos.close();
        if (bais != null)
          bais.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    File doc = new File(path);
    if (!doc.exists()) {
      return null;
    }
    byte[] data = null;
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(doc);
      data = new byte[(int) doc.length()];
      fis.read(data);
    } catch (IOException e) {
    } finally {
      try {
        if (fis != null)
          fis.close();
      } catch (IOException e) {
        return null;
      }
    }
    return data;
  }

  /**
   * Generate list of problems into HTML.
   * 
   * @param problems
   *          List of Problem object
   * @return HTML string.
   */
  private String makeHtml(List<Problem> problems) {

    // Group problems into a map.

    Map<String, List<Problem>> sortedProblems = new HashMap<String, List<Problem>>();
    for (Problem p : problems) {
      if (!sortedProblems.containsKey(p.getProblemType())) {
        sortedProblems.put(p.getProblemType(), new ArrayList<Problem>());
      }
      sortedProblems.get(p.getProblemType()).add(p);
    }
    String[] problemTypes = { ProblemType.CONCEPT.getName(),
        ProblemType.BLANKFILL.getName(), ProblemType.CHOICE.getName(),
        ProblemType.QUESTION.getName(), ProblemType.INTEGRATE.getName() };
    StringBuffer content = new StringBuffer("<html><body>");
    content.append("<h2>" + TITLE + "</h2>");

    // Traverse each type of problems

    for (String type : problemTypes) {
      List<Problem> plist = sortedProblems.get(type);
      if (plist == null || plist.size() <= 0)
        continue;
      content.append("<h3>" + type + "</h3>");
      for (int i = 0; i < plist.size(); i++) {
        String pcontent = plist.get(i).getProblemContent();

        // Prepend order number of problem.

        org.jsoup.nodes.Document doc = Jsoup.parse(pcontent);
        Elements eles = doc.select("body").get(0).children();
        Element node = new Element(Tag.valueOf("span"), "");
        node.text((i + 1) + ". ");
        eles.get(0).prependChild(node);

        // Replace image path to local absolute path.

        Elements imgs = eles.select("img");
        for (Element img : imgs) {
          String oldPath = img.attr("src");
//          String[] tmp = oldPath.split("/");
//          if (tmp == null || tmp.length <= 0) {
//            continue;
//          }
//          Integer prid = Integer.parseInt(tmp[tmp.length - 1]);
//          ProblemResource pr = problemResourceDao.read(prid);
//          String imgPath = String.format("%s/%d.%s", pr.getUri(), pr.getId(), pr.getType());
          System.out.println(Config.SERVER_URL + oldPath);
          img.attr("src", Config.SERVER_URL + oldPath);
        }

        content.append(eles.toString());
      }
      content.append("<br>");
    }

    content.append("</body></html>");
    return content.toString();
  }

  @Override
  public void removeAll() {
    problemDao.clear();
    problemResourceDao.clear();
    File dir = new File(PRES_PATH);
    if (!dir.exists() || !dir.isDirectory()) {
      return;
    }
    File[] files = dir.listFiles();
    for(File f : files) {
      f.delete();
    }
  }

}