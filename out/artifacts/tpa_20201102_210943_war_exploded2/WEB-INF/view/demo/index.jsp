<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<jsp:include page="../inc/bsa_head.jsp"></jsp:include>
<!-- Custom Styles-->
<link href="bsat1/assets/css/custom-styles.css" rel="stylesheet" />
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
					${pagetitle} <small>${subtitle}</small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="${pagename}/">首页</a></li>
					<li class="active">${pagetitle}</li>
				</ol>
			</div>
			<div id="page-inner">
				<!-- content start -->
				<div class="row">
					<div class="col-md-3 col-sm-12 col-xs-12">
						<div class="board">
							<div class="panel panel-primary">
								<div class="number">
									<h3>
										<a target="_blank" href="demo/manageuser/">演示用户管理</a><br>
										<small>演示一个表的增删改查操作</small>
									</h3>
								</div>
								<div class="icon">
									<i class="fa fa-eye fa-5x red"></i>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-12 col-xs-12">
						<div class="board">
							<div class="panel panel-primary">
								<div class="number">
									<h3>
                                        <a target="_blank" href="demo/websocket/">演示聊天室</a><br>
                                        <small>WebSocket长连接，信息转发</small>
									</h3>
								</div>
								<div class="icon">
									<i class="fa fa-shopping-cart fa-5x blue"></i>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-12 col-xs-12">
						<div class="board">
							<div class="panel panel-primary">
								<div class="number">
									<h3>
                                        <a target="_blank" href="demo/ajax/">演示异步通信</a><br>
                                        <small>Ajax与通讯并修改页面</small>
									</h3>
								</div>
								<div class="icon">
									<i class="fa fa-comments fa-5x green"></i>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-12 col-xs-12">
						<div class="board">
							<div class="panel panel-primary">
								<div class="number">
									<h3>
                                        <a target="_blank" href="demo/download/">演示文件下载</a><br>
                                        <small>隐藏文件真实地址，实现验证身份后下载</small>
									</h3>
								</div>
								<div class="icon">
									<i class="fa fa-user fa-5x yellow"></i>
								</div>
							</div>
						</div>
					</div>
                    <div class="col-md-3 col-sm-12 col-xs-12">
                        <div class="board">
                            <div class="panel panel-primary">
                                <div class="number">
                                    <h3>
                                        <a target="_blank" href="services/DemoWebService?wsdl">演示WebService</a><br>
                                        <small>通过Axis2实现，带身份验证<br>DemoWebserviceByAxis2</small>
                                    </h3>
                                </div>
                                <div class="icon">
                                    <i class="fa fa-user fa-5x yellow"></i>
                                </div>
                            </div>
                        </div>
                    </div>
					
					
				</div>
				<!-- content end -->
				<jsp:include page="../inc/bsa_foot.jsp"></jsp:include>
			</div>
			<!-- /. PAGE INNER  -->
		</div>
		<!-- /. PAGE WRAPPER  -->
	</div>
	<!-- /. WRAPPER  -->
</body>

</html>