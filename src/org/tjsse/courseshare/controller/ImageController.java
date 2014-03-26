package org.tjsse.courseshare.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import org.tjsse.courseshare.bean.Image;
import org.tjsse.courseshare.service.ImageService;
import org.tjsse.courseshare.util.Config;

@Controller
@RequestMapping("/image")
public class ImageController {

  @Autowired
  private ImageService imageService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public ModelAndView index() {
    return new ModelAndView("image", "pageTitle", "素材库");
  }

  @RequestMapping(value = "/import", method = RequestMethod.GET)
  @ResponseBody
  public String importImages() {
    String path = Config.ROOT_PATH + "image.txt";
    int count = imageService.importImages(path);
    return count + " images are imported.";
  }

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseBody
  public List<Image> listImages(
      @RequestParam(value = "image_content", required = false) String imageContent) {
    String[] contents = null;
    if (imageContent != null) {
      StringTokenizer st = new StringTokenizer(imageContent, " ");
      contents = new String[st.countTokens()];
      for (int i = 0; i < st.countTokens(); i++) {
        contents[i] = st.nextToken().trim();
      }
    }
    return imageService.findImages(contents);
  }

  @RequestMapping(value = "/read/{imageNo}", method = RequestMethod.GET)
  @ResponseBody
  public void readImage(@PathVariable String imageNo, HttpServletResponse resp) {
    byte[] data = imageService.readImage(imageNo);
    if (data == null) {
      return;
    }
    resp.setContentType("image/jpeg");
    try {
      OutputStream os = resp.getOutputStream();
      os.write(data);
      os.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
