<!------------------------------->
<!---- Generated By Denny(luhox@qq.com), cslite for sm -->
<!---- Remarks: ItemListHTML-->
<!---- Date Generated: 2018-07-02 11:17:29-->
<!------------------------------->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:set var="searchpanel" value="true"></c:set>
<c:set var="uploadfile" value="true"></c:set>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<jsp:include page="../inc/bsa_head.jsp"></jsp:include>
<!-- Custom Styles-->
<link href="bssets/css/bsat1-style.css" rel="stylesheet" />
<title>${pagetitle}-${appName}</title>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="../inc/bsa_navtop.jsp"></jsp:include>
		<!--/. NAV TOP  -->
		<jsp:include page="../inc/bsa_navside.jsp"></jsp:include>
		<!-- /. NAV SIDE  -->
		<div id="page-wrapper">
			<div class="header">
				<h1 class="page-header">
					${pagetitle}
				</h1>
			</div>
			<div id="page-inner">
				<!-- content start -->

				<div class="row">
					<div class="col-xs-12">
						<div class="panel panel-default">
							<div class="panel-heading">
								<div class="card-title">
									<div class="title">${formtitle}</div>
								</div>
							</div>
							<div class="panel-body">
								<form id="form1" class="form-horizontal" method="post" action="${pagename}">
<%--
									<div class="form-group">
										<label for="password" class="col-sm-2 control-label">原密码</label>
										<div class="col-sm-10">
											<input type="password" class="form-control" id="password"
												name="password" placeholder="原密码" autocomplete="off">
										</div>
									</div>
 --%>									
									<div class="form-group">
										<label for="password2" class="col-sm-2 control-label">新密码</label>
										<div class="col-sm-10">
											<input type="password" class="form-control" id="password2"
												name="password2" placeholder="新密码" autocomplete="off">
										</div>
									</div>
									<div class="form-group">
										<label for="password3" class="col-sm-2 control-label">再次输入</label>
										<div class="col-sm-10">
											<input type="password" class="form-control" id="password3"
												name="password3" placeholder="再次输入" autocomplete="off">
										</div>
									</div>

									<div class="form-group">
										<div class="col-sm-offset-2 col-sm-10">
											<button type="submit" class="btn btn-success">更新密码</button>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
				<!-- content end -->
				<jsp:include page="../inc/bsa_foot.jsp"></jsp:include>
			</div>
			<!-- /. PAGE INNER  -->
		</div>
	</div>


	<script type="text/javascript">
		$(document).ready(function() {
			$("#form1").submit(function(e) {
/* 				if ($("#password").val() == '') {
					alert("原密码必须输入！");
					$("#password").focus();
					return false;
				} */
				if ($("#password2").val() == '') {
					alert("新密码必须输入！");
					$("#password2").focus();
					return false;
				}
				if ($("#password3").val() == '') {
					alert("新密码必须再次输入！");
					$("#password3").focus();
					return false;
				}
			});
		});
	</script>


</body>
</html>


