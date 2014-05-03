<%@ page import="java.util.Map, java.util.HashMap, java.util.List" %>
<%@ page import="org.tjsse.courseshare.bean.Problem" %>
<%@ page import="org.springframework.web.servlet.mvc.support.RedirectAttributes" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<% 

  String path = request.getContextPath();
  List<Problem> problems = (List<Problem>) request.getAttribute("problems");
  int count = (Integer) request.getAttribute("count");
  System.out.println(count);
  int offset = 0;
  if (problems.size() > 0) {
    for (Problem p : problems) {
      if (p.getId() != null && p.getId() > offset) {
        offset = p.getId();
      }
    }
    ++offset;
    
  }
  
  

%>
<jsp:include page="layout-header.jsp" flush="true" />

<div id="cs-north-frame" class="row cs-frame-default">
  <div id="cs-problemset-navbar">
    <div id="ps-searchbar" class="input-group">
      <span class="input-group-addon">输入题目关键词</span>
      <input type="text" id="filter-content" class="form-control" placeholder="关键词">
    </div>
    <button type="button" id="ps-search" class="btn btn-primary">
      <span class="glyphicon glyphicon-search"></span> 搜题目
    </button>
    <button type="button" id="ps-basket" class="btn btn-primary">
      <span class="glyphicon glyphicon-shopping-cart"></span> 试题篮
    </button>
    <div id="ps-pswbar" class="input-group">
      <span class="input-group-addon">
        <span>答案密码</span>
        <span id="ps-passwd-instruct" class="btn-danger">说明</span>
      </span>
      <input type="password" id="ps-passwd" class="form-control" placeholder="输入密码">
    </div>
    <span id="ps-passwd-error" class="ps-hidden">密码错误</span>
    <button type="button" id="ps-passwd-hide" class="btn btn-primary ps-hidden">隐藏答案</button>
  </div>  
  
  <div id="cs-paperbasket-navbar" class="ps-hidden">
    <span id="ps-basket-title">试题篮</span>
    <button type="button" id="ps-basket-quit" class="btn btn-primary">退出</button>
    <button type="button" id="ps-basket-clear" class="btn btn-primary">
      <span class="glyphicon glyphicon-trash"></span> 清空题目
    </button>
    <button type="button" id="ps-basket-paper" class="btn btn-primary">
      <span class="glyphicon glyphicon-print"></span> 自动组卷
    </button>    
  </div>
  
</div> <!-- #problemset-header -->

<div id="cs-west-frame" class="cs-frame-default">
  <div class="form-group">
    <label for="knowledge">过滤知识点</label>
    <input type="text" class="form-control" id="filter-know" placeholder="知识点">
  </div>
  <hr>

  <%
  String types[][] = {
    {"type-all", "checked='checked'", "btn-default", "所有题型"},
    {"concept", "", "cs-frame-grey", "概念题"},
    {"blankfill", "", "cs-frame-blue", "填空题"},
    {"choice", "", "cs-frame-green", "选择题"},
    {"question", "", "cs-frame-yellow", "问答题"},
    {"integrate", "", "cs-frame-red", "综合题"}
  };
  for(String[] t : types) { %>
  <div class="checkbox">
    <label>
      <input type="checkbox" name="filter-type" id="<%=t[0] %>" <%=t[1] %>>
      <span class="btn cs-checkbox-text <%=t[2] %>"><%=t[3] %></span>
    </label>
  </div>
  <% } %>
  <hr>
  <%
  String diffs[][] = {
    {"diff-all", "checked='checked'", "btn-default", "所有难度"},
    {"1", "", "btn-primary", "难度：1级"},
    {"2", "", "btn-info", "难度：2级"},
    {"3", "", "btn-success", "难度：3级"},
    {"4", "", "btn-warning", "难度：4级"},
    {"5", "", "btn-danger", "难度：5级"}
  };
  for(String[] d : diffs) { %>
  <div class="checkbox">
    <label>
      <input type="checkbox" name="filter-diff" id="<%=d[0] %>" <%=d[1] %>>
      <span class="btn cs-checkbox-text <%=d[2] %>"><%=d[3] %></span>
    </label>
  </div>
  <% } %>
  
  <hr>
  <div class="btn-group ">
    <button type="button" id="ps-upload" class="btn btn-success btn-lg" data-toggle="modal">
     <span class="glyphicon glyphicon-upload"></span> 上传题目
    </button>
  </div>
</div> <!-- #cs-west-frame -->


