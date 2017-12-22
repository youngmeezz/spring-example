<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../include/header.jsp" %>
<!-- Main content -->
<section class="content">
	<div class="row">
		<!-- left column -->
		<div class="col-md-12">
			<!-- general form elements -->
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">REGISTER BOARD</h3>
				</div>
				<!-- /.box-header -->
				
<form role="form" method="post">
	<input type='hidden' name="bno" value="${boardVO.bno}">
	<input type='hidden' name="page" value="${cri.page}">
	<input type='hidden' name="perPageNum" value="${cri.perPageNum}">
</form>

<div class="box-body">
	<div class="form-group">
		<label for="exampleInputEamil1">Title</label>
		<input type="text" name="title" class="form-controll" value="${boardVO.title }" readonly="readonly">	
	</div>
	
	<div class="form-group">
		<label for="exampleInputPassword1">Content</label>
		<textarea class="form-control" name="content" rows="3" readonly="readonly">${boardVO.content}</textarea>	
	</div>
	
	<div class="form-group">
		<label for="exampleInputEamil1">Writer</label>
		<input type="text" name="writer" class="form-controll" value="${boardVO.writer }" readonly="readonly">	
	</div>
	
	<!-- /.box-body -->
	
	<div class="box-footer">
		<button type="submit" class="btn btn-warning">MODOFY</button>
		<button type="submit" class="btn btn-danger">REMOVE</button>
		<button type="submit" class="btn btn-primary">LIST ALL</button>
	</div>

</div>

<script>
	$(document).ready(function(){
		var formObj = $("form[role='form']");
		
		console.log(formObj);
		
		$(".btn-warning").on("click",function(){
			formObj.attr("action","/board/modifyPage");
			formObj.attr("method","get");
			formObj.submit();
		});
		
		$(".btn-danger").on("click",function(){			
			formObj.attr("action","/board/removePage");			
			formObj.submit();
		});
		
		$(".btn-primary").on("click",function(){
			formObj.attr("method","get");
			formObj.attr("action","/board/listPage");
			formObj.submit();
		});
		
	});
</script>

<%--

var formObj = $("form[role='form']");
->formObj 는
<form role="form" method="post">
	<input type="hidden" name="bno" value="${boardVO.bno}">
</form>

 --%>


			</div>
			<!-- /.box -->
		</div>
		<!--/.col (left) -->

	</div>
	<!-- /.row -->
</section>
<!-- /.content -->
</div>
<!-- /.content-wrapper -->

<%@ include file="../include/footer.jsp" %>












