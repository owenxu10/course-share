package org.tjsse.courseshare.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
import org.tjsse.courseshare.bean.Image;
import org.tjsse.courseshare.service.FlashService;
import org.tjsse.courseshare.service.ImageService;
import org.tjsse.courseshare.util.Config;
import org.tjsse.courseshare.util.LibType;

@Controller
@RequestMapping("/image")
public class ImageController {

  @Autowired
  private ImageService imageService;
  private FlashService flashService;

  /* 
   * Action: '/index', Method: GET
   * Default index page.
   */
  @RequestMapping(value = "", method = RequestMethod.GET)
  public ModelAndView index() { 
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
	 List<Image> images = imageService.findImages(str);
	    if (images == null) {
	    	images = new ArrayList<Image>();
	    }
	  
	  for (Image p : images) {
		  System.out.println(p.getId());
	    }

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
  
  /* 
   * Action: '/upload', Method: POST
   * Upload problems from web page.
   */
  @RequestMapping(value="/upload", method=RequestMethod.POST)
  public @ResponseBody boolean uploadProblems( @RequestParam("resourceName") String resourceName,
											@RequestParam("resourceknowledge") String resourceknowledge,
											@RequestParam("resourceType") String resourceType,
											@RequestParam("resourceURL") String resourceURL,
								            @RequestParam("resourceImage") MultipartFile file){
 
	 
	boolean result = false;
	
	System.out.println(resourceName);
	System.out.println(resourceknowledge);
	System.out.println(resourceType);
	System.out.println(resourceURL);
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
	
	
	if(resourceType=="image"){
		imageService.uploadImage(resourceName, resourceknowledge, file);
	}
	else
		flashService.uploadFlash(resourceName, resourceknowledge, resourceURL);
	
//	problemsetService.uploadProblem(problemType,
//		    problemDiff,
//			problemKnowledge,
//		    problemContent,
//			keyTypeText,
//			keyTypePic,
//			keyContent,
//			file);

	
	//if success 
	result= true;
	return result;
  }
  
  
  
  
  
  
  

}
