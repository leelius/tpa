<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<jsp:include page="../inc/bsa_head.jsp"></jsp:include>
<!-- Custom Styles-->
<link href="bsat1/assets/css/custom-styles.css" rel="stylesheet" />
<title>${pagetitle}-${appName}${navitem}</title>
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
					<li><a href="superadmin/">首页</a></li>
					<li class="active">${pagetitle}</li>
				</ol>
			</div>
			<div id="page-inner">
				<!-- content start -->
				<c:choose>
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
										<form class="form-horizontal" method="post"
											action="${pagename}">
											<input type="hidden" name="operate" value="addform" />
											<div class="form-group">
												<label for="wechat_headimgurl"
													class="col-sm-2 control-label"><br> <br>
													<br>用户头像</label>
												<div class="col-sm-3">
													<img id="wechat_headimg" width="120px" alt="用户头像"
														src="${wechat_headimgurl}"> <input type="hidden"
														id="wechat_headimgurl" name="wechat_headimgurl">
												</div>
												<div class="col-sm-7">
													<!-- 模态框begin -->
													<!-- Button trigger modal -->
													<br> <br> <br>
													<button type="button" class="btn btn-success"
														data-toggle="modal" data-target="#myModal">更新头像</button>
													<!-- Modal -->
													<div class="modal fade" id="myModal" tabindex="-1"
														role="dialog" aria-labelledby="myModalLabel">
														<div class="modal-dialog" role="document">
															<div class="modal-content">
																<div class="modal-header">
																	<button type="button" class="close"
																		data-dismiss="modal" aria-label="Close">
																		<span aria-hidden="true">&times;</span>
																	</button>
																	<h4 class="modal-title" id="myModalLabel">更新用户头像</h4>
																</div>
																<div class="modal-body">
																	<!-- 模态框内容 begin  -->
																	<input type="file" id="uploadfile" name="uploadfile" />
																	<span id="uploadinfo"></span>
																	<!-- 模态框内容 end  -->
																</div>
																<div class="modal-footer">
																	<button type="button" class="btn btn-default"
																		data-dismiss="modal">取消</button>
																	<button type="button" class="btn btn-primary"
																		id="update_btn_wechat_headimgurl">更新</button>
																</div>
															</div>
														</div>
													</div>
													<!-- 模态框end -->
												</div>
											</div>
											<div class="form-group">
												<label for="name" class="col-sm-2 control-label">姓名</label>
												<div class="col-sm-10">
													<input type="text" class="form-control" id="name"
														name="name" placeholder="姓名">
												</div>
											</div>
											<div class="form-group">
												<label for="username" class="col-sm-2 control-label">手机号</label>
												<div class="col-sm-10">
													<input type="text" class="form-control" id="username"
														name="username" placeholder="手机号">
												</div>
											</div>
											<div class="form-group">
												<label for="is_frozen" class="col-sm-2 control-label">是否冻结</label>
												<div class="col-sm-10">
													<div
														class="checkbox3 checkbox-round checkbox-check checkbox-light">
														<input type="checkbox" id="is_frozen" name="is_frozen">
														<label for="is_frozen"> 冻结账号 </label>
													</div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">角色</label>
												<div class="col-sm-10">
													<c:forEach var="it" items="${rolelist}" varStatus="i">
														<div
															class="radio3 radio-check radio-inline radio-${tagpostfixlist[i.count%fn:length(tagpostfixlist)]}">
															<input type="radio" id="role_id_${i.count}"
																name="role_id" value="${it.id}"
																<c:if test="${i.count==1}">checked</c:if>> <label
																for="role_id_${i.count}"> ${it.name} </label>
														</div>
													</c:forEach>
												</div>
											</div>
											<div class="form-group">
												<label for="nation_id" class="col-sm-2 control-label">民族</label>
												<div class="col-sm-10">
													<select class="selectbox" id="nation_id" name="nation_id">
														<c:forEach var="it" items="${nationlist}" varStatus="i">
															<option value="${it.code}">${it.name}</option>
														</c:forEach>
													</select>
												</div>
											</div>
											<div class="form-group">
												<label for="intro" class="col-sm-2 control-label">个人简介(100字以内)</label>
												<div class="col-sm-10">
													<textarea class="form-control" id="intro" name="intro"
														rows="3" maxlength="100"></textarea>
												</div>
											</div>
											<div class="form-group">
												<div class="col-sm-offset-2 col-sm-10">
													<button type="submit" class="btn btn-success">新增</button>
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
										<form class="form-horizontal" method="post"
											action="${pagename}">
											<input type="hidden" name="operate" value="editform" /> <input
												type="hidden" name="id" value="${it.id}" />
											<div class="form-group">
												<label for="wechat_headimgurl"
													class="col-sm-2 control-label"><br> <br>
													<br>用户头像</label>
												<div class="col-sm-3">
													<img id="wechat_headimg" width="120px" alt="用户头像"
														src="${it.wechat_headimgurl}"> <input type="hidden"
														id="wechat_headimgurl" name="wechat_headimgurl"
														value="${it.wechat_headimgurl}">
												</div>
												<div class="col-sm-7">
													<!-- 模态框begin -->
													<!-- Button trigger modal -->
													<br> <br> <br>
													<button type="button" class="btn btn-success"
														data-toggle="modal" data-target="#myModal">更新头像</button>
													<!-- Modal -->
													<div class="modal fade" id="myModal" tabindex="-1"
														role="dialog" aria-labelledby="myModalLabel">
														<div class="modal-dialog" role="document">
															<div class="modal-content">
																<div class="modal-header">
																	<button type="button" class="close"
																		data-dismiss="modal" aria-label="Close">
																		<span aria-hidden="true">&times;</span>
																	</button>
																	<h4 class="modal-title" id="myModalLabel">更新用户头像</h4>
																</div>
																<div class="modal-body">
																	<!-- 模态框内容 begin  -->
																	<input type="file" id="uploadfile" name="uploadfile" />
																	<span id="uploadinfo"></span>
																	<!-- 模态框内容 end  -->
																</div>
																<div class="modal-footer">
																	<button type="button" class="btn btn-default"
																		data-dismiss="modal">取消</button>
																	<button type="button" class="btn btn-primary"
																		id="update_btn_wechat_headimgurl">更新</button>
																</div>
															</div>
														</div>
													</div>
													<!-- 模态框end -->
												</div>
											</div>

											<div class="form-group">
												<label for="name" class="col-sm-2 control-label">姓名</label>
												<div class="col-sm-10">
													<input type="text" class="form-control" id="name"
														name="name" placeholder="姓名" value="${it.name}">
												</div>
											</div>
											<div class="form-group">
												<label for="username" class="col-sm-2 control-label">手机号</label>
												<div class="col-sm-10">
													<input type="text" class="form-control" id="username"
														name="username" placeholder="手机号" value="${it.username}">
												</div>
											</div>
											<!-- checkbox3 begin -->
											<div class="form-group">
												<label for="username" class="col-sm-2 control-label">是否冻结</label>
												<div class="col-sm-10">
													<div
														class="checkbox3 checkbox-round checkbox-check checkbox-light">
														<c:choose>
															<c:when test="${it.is_frozen == true}">
																<input type="checkbox" id="is_frozen" name="is_frozen"
																	checked>
															</c:when>
															<c:when test="${it.is_frozen == false}">
																<input type="checkbox" id="is_frozen" name="is_frozen">
															</c:when>
														</c:choose>
														<label for="is_frozen"> 冻结账号 </label>
													</div>
												</div>
											</div>
											<!-- checkbox3 end -->
											<!-- jeDate begin -->
											<div class="form-group">
												<label for="gmt_create" class="col-sm-2 control-label">创建日期</label>
												<div class="col-sm-10">
													<div class="input-group">
														<span class="input-group-addon" style="cursor: pointer;"
															onclick="$.jeDate('#gmt_create_date',{trigger:false,format: 'YYYY-MM-DD hh:mm:ss'})">时间选择</span>
														<input placeholder="YYYY-MM-DD" type="text"
															class="form-control" id="gmt_create_date"
															name="gmt_create_date"
															value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss' value='${it.gmt_create_date}' />"
															readonly>
													</div>
												</div>
											</div>
											<!-- jeDate end -->
											<!-- jeDate begin -->
											<div class="form-group">
												<label for="gmt_create" class="col-sm-2 control-label">更新日期</label>
												<div class="col-sm-10">
													<div class="input-group">
														<span class="input-group-addon" style="cursor: pointer;"
															onclick="$.jeDate('#gmt_modified_date',{trigger:false,format: 'YYYY-MM-DD hh:mm:ss'})">时间选择</span>
														<input placeholder="YYYY-MM-DD" type="text"
															class="form-control" id="gmt_modified_date"
															name="gmt_modified_date"
															value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss' value='${it.gmt_modified_date}' />"
															readonly>
													</div>
												</div>
											</div>
											<!-- jeDate end -->
											<!-- radio3 begin -->
											<div class="form-group">
												<label class="col-sm-2 control-label">角色</label>
												<div class="col-sm-10">
													<c:forEach var="item" items="${rolelist}" varStatus="i">
														<div
															class="radio3 radio-check radio-inline radio-${tagpostfixlist[i.count%fn:length(tagpostfixlist)]}">
															<input type="radio" id="role_id_${i.count}"
																name="role_id" value="${item.id}"
																<c:if test="${item.id==it.role_id}">checked</c:if>>
															<label for="role_id_${i.count}"> ${item.name} </label>
														</div>
													</c:forEach>
												</div>
											</div>
											<!-- radio3 end -->
											<!-- selectbox begin -->
											<div class="form-group">
												<label for="nation_id" class="col-sm-2 control-label">民族</label>
												<div class="col-sm-10">
													<select class="selectbox" id="nation_id" name="nation_id">
														<c:forEach var="item" items="${nationlist}" varStatus="i">
															<option value="${item.code}"
																<c:if test="${item.id==it.nation_id}">selected</c:if>>${item.name}</option>
														</c:forEach>
													</select>
												</div>
											</div>
											<!-- selectbox end -->
											<!-- intro begin -->
											<div class="form-group">
												<label for="intro" class="col-sm-2 control-label">个人简介(100字以内)</label>
												<div class="col-sm-10">
													<textarea class="form-control" id="intro" name="intro"
														rows="3" maxlength="100">${it.intro}</textarea>
												</div>
											</div>
											<!-- intro end -->


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
					<c:when test="${operate == null}">

						<!-- Advanced Tables -->
						<div class="panel panel-default">
							<div class="panel-heading">${tabletitle}</div>
							<div class="row margin-horizontal-xs">
								<div class="col-xs-12 col-sm-6">
									<form class="form-inline">
										<div class="form-group">
											<a href="${pagename}?operate=add" class="btn btn-primary">新增</a>
										</div>
									</form>
								</div>
								<div class="col-xs-12 col-sm-6 text-right">
									<form class="form-inline">
										<c:if test="${kw != null}">
											<button type="button" id="canclesearch"
												class="btn btn-danger">清除搜索结果</button>
										</c:if>
										<div class="form-group">
											<input type="text" class="form-control" id="kw" name="kw"
												value="${kw}" placeholder="关键字">
										</div>
										<button type="submit" class="btn btn-info">搜索</button>
									</form>
								</div>
							</div>

							<!-- div表格  (中大屏幕) begin -->
							<div class="panel-body visible-md-block visible-lg-block">
								<div class="row bg-primary" style="line-height: 50px;">
									<div class="col-xs-6 col-sm-6 col-md-1 text-center">序号</div>
									<div class="col-xs-6 col-sm-6 col-md-3 text-center">用户信息</div>
									<div class="col-xs-6 col-sm-6 col-md-2 text-center">操作</div>
									<div class="col-xs-6 col-sm-6 col-md-2">手机号码</div>
									<div class="col-xs-6 col-sm-6 col-md-2">QQ</div>
								</div>
								<c:forEach var="it" items="${list}" varStatus="index">
									<div style="line-height: 40px;"
										<c:choose>
			<c:when test="${index.count%2==0}">
				class="row "
			</c:when>
			<c:when test="${index.count%2!=0}">
				class="row bg-info"
			</c:when>
		</c:choose>>
										<div class="col-xs-6 col-sm-6 col-md-1" id="t${it.id}">${index.count+(pager.pagenumber - 1) * pager.pagesize}</div>
										<img class="col-xs-6 col-sm-6 col-md-1"
											src="${it.wechat_headimgurl}">
										<div class="col-xs-6 col-sm-6 col-md-2">${it.rolename}<br>${it.name}</div>
										<div class="col-xs-6 col-sm-6 col-md-2">
											<button value="${it.id}" class="edititem btn btn-success">编辑</button>
											<button value="${it.id}" class="deleteitem btn btn-danger">删除</button>
										</div>

										<div class="col-xs-6 col-sm-6 col-md-2">${it.username}</div>
										<div class="col-xs-6 col-sm-6 col-md-2">${it.qq}</div>

									</div>
								</c:forEach>
							</div>
							<!-- div表格  (中大屏幕) end -->
							<!-- div表格  (小屏幕) begin -->
							<div class="panel-body visible-xs-block visible-sm-block">
								<c:forEach var="it" items="${list}" varStatus="index">
									<div class="row"
										style="line-height: 50px; border-bottom: 1px solid black;">
										<div class="col-xs-6 col-sm-6 bg-success">${it.rolename}:${it.name}</div>
										<div class="col-xs-6 col-sm-6">
											<button value="${it.id}" class="edititem btn btn-success">编辑</button>
											<button value="${it.id}" class="deleteitem btn btn-danger">删除</button>
										</div>
										<div class="col-xs-4 col-sm-4">TEL:${it.username}/QQ:${it.qq}</div>
									</div>
								</c:forEach>
							</div>
							<!-- div表格  (小屏幕) end -->

							<jsp:include page="../inc/pager.jsp"></jsp:include>
						</div>
						<!--End Advanced Tables -->
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
				$("#gmt_create_date").jeDate({
					format : "YYYY-MM-DD hh:mm:ss"
				});
				$("#gmt_modified_date").jeDate({
					format : "YYYY-MM-DD hh:mm:ss"
				});
				//------------
			});
		</script>
	</c:if>
</body>
</html>