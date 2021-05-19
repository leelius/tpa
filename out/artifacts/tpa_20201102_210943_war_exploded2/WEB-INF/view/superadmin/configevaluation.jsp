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
							<div class="col-md-2 text-right">所属科目</div>
							<div class="col-md-10">${evaluation.subject_name}</div>
							<div class="col-md-2 text-right">考核标题</div>
							<div class="col-md-10">${evaluation.title}</div>
							<div class="col-md-2 text-right">考核内容</div>
							<div class="col-md-10">${evaluation.content}</div>
							<div class="col-md-2 text-right">考核时间</div>
							<div class="col-md-10">
								<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
									value="${evaluation.begin_gmt_date}" />
								~
								<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
									value="${evaluation.end_gmt_date}" />
							</div>
						</div>
						<div
							class="panel panel-default  visible-md-block visible-lg-block">
							<div class="row">
								<div class="col-md-12">
									<button class="builditems btn btn-primary">
										<span class="glyphicon glyphicon-refresh"></span> 生成全部未考试题
									</button>
									<a class="btn btn-success" href="${basePath}${pagename}?operate=cena&id=${param.id}">
										<span class="glyphicon glyphicon-copyright-mark"></span> 读取Cena离线评测结果
									</a>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="panel-body">
										<div class="table-responsive">
											<table class="table table-striped table-bordered table-hover"
												id="dataTables-example">
												<thead>
													<tr class="bg-blue">
														<th>序号</th>
														<th>学号</th>
														<th>姓名</th>
														<th>班级号</th>
														<th>题量</th>
														<th>难度</th>
														<th>满分</th>
														<th>成绩</th>
														<th>操作</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="it" items="${list}" varStatus="index">
														<tr>
															<td id="t${it.id}">${index.count}</td>
															<td>${it.sno}</td>
															<td>${it.alias}</td>
															<td>${it.classcode}</td>
															<td>${it.evpNumber}</td>
															<td>${it.difficulty}</td>
															<td>${it.scoreWish}</td>
															<td><c:choose>
																	<c:when test="${it.score ==-99}">
																		<span class="text-danger am-text-lg">待评</span>
																		<a class="btn btn-danger"
																			href="${basePath}superadmin/evaluationreview?evid=${evaluation.id}&studentid=${it.id}#t3">评阅</a>
																	</c:when>
																	<c:when test="${it.score ==null}">
																	尚未完成考试
<a class="btn btn-primary" href="${basePath}superadmin/takebackevaluation/${evaluation.id}?studentid=${it.id}">回收试卷</a>
																	</c:when>
																	<c:when test="${it.score !=-99}">
																		<span class="text-danger am-text-lg">${it.score}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

																		</c:when>
																</c:choose></td>
															<td>
																<button value="${it.id}"
																	class="builditem btn btn-success">重新生成</button> <c:if
																	test="${it.score !=-99}">
																	<a class="btn btn-warning"
																		href="${basePath}superadmin/evaluationreview?evid=${evaluation.id}&studentid=${it.id}#t2">重新评阅</a>
																</c:if>
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
	<script type="text/javascript">
		$(".builditems")
				.click(
						function() {
							if (confirm('确定要重新生成全部尚未考试考生的试卷吗？')) {
								$(location)
										.attr('href',
												'${basePath}${pagename}?operate=builditems&id=${param.id}');
							}
						});
		$(".builditem").click(
				function() {
					if (confirm('确定要重新生成指定考生的试卷吗？')) {
						$(location).attr(
								'href',
								'${basePath}${pagename}?operate=builditem&id=${param.id}&uid='
										+ $(this).val());
					}
				});
		$(".viewitem").click(
				function() {
					$(location).attr(
							'href',
							'${basePath}${pagename}?operate=viewitem&id=${param.id}&uid='
									+ $(this).val());
				});
	</script>
</body>
</html>