<!-- Modal -->
<div class="modal fade" id="uploadModal" tabindex="-1" role="dialog" aria-labelledby="uploadModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
     <!-- form of uploading problem -->
		<form role="form" id="uploadForm" " method="POST" enctype="multipart/form-data" action="/course-share/problemset/upload">
      <div class="modal-header">
        <button type="button" id="ps-uploadClose" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="uploadModalLabel">上传题目</h4>
      </div>
      <div class="modal-body">
		  
	    <label >题型</label>
	    <div>
		 <%
		  String typediffs[][] = {
		    {"concept",  "cs-frame-grey", "概念题"},
		    {"blankfill", "cs-frame-blue", "填空题"},
		    {"choice",  "cs-frame-green", "选择题"},
		    {"question",  "cs-frame-yellow", "问答题"},
		    {"integrate", "cs-frame-red", "综合题"}
		  };
		  for(String[] d : typediffs) { %>
		   <label class="radio-inline">
		      <input type="radio" name="typeRadio"  value="<%=d[2] %>" id="<%=d[0] %>" >
		      <span class="btn cs-radio-text <%=d[1] %>"><%=d[2] %></span>
		   </label>
 		 <% } %>
		</div>
	    <br/>
		<label >难度</label>
		<div>
		 <%
		  String uploaddiffs[][] = {
		    {"btn-primary", "1", "1级"},
		    {"btn-info", "2", "2级"},
		    {"btn-success", "3", "3级"},
		    {"btn-warning", "4", "4级"},
		    {"btn-danger", "5", "5级"}
		  };
		  for(String[] d : uploaddiffs) { %>
		   <label class="radio-inline">
		      <input type="radio" name="diffRadio"  value="<%=d[1] %>" id="<%=d[1] %>">
		      <span class="btn cs-radio-text <%=d[0] %>"><%=d[2] %></span>
		   </label>
 		 <% } %>
		 </div>
		<br/>
		 <div class="form-group">
		    <label >知识点</label>
		    <input type="text" class="form-control" id="inputknowledge" name="inputknowledge" placeholder="知识点...">
		  </div>
		  
		 <div class="form-group">
		    <label >题目内容</label>
		    <textarea class="form-control" id="problemContent" name="problemContent" rows="5" placeholder="输入题目内容..."></textarea>
		  </div>
		  
		  <hr>
		  
		  <label >答案信息</label>
		  
		  <div>
		   <label class="checkbox-inline">
		      <input type="checkbox" name="keyCheckbox"  value="text" id="textkey"> 
		      <span class="cs-typecheckbox-text">文字类型</span>
		   </label>
		   <label class="checkbox-inline">
		      <input type="checkbox" name="keyCheckbox"  value="pic" id="filekey"> 
		      <span class="cs-typecheckbox-text ">图片类型</span>
		   </label>
		 </div>
		 
		 <br/>
		 
		  <div class="form-group ps-hidden" id="textareakey">
		    <label for="exampleInputEmail1">答案内容</label>
		    <textarea class="form-control" name="keyContent" id="keyContent" rows="5" placeholder="输入答案内容..."></textarea>
		  </div>
		
		  <div class="form-group ps-hidden"  id="uploadkey" >
		    <label for="exampleInputFile" >答案文件</label>
		    <input type="file" id="uploadFile" name="uploadFile"  accept="image/*|video/*" required>
		    <p class="help-block">请上传包含答案的文件</p>
		  </div> 
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" id='problemUpload'>上传</button>
      </div>
      </form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->



