<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="../include/header.jsp"%>
<script type="text/javascript" src="/resources/js/upload.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
<!-- Main content -->
	<!-- 밑의 div에 적용되는 CSS -->
    <style type="text/css">
    .popup {position: absolute;}
    .back { background-color: gray; opacity:0.5; width: 100%; height: 300%; overflow:hidden;  z-index:1101;}
    .front { 
       z-index:1110; opacity:1; boarder:1px; margin: auto; 
      }
     .show{
       position:relative;
       max-width: 1200px; 
       max-height: 800px; 
       overflow: auto;       
     }  	
    </style>
    
    
	<!-- 이미지를 보여주기 위해서 화면상에 숨겨져 있는 div -->
    <div class='popup back' style="display:none;"></div>
    <div id="popup_front" class='popup front' style="display:none;">
     <img id="popup_img">
    </div>

<section class="content">	
	<div class="row">
		<!-- left column -->
		<div class="col-md-12">
			<!-- general form elements -->
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">READ BOARD</h3>
				</div>
				<!-- /.box-header -->

				<form role="form" action="modifyPage" method="post">

					<input type='hidden' name='bno' value="${boardVO.bno}"> <input
						type='hidden' name='page' value="${cri.page}"> <input
						type='hidden' name='perPageNum' value="${cri.perPageNum}">
					<input type='hidden' name='searchType' value="${cri.searchType}">
					<input type='hidden' name='keyword' value="${cri.keyword}">

				</form>

				<div class="box-body">
					<div class="form-group">
						<label for="exampleInputEmail1">Title</label> <input type="text"
							name='title' class="form-control" value="${boardVO.title}"
							readonly="readonly">
					</div>
					<div class="form-group">
						<label for="exampleInputPassword1">Content</label>
						<textarea class="form-control" name="content" rows="3"
							readonly="readonly">${boardVO.content}</textarea>
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Writer</label> <input type="text"
							name="writer" class="form-control" value="${boardVO.writer}"
							readonly="readonly">
					</div>
				</div>
				<!-- /.box-body -->
				
<ul class="mailbox-attachments clearfix uploadedList"></ul>


 <div class="box-footer">
   <button type="submit" class="btn btn-warning" id="modifyBtn">Modify</button>
   <button type="submit" class="btn btn-danger" id="removeBtn">REMOVE</button>
   <button type="submit" class="btn btn-primary" id="goListBtn">GO LIST </button>
 </div>



			</div>
			<!-- /.box -->
		</div>
		<!--/.col (left) -->

	</div>
	<!-- /.row -->


	<!-- 댓글 등록 뷰 -->
	<div class="row">
		<div class="col-md-12">

			<div class="box box-success">
				<div class="box-header">
					<h3 class="box-title">ADD NEW REPLY</h3>
				</div>
				<div class="box-body">
					<label for="exampleInputEmail1">Writer</label> <input
						class="form-control" type="text" placeholder="USER ID"
						id="newReplyWriter"> <label for="exampleInputEmail1">Reply
						Text</label> <input class="form-control" type="text"
						placeholder="REPLY TEXT" id="newReplyText">

				</div>
				<!-- /.box-body -->
				<div class="box-footer">
					<button type="button" class="btn btn-primary" id="replyAddBtn">ADD
						REPLY</button>
				</div>
			</div>

		
		<!-- The time line -->
		<ul class="timeline">
		  <!-- timeline time label -->
		<li class="time-label" id="repliesDiv">
		  <span class="bg-green">
		    Replies List <small id='replycntSmall'> [ ${boardVO.replycnt} ] </small>
		    </span>
		  </li>
		</ul>
		   
			<div class='text-center'>
				<ul id="pagination" class="pagination pagination-sm no-margin ">

				</ul>
			</div>

		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->


<!-- 수정과 삭제 작업을 하기 위해 별도의 <div> & css -->  
<!-- Modal -->
<div id="modifyModal" class="modal modal-primary fade" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title"></h4>
      </div>
      <div class="modal-body" data-rno>
        <p><input type="text" id="replytext" class="form-control"></p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-info" id="replyModBtn">Modify</button>
        <button type="button" class="btn btn-danger" id="replyDelBtn">DELETE</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>      
	
	
</section>
<!-- /.content -->


<!--첨부파일 템플릿 -->
<script id="templateAttach" type="text/x-handlebars-template">
<li data-src='{{fullName}}'>
  <span class="mailbox-attachment-icon has-img"><img src="{{imgsrc}}" alt="Attachment"></span>
  <div class="mailbox-attachment-info">
	<a href="{{getLink}}" class="mailbox-attachment-name">{{fileName}}</a>
	</span>
  </div>
</li>                
</script>  


