<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>LIST PAGE</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>	
</head>
<body>

<h4>Test list page..</h4>
<input type="text" id="text"><button id="btnAdd">등록</button> <br />
<div id="listDiv"></div>


<script>
var defaultUrl = '/context/menu';
var printList = function() {
	$('#listDiv').empty();
	$.getJSON(defaultUrl+'/all',function(data) {
		var html = '<table>';
		$.each(data, function(key,value){
			var btn1 = '<button type="button" class="btnRemove" data-id="' + key +'">삭제</button>';
			var btn2 = '<button type="button" class="btnDetail" data-id="' + key +'">상세</button>';
			html += '<tr><th>' + key + '</th><td>' + value + '</td><td>' +btn1+ '</td><td>'+btn2+'</td></tr>';
		});
		html+='</table>';		
		$('#listDiv').append(html);
	});
};

$(function() {	
	// list	
	printList();
	
	// add
	$('#btnAdd').on('click',function(e) {
		$.ajax( {
			type:'post',
			url: defaultUrl+'/',
			headers: { 
			      "Content-Type": "application/json",
			      "X-HTTP-Method-Override": "POST" },
			dataType:'text',
			data: JSON.stringify($('#text').val()),
			success:function(result) {
				if(result == 'SUCCESS') {
					alert("등록 되었습니다.");
					$('#text').val('');
					printList();
				};
			}
		});
	});
	
	// read
	$(document).on('click', '.btnDetail', function(e) {
		console.log('click detail');
		var id = $(this).data('id');
		$.getJSON(defaultUrl+'/r/'+id, function(e) {
			alert(e);
		});
	});
	
	// remove
	$(document).on('click', '.btnRemove', function(e) {
		console.log('click remove');
		var id = $(this).data('id');
		$.ajax( {
			type:'delete',
			url:defaultUrl+'/'+id,
			headers: { 
			      "Content-Type": "application/json",
			      "X-HTTP-Method-Override": "DELETE" },
			dataType:'text', 
			success:function(result){
				console.log("result: " + result);
				if(result == 'SUCCESS') {
					alert("삭제 되었습니다.");
					printList();
				}
			}
		});		
	});
});

</script>  



</body>
</html>