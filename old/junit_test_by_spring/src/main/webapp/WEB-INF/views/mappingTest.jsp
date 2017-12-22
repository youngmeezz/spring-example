<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Mapping Test</title>
</head>
<body>

<form action="/mappings" method="POST">
	<table width="100%">
		<tr>
			<th>title</th>
			<td><input type="text" name="title"></td>
		</tr>
		<tr>
			<th>author.name</th>
			<td><input type="text" name="author.name"></td>
		</tr>
				
		<tr>
			<td colspan="2">
				<input type="submit" value="submit">
			</td>
		</tr>
		
						
	</table>
	
</form>


</body>
</html>