<!-- 댓글 템플릿 -->
<script id="template" type="text/x-handlebars-template">
{{#each .}}
<li class="replyLi" data-rno={{rno}}>
<i class="fa fa-comments bg-blue"></i>
 <div class="timeline-item" >
  <span class="time">
    <i class="fa fa-clock-o"></i>{{prettifyDate regdate}}
  </span>
  <h3 class="timeline-header"><strong>{{rno}}</strong> -{{replyer}}</h3>
  <div class="timeline-body">{{replytext}} </div>
    <div class="timeline-footer">
     <a class="btn btn-primary btn-xs" 
	    data-toggle="modal" data-target="#modifyModal">Modify</a>
    </div>
  </div>			
</li>
{{/each}}
</script>

<script>
	Handlebars.registerHelper("prettifyDate", function(timeValue) {
		var dateObj = new Date(timeValue);
		var year = dateObj.getFullYear();
		var month = dateObj.getMonth() + 1;
		var date = dateObj.getDate();
		return year + "/" + month + "/" + date;
	});

	var printData = function(replyArr, target, templateObject) {

		var template = Handlebars.compile(templateObject.html());

		var html = template(replyArr);
		$(".replyLi").remove();
		target.after(html);

	}

	var bno = ${boardVO.bno};//JSP에 처리되는 문자열로 해당 게시물의 번호
	
	var replyPage = 1;	// 수정,삭제 작업 이후에 사용자가 보던 댓글의 페이지 번호를 가지고 다시 목록을 출력

	function getPage(pageInfo){
		//특정 게시물에 대한 페이징 처리를 위해 호출되는 함수(내부적으로 jQuery를 이용해서 JSON타입의 데이터를 처리)	
		$.getJSON(pageInfo,function(data){
			printData(data.list, $("#repliesDiv") ,$('#template'));
			printPaging(data.pageMaker, $(".pagination"));
			
			$("#modifyModal").modal('hide'); //댓글 수정,삭제 탭 hide
			$("#replycntSmall").html("[ " + data.pageMaker.totalCount +" ]");  //댓글 삭제 후 페이징 처리 + 댓글 수 갱신
			
		});
	}



	var printPaging = function(pageMaker, target) {

		var str = "";

		if (pageMaker.prev) {
			str += "<li><a href='" + (pageMaker.startPage - 1)
					+ "'> << </a></li>";
		}

		for (var i = pageMaker.startPage, len = pageMaker.endPage; i <= len; i++) {
			var strClass = pageMaker.cri.page == i ? 'class=active' : '';
			str += "<li "+strClass+"><a href='"+i+"'>" + i + "</a></li>";
		}

		if (pageMaker.next) {
			str += "<li><a href='" + (pageMaker.endPage + 1)
					+ "'> >> </a></li>";
		}

		target.html(str);
	};
	
	//목록의 사이즈 체크(목록을 가져오는 버튼이 보여지는 <li>만 있는 경우에 1페이지의 댓글 목록을 가져오기 위한 코드)
	$("#repliesDiv").on("click", function() {

		if ($(".timeline li").size() > 1) {
			return;
		}
		getPage("/replies/" + bno + "/1");

	});
	
	//페이징처리
	$(".pagination").on("click", "li a", function(event){
		
		event.preventDefault(); //<a href=''> 태그의 기본동작인 페이지 전환을 막음.
		
		replyPage = $(this).attr("href"); //현재 클릭된 페이지의 번호 얻음
		
		getPage("/replies/"+bno+"/"+replyPage);	//해당하는 페이지의 getPage호출	
		
	});
	

	//댓글 등록의 이벤트처리
	$("#replyAddBtn").on("click",function(){		 
		 var replyerObj = $("#newReplyWriter");
		 var replytextObj = $("#newReplyText");
		 var replyer = replyerObj.val();
		 var replytext = replytextObj.val();	  
		 $.ajax({ 	//jQuery를 이용하여 $.ajax()를 통해 서버를 호출 
			 		//(전송데이터는 JSON으로 구성된 문자열, 받는 데이터 단순 문자열)	
			 		//$.post()는 일반적인 데이터 전송 방식에 적합 BUT RestController에서 
			 		//@RequestBody(JSON으로 전송된 데이터를 ReplyVO 타입의 객체로 변환)를 제대로 처리못하는 문제 발생
			type:'post',
			url:'/replies/',
			headers: { //헤더 정보에 "application/json"이라고 명시
			      "Content-Type": "application/json",
			      "X-HTTP-Method-Override": "POST" },
			dataType:'text',
			data: JSON.stringify({bno:bno, replyer:replyer, replytext:replytext}), //JSON데이터 구성
			//e.g) 실제 전송 시 {"bno":12345,"replyer":"user00","replytext":"hivava"} 
			success:function(result){ //성공시
				console.log("result: " + result);
				if(result == 'SUCCESS'){
					alert("등록 되었습니다.");
					replyPage = 1;
					getPage("/replies/"+bno+"/"+replyPage); //등록 후 전체 댓글 목록 갱신
					replyerObj.val("");
					replytextObj.val("");
				}
		}});
	});

	//댓글 보기 버튼 이벤트 처리
	$(".timeline").on("click", ".replyLi", function(event){
		
		var reply = $(this);
		
		$("#replytext").val(reply.find('.timeline-body').text());
		$(".modal-title").html(reply.attr("data-rno"));
		
	});
	
	
	//댓글 - 수정하기 버튼 이벤트 처리
	$("#replyModBtn").on("click",function(){
		  
		  var rno = $(".modal-title").html();
		  var replytext = $("#replytext").val();
		  
		  $.ajax({
				type:'put',
				url:'/replies/'+rno,
				headers: { 
				      "Content-Type": "application/json",
				      "X-HTTP-Method-Override": "PUT" },
				data:JSON.stringify({replytext:replytext}), 
				dataType:'text', 
				success:function(result){
					console.log("result: " + result);
					if(result == 'SUCCESS'){
						alert("수정 되었습니다.");
						getPage("/replies/"+bno+"/"+replyPage );
					}
			}});
	});
	
	//댓글 - 삭제하기
	$("#replyDelBtn").on("click",function(){
		  
		  var rno = $(".modal-title").html();
		  var replytext = $("#replytext").val();
		  
		  $.ajax({
				type:'delete',
				url:'/replies/'+rno,
				headers: { 
				      "Content-Type": "application/json",
				      "X-HTTP-Method-Override": "DELETE" },
				dataType:'text', 
				success:function(result){
					console.log("result: " + result);
					if(result == 'SUCCESS'){
						alert("삭제 되었습니다.");
						getPage("/replies/"+bno+"/"+replyPage );
					}
			}});
	});
	
</script>


<script>
//게시글 수정,삭제,리스트
$(document).ready(function(){
	
	var formObj = $("form[role='form']");
	
	console.log(formObj);
	
	$("#modifyBtn").on("click", function(){
		formObj.attr("action", "/sboard/modifyPage");
		formObj.attr("method", "get");		
		formObj.submit();
	});
	
/* 	$("#removeBtn").on("click", function(){
		formObj.attr("action", "/sboard/removePage");
		formObj.submit();
	}); */

	
	$("#removeBtn").on("click", function(){
		
		var replyCnt =  $("#replycntSmall").html();
		
		if(replyCnt > 0 ){
			alert("댓글이 달린 게시물을 삭제할 수 없습니다.");
			return;
		}	
		
		var arr = [];
		$(".uploadedList li").each(function(index){
			 arr.push($(this).attr("data-src"));
		});
		
		if(arr.length > 0){
			$.post("/deleteAllFiles",{files:arr}, function(){
				
			});
		}
		
		formObj.attr("action", "/sboard/removePage");
		formObj.submit();
	});	
	
	$("#goListBtn ").on("click", function(){
		formObj.attr("method", "get");
		formObj.attr("action", "/sboard/list");
		formObj.submit();
	});
	
	
	//컨트롤러에서 문자열 리스트를 반환 -> JSON 형태의 데이터 전송
	//-> 이를 getJSON()을 이용해 처리
	var bno = ${boardVO.bno};
	var template = Handlebars.compile($("#templateAttach").html());
	
	$.getJSON("/sboard/getAttach/"+bno,function(list){ 
		$(list).each(function(){
			
			var fileInfo = getFileInfo(this);
			
			var html = template(fileInfo);
			
			 $(".uploadedList").append(html);
			
		});
	});
	

	//파일 클릭 시 이벤트 처리
	$(".uploadedList").on("click", ".mailbox-attachment-info a", function(event){		
		var fileLink = $(this).attr("href");		
		if(checkImageType(fileLink)){  //사용자가 첨부파일의 제목을 클릭 & 이미지이면			
			event.preventDefault(); //화면 이동 못하게 막기					
			var imgTag = $("#popup_img"); //현재 클릭한 이미지의 경로를 id속성값이 'popup_img'인 요소에 추가
			imgTag.attr("src", fileLink);			
			console.log(imgTag.attr("src"));					
			$(".popup").show('slow'); //화면에 보이도록
			imgTag.addClass("show");		
		}	
	});
	
	//원본이미지 클릭시 이벤트처리(hide)
	$("#popup_img").on("click", function(){
		
		$(".popup").hide('slow');
		
	});	
});
</script>

<%@include file="../include/footer.jsp"%>
