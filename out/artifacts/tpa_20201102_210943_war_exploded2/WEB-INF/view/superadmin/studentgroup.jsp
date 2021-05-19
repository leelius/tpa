<!------------------------------->
<!---- Generated By Denny(luhox@qq.com), cslite for sm -->
<!---- Remarks: ItemListHTML-->
<!---- Date Generated: 2019-06-28 21:17:00-->
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
				<c:choose>
					<c:when test="${operate == null}">

						<div class="panel panel-default">
							<div class="row margin-xs">
								<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
									<div>
										<a href="${pagename}?operate=add" class="btn btn-primary">新增</a>
										&nbsp;&nbsp;&nbsp;
										<button class="btn btn-success" type="button"
											data-toggle="collapse" data-target="#searchPanel"
											aria-expanded="false" aria-controls="searchPanel">
											筛选条件</button>
									</div>
								</div>
								<c:if test="${searchpanel=='true'}">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
										<div class="row">
											<div class="col-sm-12">
												<div id="searchPanel"
													<c:if test="${kw != null }">class="collapse in form-inline" </c:if>
													<c:if test="${kw == null }">class="collapse form-inline" </c:if>>
													<div class="well">
														<!-- 面板 -->
														<div class="row">
															<div
																class="col-xs-4 col-sm-3 col-md-2 col-lg-2 text-right margin-top-xs">
																模糊查询：</div>
															<div class="col-xs-6 col-sm-4 col-md-4 col-lg-3">
																<div class="form-group">
																	<input style="width: 100%;" type="text"
																		class="form-control" id="kw" name="kw" value="${kw}"
																		placeholder="模糊查询">
																</div>
															</div>
														</div>
														<div class="clearfix showborder-top"></div>
														<div class="clearfix"></div>
														<div class="row">
															<div class="col-xs-12 col-sm-12 col-md-4 col-lg-3">
																<button id="searchbtn" class="btn btn-warning">搜索</button>
															</div>
															<div class="col-xs-12 col-sm-12 col-md-4 col-lg-3">
																<button class="btn btn-danger canclesearch">清除搜索条件</button>
															</div>
														</div>
														<!-- /面板 -->
													</div>
												</div>
											</div>
										</div>
									</div>
								</c:if>
							</div>
							<jsp:include page="../inc/pager.jsp"></jsp:include>
							<!-- div表格  (中大屏幕，表格显示) begin -->
							<!-- Advanced Tables -->
							<div
								class="panel panel-default  visible-md-block visible-lg-block">
								<!-- <div class="panel-heading">${tabletitle}</div> -->
								<div class="row">
									<div class="col-md-12">
										<div class="panel-body">
											<div class="table-responsive">
												<table
													class="table table-striped table-bordered table-hover"
													id="dataTables-example">
													<thead>
														<tr class="bg-blue">
															<th>序号</th>
															<th>可登录</th>
															<th>班级编号</th>
															<th>班级名称</th>
															<th>操作</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach var="it" items="${list}" varStatus="index">
															<tr>
																<td id="t${it.id}">${index.count+(pager.current - 1) * pager.size}</td>
																<td><c:choose>
																		<c:when test="${it.is_actived}">
																			<a
																				href="${basePath}${pagename}?operate=hideitem&id=${it.id}"><img
																				alt="" src="bssets/i/greendot.png"></a>
																		</c:when>
																		<c:when test="${it.is_actived==false}">
																			<a
																				href="${basePath}${pagename}?operate=showitem&id=${it.id}"><img
																				alt="" src="bssets/i/reddot.png"></a>
																		</c:when>
																	</c:choose></td>
																<td>${it.code}</td>
																<td>${it.name}</td>
																<td><a
																	href="${basePath}superadmin/student?sgid=${it.id}"
																	class="btn btn-info">(查看/添加)学生名单</a> <a
																	href="${basePath}${pagename}?operate=edit&id=${it.id}"
																	class="btn btn-sm btn-success">编辑</a>
																	<button value="${it.id}"
																		class="deleteitem btn btn-sm btn-danger">删除</button></td>
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
							<!-- div表格  (小屏幕，类卡片显示) begin -->
							<div class="panel-body visible-xs-block visible-sm-block">
								<div class="row">
									<c:forEach var="it" items="${list}" varStatus="index">
										<div class="col-xs-12 col-sm-6">
											<div class="row showbd margin-xs">
												<div class="col-xs-12 col-sm-12 bg-primary padding-xs"
													id="t${it.id}">
													<h3>${index.count+(pager.current - 1) * pager.size}</h3>
												</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.id}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.is_actived}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.code}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.name}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">
													<c:if test="${it.create_gmt !=0}">
														<fmt:formatDate pattern="yyyy-MM-dd"
															value="${it.create_gmt_date}" />
													</c:if>
												</div>
												<div class="clearfix"></div>
												<div class="col-xs-12 col-sm-12">
													<c:if test="${it.update_gmt !=0}">
														<fmt:formatDate pattern="yyyy-MM-dd"
															value="${it.update_gmt_date}" />
													</c:if>
												</div>
												<div class="clearfix"></div>
												<div class="col-xs-12 col-sm-12">
													<a href="${basePath}${pagename}?operate=edit&id=${it.id}"
														class="btn btn-sm btn-success">编辑</a>
													<button value="${it.id}" class="deleteitem btn btn-danger">删除</button>
												</div>
											</div>
										</div>
									</c:forEach>
								</div>
							</div>
							<!-- div表格  (小屏幕，类卡片显示) end -->
						</div>
						<!--End div Tables -->
					</c:when>
					<c:when test="${operate == 'add'}">
						<div class="row">
							<div class="col-xs-12">
								<div class="panel panel-default">
									<div class="panel-heading">
										<div class="card-title">
											<div class="title">${formtitle}</div>
										</div>
									</div>
									<div class="panel-body">
										<a class="btn btn-warning" href="${pagename}">放弃保存，直接返回</a>
										<form id="form1" class="form-horizontal" method="post"
											action="${pagename}">
											<input type="hidden" name="operate" value="addform" /> <input
												type="hidden" name="referer" value="${referer}" />
											<div class="form-group">
												<label for="is_actived" class="col-sm-2 control-label">是否可以登录</label>
												<div class="col-sm-10">
													<div
														class="checkbox3 checkbox-round checkbox-check checkbox-light">
														<input type="checkbox" id="is_actived" name="is_actived">
														<label for="is_actived"> 是否可以登录 </label>
													</div>
												</div>
											</div>
											<div class="form-group">
												<label for="code" class="col-sm-2 control-label">分组编号，8位字符</label>
												<div class="col-sm-10">
													<input type="text" class="form-control" id="code"
														name="code" placeholder="分组编号，8位字符">
												</div>
											</div>
											<div class="form-group">
												<label for="name" class="col-sm-2 control-label">组名</label>
												<div class="col-sm-10">
													<input type="text" class="form-control" id="name"
														name="name" placeholder="组名">
												</div>
											</div>
											<div class="form-group">
												<div class="col-sm-offset-2 col-sm-10">
													<button type="submit" class="btn btn-success">添加信息</button>
												</div>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>
					</c:when>
					<c:when test="${operate == 'edit'}">
						<div class="row">
							<div class="col-xs-12">
								<div class="panel panel-default">
									<div class="panel-heading">
										<div class="card-title">
											<div class="title">${formtitle}</div>
										</div>
									</div>
									<div class="panel-body">
										<a class="btn btn-warning" href="${pagename}">放弃保存，直接返回</a>
										<form id="form1" class="form-horizontal" method="post"
											action="${pagename}">
											<input type="hidden" name="operate" value="editform" /> <input
												type="hidden" name="id" value="${it.id}" /> <input
												type="hidden" name="referer" value="${referer}" />
											<div class="form-group">
												<label for="is_actived" class="col-sm-2 control-label">是否可以登录</label>
												<div class="col-sm-10">
													<div
														class="checkbox3 checkbox-round checkbox-check checkbox-light">
														<c:choose>
															<c:when test="${it.is_actived == true}">
																<input type="checkbox" id="is_actived" name="is_actived"
																	checked>
															</c:when>
															<c:when test="${it.is_actived == false}">
																<input type="checkbox" id="is_actived" name="is_actived">
															</c:when>
														</c:choose>
														<label for="is_actived"> 是否可以登录 </label>
													</div>
												</div>
											</div>
											<div class="form-group">
												<label for="code" class="col-sm-2 control-label">分组编号，8位字符</label>
												<div class="col-sm-10">
													<input type="text" class="form-control" id="code"
														name="code" placeholder="分组编号，8位字符" value="${it.code}">
												</div>
											</div>
											<div class="form-group">
												<label for="name" class="col-sm-2 control-label">组名</label>
												<div class="col-sm-10">
													<input type="text" class="form-control" id="name"
														name="name" placeholder="组名" value="${it.name}">
												</div>
											</div>
											<div class="form-group d_t_dater">
												<label for="create_gmt_date" class="col-sm-2 control-label">创建时间</label>
												<div class="col-sm-10">
													<div class="input-group">
														<span class="form-control-nobd "> <fmt:formatDate
																pattern='yyyy-MM-dd HH:mm:ss'
																value='${it.create_gmt_date}' />
														</span>
													</div>
												</div>
											</div>
											<div class="form-group d_t_dater">
												<label for="update_gmt_date" class="col-sm-2 control-label">修改时间</label>
												<div class="col-sm-10">
													<div class="input-group">
														<span class="form-control-nobd " readonly><c:if
																test="${it.update_gmt != 0}">
																<fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss'
																	value='${it.update_gmt_date}' />
															</c:if> </span>
													</div>
												</div>
											</div>
											<div class="form-group">
												<div class="col-sm-offset-2 col-sm-10">
													<button type="submit" class="btn btn-success">更新</button>
												</div>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>
					</c:when>
				</c:choose>
				<!-- content end -->
				<jsp:include page="../inc/bsa_foot.jsp"></jsp:include>
			</div>
			<!-- /. PAGE INNER  -->
		</div>
	</div>
	<c:if test="${operate != null}">
		<script type="text/javascript" src="bssets/js/uploadheadimg.js"></script>
		<script type="text/javascript" src="bssets/jedate/jquery.jedate.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				//------------
				$("#create_gmt_date").jeDate({
					format : "YYYY-MM-DD hh:mm:ss"
				});
				$("#update_gmt_date").jeDate({
					format : "YYYY-MM-DD hh:mm:ss"
				});
				//------------
				$("#form1").submit(function(e) {
					if ($("#id").val() == '') {
						alert("主键必须输入！");
						$("#id").focus();
						return false;
					}
					if ($("#is_actived").val() == '') {
						alert("是否可以登录必须输入！");
						$("#is_actived").focus();
						return false;
					}
					if ($("#code").val() == '') {
						alert("分组编号，8位字符必须输入！");
						$("#code").focus();
						return false;
					}
					if ($("#name").val() == '') {
						alert("组名必须输入！");
						$("#name").focus();
						return false;
					}
					if ($("#create_gmt_date").val() == '') {
						alert("创建时间必须输入！");
						$("#create_gmt_date").focus();
						return false;
					}
					if ($("#update_gmt_date").val() == '') {
						alert("修改时间必须输入！");
						$("#update_gmt_date").focus();
						return false;
					}

				});
			});
		</script>
	</c:if>
	<c:if test="${operate == null}">
		<script type="text/javascript">
			//搜索框
			$("#searchbtn").click(function() {
				var kw = $.trim($("#kw").val());
				var qs = "";
				var and = "?";
				if (kw != '') {
					qs += and + "kw=" + kw;
					and = "&";
				}
				$(location).attr('href', '${basePath}${pagename}' + qs);
			});
		</script>
	</c:if>

</body>
</html>

