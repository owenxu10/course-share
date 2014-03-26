package org.tjsse.courseshare.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.tjsse.courseshare.bean.Flash;
import org.tjsse.courseshare.service.FlashService;
import org.tjsse.courseshare.util.Config;

@Controller
@RequestMapping("/flash")
public class FlashController {

  @Autowired
  private FlashService flashService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public ModelAndView index() {
    return new ModelAndView("flash", "pageTitle", "动画库");
  }

  @RequestMapping(value = "/import", method = RequestMethod.GET)
  @ResponseBody
  public String importFlashes() {
    String path = Config.ROOT_PATH + "flash.txt";
    int count = flashService.importFlashes(path);
    return count + " flashes are imported.";
  }

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseBody
  public List<Flash> listFlashes(
      @RequestParam(value = "flash_content", required = false) String flashContent) {
    String[] contents = null;
    if (flashContent != null) {
      StringTokenizer st = new StringTokenizer(flashContent, " ");
      contents = new String[st.countTokens()];
      for (int i = 0; i < st.countTokens(); i++) {
        contents[i] = st.nextToken().trim();
      }
    }
    return flashService.findFlashes(contents);
  }

  @RequestMapping(value = "/read/{flashNo}", method = RequestMethod.GET)
  @ResponseBody
  public void readFlash(@PathVariable String flashNo, HttpServletResponse resp) {
    byte[] data = flashService.readFlash(flashNo);
    if (data == null) {
      return;
    }
    resp.setContentType("application/x-shockwave-flash");
    resp.setContentLength(data.length);
    resp.setHeader("cache-control", "must-revalidate");
    try {
      OutputStream os = resp.getOutputStream();
      os.write(data);
      os.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
