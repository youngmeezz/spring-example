<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
</head>
<body>

<form id="excelDownForm" action="/excel-down/test" method="POST">
</form>

<ul>
	<li>
		<a href="/excel-down/xls">XLS Download by reflection</a>
	</li>
	<li>
		<a href="/excel-down/xlsView-basic">XLS Download by default</a>
	</li>
	<li>
		<a href="/excel-down/xlsx">XLSX Download</a>
	</li>
	<li>
		<a href="/excel-down/xlsxStream">Xlsx Streaming Download</a>
	</li>
	<li>
		<button type="button" id="excelDownBtn">ExcelDown</button>
	</li>	
</ul>

<script>
	$(function() {
		var url = '/excel-down/test';
		$('#excelDownBtn').on('click', function(e) {
			var formObj = $('#excelDownForm');
			var html = "<input type='hidden' name='type' value='" + 'test' + "'>"
			formObj.append(html).submit().empty();
		});		
	});
</script>


</body>
</html>
