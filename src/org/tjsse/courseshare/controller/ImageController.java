package org.tjsse.courseshare.controller;

import java.io.File;
import java.awt.Image;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.tjsse.courseshare.bean.Resource;
import org.tjsse.courseshare.service.ImageService;
import org.tjsse.courseshare.util.Config;
import org.tjsse.courseshare.util.LibType;

@Controller
@RequestMapping("/image")
public class ImageController {

  @Autowired
  private ImageService imageService;

  /* 
   * Action: '/index', Method: GET
   * Default index page.
   */
  @RequestMapping(value = "", method = RequestMethod.GET)
  public ModelAndView index(HttpServletRequest request) { 
    ModelMap imageMap = new ModelMap();
    imageMap.addAttribute("libType", LibType.RESOURCE);
    System.out.println("in /image");
    return new ModelAndView("image", imageMap);
  }
 
  /* 
   * Action: '/search', Method: POST
   * Search index page.
   */
  @RequestMapping(value = "/search", method = RequestMethod.POST)
  public String search(@RequestParam("searchKeyWord") String searchKeyWord,HttpServletRequest request) { 
	 
	  System.out.println("in /search");
	  
	  String[] str ={searchKeyWord} ;
	  //get the list of result from the service;
	 List<Resource> images = imageService.findImages(str);
	    if (images == null) {
	    	images = new ArrayList<Resource>();
	    }
	  
	  for (Resource p : images) {
		  System.out.println(p.getId());
	    }
	  request.getSession().setAttribute("keyword", searchKeyWord);
	  request.getSession().setAttribute("images", images);
	  return "redirect:/image"; //done
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
  public List<Resource> listImages(
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
  
  /* 
   * Action: '/upload', Method: POST
   * Upload problems from web page.
   */
  @RequestMapping(value="/uploadimage", method=RequestMethod.POST)
  public @ResponseBody boolean uploadImage( @RequestParam("resourceName") String resourceName,
											@RequestParam("resourceknowledge") String resourceknowledge,
											@RequestParam("resourceType") String resourceType,
								            @RequestParam("resourceImage") MultipartFile file,
								            HttpServletRequest request){
	boolean result = false;
	
	System.out.println(resourceName);
	System.out.println(resourceknowledge);
	System.out.println(resourceType);
	 // 获取文件类型  
    System.out.println(file.getContentType());  
    // 获取文件大小  
    System.out.println(file.getSize());  
    // 获取文件名称  
    System.out.println(file.getOriginalFilename());  
  
	if (!file.isEmpty()) {
         try {
             byte[] bytes = file.getBytes();
             BufferedOutputStream stream =
                     new BufferedOutputStream(new FileOutputStream(new File(resourceName + "-uploaded")));
             stream.write(bytes);
             stream.close(); 
            
         } catch (Exception e) {}
     } else {}
	
	  String username = (String) request.getSession().getAttribute("username");
	  Integer id = (Integer) request.getSession().getAttribute("id");
	
	
    imageService.uploadImage(resourceName, resourceknowledge, file,username,id);
	result= true;
	return result;
	
	
  }
  
  @RequestMapping(value="/uploadflash", method=RequestMethod.POST)
  public @ResponseBody boolean uploadFlash( @RequestParam("resourceName") String resourceName,
											@RequestParam("resourceknowledge") String resourceknowledge,
											@RequestParam("resourceType") String resourceType,
											@RequestParam("resourceURL") String resourceURL,
								            HttpServletRequest request){	 
	boolean result = false;
	
	System.out.println(resourceName);
	System.out.println(resourceknowledge);
	System.out.println(resourceType);
	System.out.println(resourceURL);
	
    String username = (String) request.getSession().getAttribute("username");
    Integer id = (Integer) request.getSession().getAttribute("id");
  
    imageService.uploadFlash(resourceName, resourceknowledge, resourceURL,username,id);

	
	//if success 
	result= true;
	return result;
  
  }
}