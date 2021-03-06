package org.tjsse.courseshare.controller;

import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.tjsse.courseshare.bean.Subject;
import org.tjsse.courseshare.bean.Orders;
import org.tjsse.courseshare.bean.Theme;
import org.tjsse.courseshare.service.SubjectService;
import org.tjsse.courseshare.util.Config;
import org.tjsse.courseshare.util.LibType;

@Controller
@RequestMapping("/subject")
public class SubjectController {

  @Autowired
  private SubjectService subjectService;
  

  @RequestMapping(value = "", method = RequestMethod.GET)
  public ModelAndView index(HttpServletRequest request,HttpServletResponse response) {
    ModelMap subjectMap = new ModelMap();
    int themeid=1;
    subjectMap.addAttribute("libType", LibType.SUBJECT);
    
    List<Subject> subjects = subjectService.findSubjects(themeid);
    //List<Subject> subjects = subjectService.findSubjects();
    List<Subject> temp = subjectService.findSubjects(themeid);
    int ID=2;
    ID = (int) request.getSession().getAttribute("id");
    String order;
   
    
    int size = subjectService.getOrder(themeid, ID).size();
    if(size!=0){
    	 List<Orders> orders = subjectService.getOrder(themeid, ID);
    	 order = orders.get(0).getorderlist();
    	 subjects= adjustOrder(subjects,temp, order);
//    	 for(Subject s:subjects){
//    			System.out.println(s.getSubject_id());
//    		}
//   	  	System.out.println(order);
   
    }
    
    List<Theme> themes = subjectService.getTheme();
    
    subjectMap.addAttribute("subjects", subjects);
    subjectMap.addAttribute("themes", themes);
    return new ModelAndView("subject", subjectMap);
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
  
  @RequestMapping(value = "/order", method = RequestMethod.GET)
  @ResponseBody
  public void setOrder(@RequestParam(value = "order") String order,
		  			   @RequestParam(value = "theme_id") int theme_id,
		  			 HttpServletRequest request,HttpServletResponse response){
	  String orderlist = "";
    System.out.println("order--"+order);
	if(!order.equals("null")){
	    String[] array = order.split("[\r\n]+");
	    for(int i =0 ;i<array.length;i++){
	    	if(i%10==2){
	    		orderlist=orderlist+array[i].trim()+"/";
	    	}
	    }
	    int userid= (int) request.getSession().getAttribute("id");
	    System.out.println(userid);
	    System.out.println(theme_id);
	    System.out.println(orderlist);
	    
	    subjectService.setOrder(userid, theme_id, orderlist);
	}
  }
  
  
  @RequestMapping(value = "/orderedlist", method = RequestMethod.GET)
  @ResponseBody
  public List<Subject> listOrder(@RequestParam(value = "themeid") int themeid,
		  HttpServletRequest request,HttpServletResponse response){
	    String order;
		int ID = (int) request.getSession().getAttribute("id");
		List<Subject> subjects = subjectService.findSubjects(themeid);
		List<Subject> temp = subjectService.findSubjects(themeid);
		  int size = subjectService.getOrder(themeid, ID).size();
		    if(size!=0){
		    	 List<Orders> orders = subjectService.getOrder(themeid, ID);
		    	 order = orders.get(0).getorderlist();
		    	 subjects= adjustOrder(subjects,temp, order);
		    }
		return subjects;
  }
  
  @RequestMapping(value = "/themelist", method = RequestMethod.GET)
  @ResponseBody
  public List<Theme> listOrder(){
	    List<Theme> themes = subjectService.getTheme();
		return themes;
	
  }
  
  @RequestMapping(value = "/importSubject", method = RequestMethod.GET)
  @ResponseBody
  public void importSubject(@RequestParam(value = "title") String title,
								  @RequestParam(value = "theme_id") int theme_id,
								  @RequestParam(value = "description") String description,
								  @RequestParam(value = "url") String url,
								  HttpServletRequest request,HttpServletResponse response){
	  
	  int ID = (int) request.getSession().getAttribute("id");
	  
	  int subjectID=subjectService.importSubject(title,theme_id,description,url); 
	  subjectService.importOrder(subjectID,theme_id, ID);
  }
  
  @RequestMapping(value = "/deleteTheme", method = RequestMethod.GET)
  @ResponseBody
  public void deleteTheme(@RequestParam(value = "theme_id") int theme_id){
	  
	  subjectService.deleteTheme(theme_id);
  }
  
  @RequestMapping(value = "/addTheme", method = RequestMethod.GET)
  @ResponseBody
  public void importSubject(@RequestParam(value = "name") String name){

	  subjectService.addTheme(name);
  }
  
  @RequestMapping(value = "/deletesubject", method = RequestMethod.GET)
  @ResponseBody
  public void deleteSubject(@RequestParam(value = "subjectid") int subjectid,
		  			HttpServletRequest request,HttpServletResponse response){
	  
	  List<Subject> subjects = subjectService.findSubjectbyid(subjectid);
	  int theme_id= subjects.get(0).getThemeid();
	  int ID = (int) request.getSession().getAttribute("id");
	  subjectService.deleteSubject(subjectid);
	  subjectService.deleteOrder(subjectid,theme_id, ID);
  }
  
private List<Subject> adjustOrder(List<Subject> subject,List<Subject> temp,  String order){
	  String [] orderlist=order.split("/") ;
	  int toInt;
	  for(int i = 0; i<orderlist.length; i++){
		toInt=Integer.parseInt(orderlist[i]);
		Subject s =getSubjectByid(temp,toInt);
		 subject.set(i, getSubjectByid(temp,toInt));
	  }
	  return subject;
  }

private Subject getSubjectByid(List<Subject> subject, int sid){
	  for(Subject s: subject){
		  if(s.getSubject_id()==sid)
			  return s;
	  }
	  
	  return null;
}

}
