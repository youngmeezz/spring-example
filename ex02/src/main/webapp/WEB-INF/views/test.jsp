<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<style>
#modDiv{
width:300px;
height:100px;
background-color:gray;
position:absolute;
top:50%;
left:50%;
margin-top:-50px;
margin-left:-150px;
padding:10px;
z-index:1000;
}	
</style>

</head>
<body>
	<h2>Ajax Test Page</h2>
	
	<div id='modDiv' style="display:none;">
		<div class='modal-title'></div>
		<div>
			<input type='text' id='replytext'>
		</div>
		<div>
			<button type="button" id="replyModBtn">Modify</button>
			<button type="button" id="replyDelBtn">Delete</button>
			<button type="button" id="closeBtn">Close</button>
		</div>
	</div>
	<!-- 댓글 등록 화면(form태그도 가능하지만, 전송 내용이 많지 않아서 id속성을 이용) -->
	<div>
		<div>
			REPLYER <input type="text" name='replyer' id='newReplyWriter'>
		</div>
		<div>
			REPLY TEXT <input type='text' name='replytext' id='newReplyText'>
		</div>
		<button id="replyAddBtn">ADD REPLY</button>
	</div>	
	
	<!-- 댓글을 위한 ul처리 -->
	<ul id="replies">	
	</ul>
	
	<!-- 댓글 페이지를 위한 ul처리  -->
	<ul class="pagination">	
	</ul>	
	
	
	<!-- jQuery 2.1.4 -->	
	<script src="resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
	<script>
		var bno =1703923;		
		getPageList(1);
		
		//댓글 목록 (페이징처리X)
		function getAllList() {
			$.getJSON("/replies/all/" + bno,function(data){			
				var str="";
				console.log(data.length);
				
				$(data).each(function(){					
					str +="<li data-rno='"+this.rno+"' class='replyLi'>"
							+ this.rno 
							+ " : " 
							+ this.replytext+"<button>MOD</button></li>";							
				});				
				$("#replies").html(str);
			});
		}
		
		//댓글 목록(페이징 처리)
		function getPageList(page) {
			$.getJSON("/replies/"+bno+"/"+page, function(data){
				console.log(data.list.length);
				
				var str="";
				
				$(data.list).each(function(){
					str+="<li data-rno='"+this.rno+"' class='replyLi'>"
						+this.rno+" : "+this.replytext
						+"<button>MOD</button></li>"
				});
				
				$("#replies").html(str);
				printPaging(data.pageMaker);				
			});			
		}
		
		function printPaging(pageMaker) {
			var str = "";
			
			if(pageMaker.prev) {
				str += "<li><a href='"+(pageMaker.startPage-1)+ "'><<</a></li>";
			}
			
			for(var i=pageMaker.startPage, len=pageMaker.endPage; i <= len; i++) {
				var strClass = pageMaker.cri.page  == i?'class=active':'';
				str += "<li "+strClass+"><a href='"+i+"'>"+i+"</a></li>";						
			}
			
			if(pageMaker.next) {
				str += "<li><a href='"+(pageMaker.endPage + 1)+ "'> >> </a></li>";
			}
			
			$('.pagination').html(str);		
		}
		
		var replyPage = 1;
		
		$(".pagination").on("click","li a",function(event){
			event.preventDefault(); //<a href''>태그의 기본동작인 페이지 전환을 막음 & 현재 클릭된 페이지 번호를 얻어냄
			replyPage = $(this).attr("href");
			getPageList(replyPage); 
		});
		
		
		//댓글 등록 버튼 이벤트 처리
		 $("#replyAddBtn").on("click",function() {
			var replyer = $("#newReplyWriter").val(); //화면에서 입력된 <input> 태그를 변수로 처리
			var replytext = $("#newReplyText").val(); //화면에서 입력된 <input> 태그를 변수로 처리
			
			$.ajax({
				type : 'post',
				url : '/replies',
				headers : {
					"Content-Type" : "application/json",
					"X-HTTP-Method-Override" : "POST"
				},
				dataType : 'text',
				data : JSON.stringify({
					bno : bno,
					replyer : replyer,
					replytext : replytext
				}),
				success : function(result) {
					if(result == 'SUCCESS') {
						alert("등록 되었습니다.");
						getListPage(1);
					}
				}
			});	
			/*
			* jQeury의 $.post()를 사용하지 않는 이유
			 $.post("/replies",
				{replyer:replyer,replytext:replytext},
				function(result){
					alert(result);
				}
			);
			=><form> 처리된 데이터와 동일
			=> 415(지원되지 않는 타입) 상태 코드 전송
			=> RestController에서는 @RequestBody 애노테이션이 제대로 처리X
			*/						
		});
		
		//
		$('#replies').on("click",".replyLi button",function() {
			var reply = $(this).parent();			
			var rno = reply.attr("data-rno");
			var replytext = reply.text();
			
			//alert(rno + " : " + replytext);
			$(".modal-title").html(rno);
			$("#replytext").val(replytext);
			$("#modDiv").show("slow");
		});
		
		//댓글 MOD -> 삭제 버튼 누른 이벤트 처리
		$("#replyDelBtn").on("click",function() {
			var rno = $(".modal-title").html();
			var replytext = $("#replytext").val();
			
			$.ajax({
				type : 'delete',
				url : '/replies/'+rno,
				headers : {
					"Content-Type" : "application/json",
					"X-HTTP-Method-Override" : "DELETE"
				},
				dataType : 'text',
				success : function(result) {
					console.log("result : "+result);
					if(result == 'SUCCESS') {
						alert("삭제 되었습니다.");
						$("#modDiv").hide("slow");
						getListPage(1);
					}
				}				
			});			
		});
		
		//댓글 수정 버튼 클릭
		$("#replyModBtn").on("click",function() {
			var rno = $(".modal-title").html();
			var replytext = $("#replytext").val();
			
			$.ajax({
				type : 'put',
				url : '/replies/'+rno,
				headers : {
					"Content-Type" : "application/json",
					"X-HTTP-Method-Override" : "PUT"},
				data:JSON.stringify({replytext:replytext}),
				dataType : 'text',
				success:function(result) {
					console.log("result : "+result);
					if(result == 'SUCCESS') {
						alert("수정되었습니다.");
						$("#modDiv").hide("slow");
						getListpage(1);
						//getPageList(replyPage);
					}
				}
				
			});			
		});
		
		
		
		
			
	</script>

</body>
</html>