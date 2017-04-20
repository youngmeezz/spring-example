<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<style>
.fileDrop {
	width:100%;
	height:200px;
	border:1px dotted blue;
}

small {
	margin-left : 3px;
	font-weight : bold;
	color : gray;
}
</style>
</head>
<body>

	<h3>Ajax File Upload</h3>
	<div class='fileDrop'></div>
	
	<div class='uploadedList'></div>
	
	<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
	<script>
		$(".fileDrop").on("dragenter dragover", function(event){ //브라우저에서 파일을 끌어다 놓아도 아무런 동작X
			event.preventDefault();
		});
		
		$(".fileDrop").on("drop",function (event) {
			event.preventDefault();
			
			var files = event.originalEvent.dataTransfer.files;
			
			var file = files[0];

			//console.log(file);
			
			var formData = new FormData();		
			
			formData.append("file", file);
			$.ajax({
				  url: '/uploadAjax',
				  data: formData,
				  dataType:'text',
				  processData: false,
				  contentType: false,
				  type: 'POST',
				  success: function(data){					  
					var str ="";		
					console.log(data);
					console.log(checkImageType(data));
					if(checkImageType(data)){
						  str ="<div><a href=displayFile?fileName="+getImageLink(data)+">"
								  +"<img src='displayFile?fileName="+data+"'/>"
								  +"</a><small data-src="+data+">X</small></div>";
					}else{
						  str = "<div><a href='displayFile?fileName="+data+"'>" 
								  + getOriginalName(data)+"</a>"
								  +"<small data-src="+data+">X</small></div></div>";
					}					  
					$(".uploadedList").append(str);
				  }
			});								
		});
		
		$(".uploadedList").on("click","small",function(event){			
			var that = $(this);			
			$.ajax({
				url:"deleteFile",
				type:"post",
				data:{fileName:$(this).attr("data-src")},
				dataType:"text",
				success:function(result){
					if(result == 'deleted') {
						//alert("deleted");
						that.parent("div").remove(); //view화면에서 지우기
					}
				}				
			});	
			//<small>태그로 처리된 'x'를 클릭하면 'data-src' 속성 값으로 사용된 파일의 이름을 얻어와서 POST방식으로 호출
		});
		
 
 		// 이미지 타입인지 체크 		
		function checkImageType(fileName) {
			var pattern = /jpg|gif|png\jpeg/i; //정규표현식, i는 대소문자 구문X
			return fileName.match(pattern);
		}
		
		//원본 파일 이름 얻기
		function getOriginalName(fileName) {
			if(checkImageType(fileName)) {
				return;
			}
			
			var idx = fileName.indexOf("_")+1;
			return fileName.substr(idx);
		}
		
		//원본 이미지 파일 이름 얻기
		function getImageLink(fileName) {
			if(!checkImageType(fileName)) {
				return;
			}
			var front = fileName.substr(0,12); //'/년/월/일 경로 추출'
			var end = fileName.substr(14); //'s_'제거
			return front+end;				
		}
		
		/* 		
		$(".fileDrop").on("drop", function(event){
			event.preventDefault();
			
			var files = event.originalEvent.dataTransfer.files;
			//event.originalEvent는 jQuery를 이용하는 경우 event가 순수한 DOM 이벤트가 아님
			//=>event.originalEvent를 이용해서 순수한 원래의 DOM 이벤트를 가져옴
			
			//event.dataTransfer는 이벤트와 같이 전달된 데이터를 의미하고, 그 안에 포함된 파일 데이터를 찾아내기 위해서 사용
			
			var file = files[0];
			
			//console.log(file); //Drop이 이루어진 파일에 대한 정보 출력
			var formData = new FormData();
			formData.append("file",file);
			
			$.ajax({
				url : '/uploadAjax',
				data : formData,
				dataType : 'text',
				processData : false,
				contentType : false,
				type:'POST',
				success:function(data){
					alert(data);
				}
			});			
		});
 */
		
		
	</script>
	
</body>
</html>











