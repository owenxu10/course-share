package org.tjsse.courseshare.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tjsse.courseshare.util.Config;

@Controller
@RequestMapping("/config")
public class ConfigController {

  @RequestMapping(value = "/set", method = RequestMethod.GET)
  @ResponseBody
  public String setConfig(HttpServletRequest req) {
    String url = (String) req.getParameter("url");
    if (url != null && !url.isEmpty()) {
      Config.SERVER_URL = url;
      return "'url' is now set to " + url;
    }
    return "Nothing changed!";
  }
}
