package org.tjsse.courseshare.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.tjsse.courseshare.bean.DSPicture;
import org.tjsse.courseshare.bean.Problem;
import org.tjsse.courseshare.service.ProblemsetService;
import org.tjsse.courseshare.util.Config;
import org.tjsse.courseshare.util.LibType;

@Controller
@RequestMapping("/problemset")
public class ProblemsetController {

  @Autowired
  private ProblemsetService problemsetService;

  private static final Map<String, String> TYPES = new HashMap<String, String>();
  static {
    TYPES.put("concept", "概念题");
    TYPES.put("blankfill", "填空题");
    TYPES.put("choice", "选择题");
    TYPES.put("question", "问答题");
    TYPES.put("integrate", "综合题");
  }

  public static final String PROBLEM_PATH = Config.PROBLEM_PATH;

  /* 
   * Action: '/index', Method: GET
   * Default index page.
   */
  @RequestMapping(value = "", method = RequestMethod.GET)
  public ModelAndView index() {
    List<Problem> problems = problemsetService.findProblems(null, null, null, null, 0);
    if (problems == null) {
      problems = new ArrayList<Problem>();
    }
    ModelMap map = new ModelMap();
    map.addAttribute("problems", problems);
    map.addAttribute("libType", LibType.PROBLEMSET);
    return new ModelAndView("problemset", map);
  }

  /* 
   * Action: '/import', Method: GET
   * Import problems from ms word.
   */
  @RequestMapping(value = "/import/{problems}", method = RequestMethod.GET)
  @ResponseBody
  public String importProblems(@PathVariable String problems) {
    if (problems == null) {
      return "0 problems are imported.";
    }
    StringTokenizer st = new StringTokenizer(problems, ",");
    int count = 0;
    while (st.hasMoreTokens()) {
      String p = st.nextToken().trim();
      if (p.isEmpty()) {
        continue;
      }
      String docPath = String.format("%s/%s.doc", PROBLEM_PATH, p);
      String htmlPath = String.format("%s/%s.html", PROBLEM_PATH, p);
      if (!problemsetService.convertDoc2Html(docPath, htmlPath)) {
        continue;
      }
      int c = problemsetService.splitProblem(htmlPath);
      if (c > 0) {
        count += c;
      }
      new File(htmlPath).delete();
    }
    return count + " problems are imported";
  }
  
  /*
   * Action: '/clear', Method: GET
   * Clear all problem records in DB and files on disk.
   */
  @RequestMapping(value="/clear", method = RequestMethod.GET)
  @ResponseBody
  public String clearProblems() {
    problemsetService.removeAll();
    return "All problems are cleared!";
  }

  /*
   * Action: '/list', Method: GET
   * List all problems under certain condition.
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseBody
  public List<Problem> listProblems(
      @RequestParam(value = "problem_type", required = false) String problemType,
      @RequestParam(value = "difficulty", required = false) String difficulty,
      @RequestParam(value = "problem_content", required = false) String problemContent,
      @RequestParam(value = "knowledge", required = false) String knowledge,
      @RequestParam(value = "offset", required = false) Integer offset) {

    System.out.println("type:" + problemType);
    System.out.println("difficulty:" + difficulty);
    System.out.println("content:" + problemContent);
    System.out.println("knowledge:" + knowledge);
    System.out.println("offset:" + offset);

    // Get problems for specific types
    List<String> types = new ArrayList<String>();
    String[] pts = null;
    if (problemType != null) {
      StringTokenizer st = new StringTokenizer(problemType, ",");
      while (st.hasMoreTokens()) {
        String t = TYPES.get(st.nextToken().trim());
        if (!t.isEmpty())
          types.add(t);
      }
      if (!types.isEmpty())
        pts = types.toArray(new String[types.size()]);
    }

    // Get problems for specific difficulty
    List<Integer> diffs = new ArrayList<Integer>();
    Integer[] pds = null;
    if (difficulty != null) {
      StringTokenizer st = new StringTokenizer(difficulty, ",");
      while (st.hasMoreTokens()) {
        Integer d = Integer.parseInt(st.nextToken().trim());
        diffs.add(d);
      }
      if (!diffs.isEmpty())
        pds = diffs.toArray(new Integer[diffs.size()]);
    }

    // Get problems for specific contents
    List<String> contents = new ArrayList<String>();
    String[] pcs = null;
    if (problemContent != null) {
      StringTokenizer st = new StringTokenizer(problemContent, ",");
      while (st.hasMoreTokens()) {
        String c = st.nextToken().trim();
        if (!c.isEmpty())
          contents.add(c);
      }
      if (!contents.isEmpty())
        pcs = contents.toArray(new String[contents.size()]);
    }

    // Get problems for specific knowledge
    List<String> knows = new ArrayList<String>();
    String[] pks = null;
    if (knowledge != null) {
      StringTokenizer st = new StringTokenizer(knowledge, ",");
      while (st.hasMoreTokens()) {
        String k = st.nextToken().trim();
        if (!k.isEmpty())
          knows.add(k);
      }
      if (!knows.isEmpty())
        pks = knows.toArray(new String[knows.size()]);
    }

    if (offset == null)
      offset = 0;

    return problemsetService.findProblems(pts, pds, pcs, pks, offset);
  }

  /*
   * Action: '/resource', Method: GET
   * Read problem resources from DB and disk.
   */
  @RequestMapping(value = "/resource/{rid}", method = RequestMethod.GET)
  public void picture(@PathVariable Integer rid, HttpServletResponse resp) {
    DSPicture pic = problemsetService.readPicture(rid);
    resp.setContentType("image/" + pic.getMediaType());
    try {
      OutputStream os = resp.getOutputStream();
      os.write(pic.getContent());
      os.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /*
   * Action: '/paper', Method: GET
   * Make paper from selected problems
   */
  @RequestMapping(value = "/paper", method = RequestMethod.GET)
  public void paper(@RequestParam(value = "pids") String pids,
      HttpServletResponse resp) {
    List<Integer> tmp = new ArrayList<Integer>();
    StringTokenizer st = new StringTokenizer(pids, ",");
    Integer[] problemIds = null;
    while (st.hasMoreTokens()) {
      Integer p = Integer.parseInt(st.nextToken().trim());
      tmp.add(p);
    }
    if (tmp.isEmpty()) {
      return;
    }
    problemIds = tmp.toArray(new Integer[tmp.size()]);
    byte[] data = problemsetService.makePaper(problemIds);
    resp.setHeader("Content-disposition", "attachment;filename=paper.doc");
    resp.setContentType("application/msword");
    resp.setContentLength(data.length);
    try {
      OutputStream os = resp.getOutputStream();
      os.write(data);
      os.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