<!-- Modal -->
<div class="modal fade" id="successModal" tabindex="-1" role="dialog" aria-labelledby="successModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      
      <div class="modal-body">
         <button type="button" class="close" id="closeUploadSuccess" data-dismiss="modal" aria-hidden="true">&times;</button>
         <h4 class="modal-title" id="uploadModalLabel">上传成功</h4>
	  	</div>
      
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<!-- Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      
      <div class="modal-body">
         <button type="button" class="close" id="closedelete" data-dismiss="modal" aria-hidden="true">&times;</button>
         <h4 class="modal-title" id="uploadModalLabel">删除成功</h4>
	  	</div>
      
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<div id="cs-center-frame">
  <div class="ps-row cs-frame-default">
    <div class="ps-col ps-title ps-type">题型</div>
    <div class="ps-col ps-title ps-diff">难度</div> 
    <div class="ps-col ps-title ps-content">题目</div>   
    <div class="ps-col ps-title ps-know">知识点</div>
    <!-- 
    <div class="ps-col" style="float:right;">
      <button id="selectall" class="btn btn-success ps-btn-style">全选</button>
      <button id="selectnone" class="btn btn-danger ps-btn-style">取消</button>
    </div>
    -->
  </div>
  <div id="problemset-list">
  <% 
  Map<String, String> typeCls = new HashMap<String, String>();
  typeCls.put("概念题", "cs-frame-grey");
  typeCls.put("填空题", "cs-frame-blue");
  typeCls.put("选择题", "cs-frame-green");
  typeCls.put("问答题", "cs-frame-yellow");
  typeCls.put("综合题", "cs-frame-red");

  Map<Integer, String> diffCls = new HashMap<Integer, String>();
  diffCls.put(1, "btn-primary");
  diffCls.put(2, "btn-info");
  diffCls.put(3, "btn-success");
  diffCls.put(4, "btn-warning");
  diffCls.put(5, "btn-danger");

  for(Problem p : problems) { 
    String type = typeCls.get(p.getProblemType());
    String diff = diffCls.get(p.getDifficulty());
  %>
  <div class="ps-row <%=type %>" id="<%=p.getId() %>">
  <div id="<%=p.getId() %>" class="ps-problem">
    <div class="ps-col ps-type">
      <span class="btn ps-type-style cs-style-grey2"><%=p.getProblemType() %></span>
    </div>
    <div class="ps-col ps-diff">
      <span class="btn ps-diff-style <%=diff %>"><%=p.getDifficulty() %></span>
    </div>
    <div class="ps-col ps-content">
      <%=p.getProblemContent() %>
    </div>
    <div class="ps-col ps-know">
      <%=p.getKnowledge() %>
    </div>
    <div class="ps-col ps-action">   
      <button id="<%=p.getId() %>" class="basket-add btn btn-success ps-btn-style">放入试题篮</button>
    </div>
  </div>
  
  <div id="<%=p.getId() %>" class="ps-key ps-hidden">
    <div class="ps-key-left">
      【答案】 <span class="glyphicon glyphicon-hand-right"></span> 
    </div>
    <div class="ps-key-right"><%=p.getKeyContent() %></div>
    <button  name="<%=p.getId() %>" id="<%=p.getId() %>" class="btn btn-danger ps-btn-style" onclick="deleteProblem(this.name)">删除</button>
  </div>
  
  
  </div>  
  <% } %>
  <script language="javascript">
   function deleteProblem(name){
 		var deleteAddress = ROOT + 'problemset/deleteProblem';
 		
 		$.ajax({
 			  type: "POST",
 			  url: deleteAddress,
 			  data: {problemID:name}
 			})
 			  .done(function( msg ) {
 				 $('#deleteModal').modal({
 			   	    backdrop:true,
 			   	    keyboard:true,
 			   	    show:true
 			 		});
 				 
 				 
 				$('#deleteModal').on('hidden.bs.modal', function (e) {
 					 window.location.href = ROOT + 'problemset/';
 					})
 				 
 			  });
 		
	  
  }
  </script>
  </div>
   
   <div id="page-nav">
 
<div id="pagniation"></div>
	<div class="input-group" id="page-goto">
	 	 <span class="input-group-addon">跳转到</span>
	  	 <input type="text" class="form-control text-width">
	  	 <span class="input-group-addon" id="count">/<%=count/20%>页</span>
	  	 <span class="input-group-btn">
         	<button id="page-goto-btn" class="btn btn-default" type="button">Go</button>
         </span>
	</div>
  </div>
  

    <script type='text/javascript'>
    </script>
   

  <div id="problemset-loading" class="loading">Loading ......</div>
  <input type="hidden" id="filter-offset" value="<%=offset %>">
  <input type="hidden" id="filter-contents" value="">
  <input type="hidden" id="filter-knows" value="">
  <input type="hidden" id="filter-types" value="">
  <input type="hidden" id="filter-diffs" value="">
  <input type="hidden" id="count" value="<%=count%>">
</div>

<div id="cs-center-dialog" class="ps-hidden">
  <div class="ps-row cs-frame-default">
    <div class="ps-col ps-title ps-type">题型</div>
    <div class="ps-col ps-title ps-diff">难度</div> 
    <div class="ps-col ps-title ps-content">题目</div>   
    <div class="ps-col ps-title ps-know">知识点</div>
  </div>
  <div id="paperbasket-list"></div>
</div>

  


<script type="text/template" id="problem-tpl">
<div class="ps-row <@=typeCls @>" id="<@=id @>">
  <div id="<@=id @>" class="ps-problem">
    <div class="ps-col ps-type">
      <span class="btn ps-type-style cs-style-grey2"><@=type @></span>
    </div>
    <div class="ps-col ps-diff">
      <span class="btn ps-diff-style <@=diffCls @>"><@=diff @></span>
    </div>
    <div class="ps-col ps-content">
      <@=content @>
    </div>
    <div class="ps-col ps-know">
      <@=know @>
    </div>
    <div class="ps-col ps-action">   
      <button id="<@=id @>" class="basket-add btn btn-success ps-btn-style">放入试题篮</button>
     
    </div>
  </div>
  <div id="<@=id @>" class="ps-key ps-hidden">
    <div class="ps-key-left">
      【答案】 <span class="glyphicon glyphicon-hand-right"></span> 
    </div>
    <div class="ps-key-right"><@=key @></div>
 <button  name="<@=id @>" id="<@=id @>" class="btn btn-danger ps-btn-style" onclick="deleteProblem(this.name)">删除</button>
 
  </div>

</div>
</script>

<script type="text/template" id="basket-btn-tpl">
<button id="<@=id @>" class="basket-remove btn btn-danger ps-btn-style">移出试题篮</button>
<button id="<@=id @>" class="basket-select btn btn-success ps-btn-style">已选</button>
</script>

<jsp:include page="layout-footer.jsp" flush="true" />