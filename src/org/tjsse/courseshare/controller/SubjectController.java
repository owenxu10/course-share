package org.tjsse.courseshare.controller;

import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.tjsse.courseshare.bean.Subject;
import org.tjsse.courseshare.service.SubjectService;
import org.tjsse.courseshare.util.Config;

@Controller
@RequestMapping("/subject")
public class SubjectController {

  @Autowired
  private SubjectService subjectService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public ModelAndView index() {
    return new ModelAndView("subject", "pageTitle", "专题库");
  }

  @RequestMapping(value = "/import", method = RequestMethod.GET)
  @ResponseBody
  public String importSubjects() {
    String path1 = Config.ROOT_PATH + "subject1.txt";
    String path2 = Config.ROOT_PATH + "subject2.txt";
    String path3 = Config.ROOT_PATH + "subject3.txt";
    int count = subjectService.importSubject(path1);
    count += subjectService.importSubject(path2);
    count += subjectService.importSubject(path3);
    return count + " subjects are imported.";
  }

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseBody
  public List<Subject> listSubjects(
      @RequestParam(value = "subject_content", required = false) String subjectContent) {
    System.out.println("content:" + subjectContent);
    String[] contents = null;
    if (subjectContent != null) {
      StringTokenizer st = new StringTokenizer(subjectContent, " ");
      contents = new String[st.countTokens()];
      for (int i = 0; i < st.countTokens(); i++) {
        contents[i] = st.nextToken().trim();
      }
    }
    return subjectService.findSubjects(contents);
  }

}
