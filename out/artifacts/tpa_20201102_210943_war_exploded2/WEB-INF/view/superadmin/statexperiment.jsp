<!------------------------------->
<!---- Generated By Denny(luhox@qq.com), cslite for sm -->
<!---- Remarks: ItemListHTML-->
<!---- Date Generated: 2019-06-25 19:58:58-->
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
				<h3 class="page-header">
					${pagetitle} <small>${subtitle}</small>
				</h3>
			</div>
			<div id="page-inner">
				<!-- content start -->
				<!-- div Tables -->
				<div class="panel panel-default">
					<!-- div表格  (中大屏幕，表格显示) begin -->
					<!-- Advanced Tables -->
					<div class="panel panel-default ">
						<!-- <div class="panel-heading">${tabletitle}</div> -->
						<div class="row">
							<div class="col-md-12">
								<div class="panel-body">
									<div class="table-responsive">
										<table class="table table-striped table-bordered table-hover"
											id="dataTables-example">
											<thead>
												<tr class="bg-blue">
													<th>序号</th>
													<th>课程id</th>
													<th>分组名称</th>
													<th>学期编号</th>
													<th>科目</th>
													<th>可用</th>
													<th>实验数量</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="it" items="${list}" varStatus="index">
													<tr>
														<td>${index.count+(pager.current - 1) * pager.size}</td>
														<td>${it.curriculum_id}</td>
														<td>${it.student_group_name}</td>
														<td>${it.semester}</td>
														<td>${it.subject_name}</td>
														<td><c:choose>
																<c:when test="${it.experiment_curriculum_is_actived}">
																	<a
																		href="${basePath}${pagename}?operate=hideitem&id=${it.curriculum_id}"><img
																		alt="" src="bssets/i/greendot.png"></a>
																</c:when>
																<c:when
																	test="${it.experiment_curriculum_is_actived==false}">
																	<a
																		href="${basePath}${pagename}?operate=hideitem&id=${it.curriculum_id}"><img
																		alt="" src="bssets/i/reddot.png"></a>
																</c:when>
															</c:choose></td>
														<td>${it.num}</td>
														<td>
<a href="${basePath}superadmin/statexprecordsubmit?cid=${it.curriculum_id}"
	class="btn btn-sm btn-success">查看统计</a>
	
	
	</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!--End Advanced Tables -->
					<!-- div表格  (中大屏幕，表格显示) end -->
				</div>
				<!--End div Tables -->
				<!-- content end -->
				<jsp:include page="../inc/bsa_foot.jsp"></jsp:include>
			</div>
			<!-- /. PAGE INNER  -->
		</div>
	</div>
</body>
</html>


