package org.tjsse.courseshare.service.impl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.tjsse.courseshare.bean.ProblemResource;
import org.tjsse.courseshare.bean.Resource;
import org.tjsse.courseshare.dao.ResourceDao;
import org.tjsse.courseshare.dao.ProblemResourceDao;
import org.tjsse.courseshare.service.ImageService;
import org.tjsse.courseshare.util.Config;

@Service
public class ImplImageService implements ImageService {
	  
  public static final String PRES_PATH = Config.PROBLEM_RES_PATH;
  public static final String PRES_URL = Config.ROOT_URI
      + "/problemset/resource/";
  
  @Autowired
  private ResourceDao resourceDao;
  @Autowired
  private  ProblemResourceDao problemResourceDao;

  @Override
  public int importImages(String path) {
    int count = 0;
    try {
      FileReader reader = new FileReader(path);
      BufferedReader br = new BufferedReader(reader);
      String line = "";
      while ((line = br.readLine()) != null) {
        StringTokenizer st = new StringTokenizer(line, " ");
        Resource image = new Resource();
        image.setName(line);
        image.setUrl(IMG_URL + st.nextToken());
        image.setKnowledge(br.readLine());
        br.readLine();
        if (resourceDao.save(image) != null) {
          count++;
        }
      }
      br.close();
      reader.close();
    } catch (IOException e) {
      return count;
    }
    return count;
  }

  @Override
  public List<Resource> findImages() {
    return resourceDao.find();
  }

  @Override
  public List<Resource> findImages(String[] contents) {
    if (contents == null || contents.length == 0) {
      return findImages();
    }
    StringBuffer condition = new StringBuffer();
    for (int i = 0; i < contents.length; i++) {
      if (contents[i] == null || contents[i].isEmpty())
        continue;
      if (condition.length() != 0) {
        condition.append(" AND ");
      }
      condition.append(String.format(
          "(name LIKE '%%%s%%' OR knowledge LIKE '%%%s%%' OR user_name LIKE '%%%s%%')", contents[i],
          contents[i], contents[i]));
      
    }
    return resourceDao.find(condition.toString());

  }

  @Override
  public byte[] readImage(String imageNo) {
    String path = String.format("%s%s.%s", IMG_PATH, imageNo, IMG_SUFFIX);
    File image = new File(path);
    if (!image.exists()) {
      return null;
    }
    byte[] data = null;
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(path);
      data = new byte[(int) image.length()];
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
  
  
	@Override
	public boolean uploadImage(String resourceName, String resourceknowledge,
			MultipartFile file,String username,Integer id) {
		// TODO Auto-generated method stub
		Resource re = new Resource();
		
		String imagePath="";
		
		//save this picure into the DB and FS
	 	ProblemResource pr = new ProblemResource();
	 	ProblemResource prA = new ProblemResource();
	    String[] contentType = file.getContentType().split("/");
	    String pictureType = contentType[1];
	    String presAUrl = null; 
	    System.out.println(pictureType);
	    byte[] content;
		try {
			content = file.getBytes();
			System.out.println(content);
			pr.setType(pictureType);
	        pr.setUri(PRES_PATH);
	        pr = problemResourceDao.save(pr);
	        if (pr == null) {
	        	imagePath = " ";
	        }
	        // Absolute path of problem resources on disk.
	        String presPath = String.format("%s/%d.%s", PRES_PATH, pr.getId(),
	            pr.getType());
	        // Web URI to access problem resources.
	        String presUrl = String.format("%s%d", PRES_URL, pr.getId());
	
	        
	        imagePath = writeFile(content, presPath) ? presUrl : "";
	        
	       
		    int height=180;
		    int width=216;
		    	        
		    File f = new File(presPath);
		    
		    prA.setType(pictureType);
		    prA.setUri(PRES_PATH);
		    prA = problemResourceDao.save(prA);
		    
		    String presAPath = String.format("%s/%d.%s", PRES_PATH, prA.getId(),
		            prA.getType());
		    // Web URI to access problem resources.
		   presAUrl = String.format("%s%d", PRES_URL, prA.getId());

			BufferedImage bimg = ImageIO.read(f);

			Image image = bimg.getScaledInstance(width, height, Image.SCALE_SMOOTH);

			BufferedImage target = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);

			Graphics g = target.getGraphics();  

			g.drawImage(image, 0, 0, null);

			g.dispose(); 

			ImageIO.write(target, pictureType, new File(presAPath)); 
		  		    
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		re.setName(resourceName);
		re.setKnowledge(resourceknowledge);
		re.setAddress(presAUrl);
		re.setUrl(imagePath);
		re.setUserId(id);
		re.setUserName(username);
		

	    
		System.out.println("======"+id);
		
		
		return resourceDao.save(re) == null ?true : false;
	}

	@Override
	public boolean uploadFlash(String resourceName, String resourceknowledge,
			String resourceURL,String username,Integer id) {
		// TODO Auto-generated method stub
		Resource re = new Resource();
		
		ProblemResource pr = new ProblemResource();
		pr.setType("jpg");
        pr.setUri(PRES_PATH);
        pr = problemResourceDao.save(pr);
	
		String APath = String.format("%s/%s.%s", PRES_PATH, "flash",pr.getType());
		
		File f = new File(APath);
		    
		 int height=180;
		 int width=216;
	    
	   String presAPath = String.format("%s/%d.%s", PRES_PATH, pr.getId(),
			   pr.getType());
	    // Web URI to access problem resources.
	    String presAUrl = String.format("%s%d", PRES_URL, pr.getId());

		BufferedImage bimg;
		try {
			bimg = ImageIO.read(f);
			
			Image image = bimg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	
			BufferedImage target = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
	
			Graphics g = target.getGraphics();  
	
			g.drawImage(image, 0, 0, null);
	
			g.dispose(); 
	
			ImageIO.write(target, pr.getType(), new File(presAPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		re.setName(resourceName);
		re.setKnowledge(resourceknowledge);
		re.setAddress(presAUrl);
		re.setUrl(resourceURL);
		re.setUserId(id);
		re.setUserName(username);

		return resourceDao.save(re) == null ?true : false;
	}
	

	
}
