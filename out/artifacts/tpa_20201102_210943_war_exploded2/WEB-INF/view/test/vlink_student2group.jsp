<!------------------------------->
<!---- Generated By Denny(luhox@qq.com), cslite for sm -->
<!---- Remarks: ItemListHTML-->
<!---- Date Generated: 2020-10-09 10:48:00-->
<!------------------------------->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:set var="searchpanel" value="true"></c:set>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<jsp:include page="../inc/bsa_head.jsp"></jsp:include>
<link href="bssets/css/bsat1-white.css" rel="stylesheet" />
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

						<!-- div Tables -->
						<div class="panel panel-default">
							<!--  <div class="panel-heading">${tabletitle}</div> -->
							<div class="row margin-xs">
								<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
										<div>
				<button class="btn btn-success" type="button"
					data-toggle="collapse" data-target="#searchPanel"
					aria-expanded="false" aria-controls="searchPanel">
					筛选条件</button>
					<a href="${pagename}?operate=add" class="btn btn-primary">新增</a>
										</div>
								</div>
<c:if test="${searchpanel=='true'}">
	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		<div class="row">
			<div class="col-sm-12">
				<div id="searchPanel"
					<c:if test="${kw != null  || student_id_map != null  || student_group_id_map != null }">class="collapse in form-inline" </c:if>
					<c:if test="${kw == null  && student_id_map == null  && student_group_id_map == null }">class="collapse form-inline" </c:if>>
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
						<!-- 搜索条件：用户id -->
						<div class="row">
							<div class="col-xs-4 col-sm-3 col-md-2 col-lg-2 text-right margin-top-xs">
								用户:</div>
							<c:forEach var="item" items="${studentlist}" varStatus="i">
<!-- checkbox begin -->
								<div class="col-xs-4 col-sm-3 col-md-3 col-lg-3">
									<div class="checkbox3 checkbox-${tagpostfixlist[i.count%fn:length(tagpostfixlist)]} checkbox-check checkbox-light">
										<input value="${item.id}" id="student_id${i.count}"
class="student_id_ck" type="checkbox"
<c:if test="${student_id_map.get(item.id) != null}">checked</c:if>>
										<label for="student_id${i.count}"> ${item.name} </label>
									</div>
								</div>
<!-- checkbox end -->
<!-- radio begin -->
			<div class="radio3 radio-check radio-${tagpostfixlist[i.count%fn:length(tagpostfixlist)]} radio-inline">
				<input type="radio" id="student_id${i.count}" name="student_id" value="${item.id}" <c:if test="${student_id_map.get(item.id) != null}">checked</c:if>> <label for="student_id${i.count}">${item.name}</label>
			</div>
<!-- radio end -->
								<div class="visible-lg-block">
									<c:if test="${i.count % 3==0}">
										<div class="clearfix"></div>
										<div class="col-lg-2"></div>
									</c:if>
								</div>
								<div class="visible-md-block">
									<c:if test="${i.count % 3==0}">
										<div class="clearfix"></div>
										<div class="col-md-2"></div>
									</c:if>
								</div>
								<div class="visible-sm-block">
									<c:if test="${i.count % 3==0}">
										<div class="clearfix"></div>
										<div class="col-sm-3"></div>
									</c:if>
								</div>
								<div class="visible-xs-block">
									<c:if test="${i.count % 2==0}">
										<div class="clearfix"></div>
										<div class="col-xs-4"></div>
									</c:if>
								</div>
							</c:forEach>
						</div>
						<div class="clearfix showborder-top"></div>
						<!-- /搜索条件：用户id -->
						<!-- 搜索条件：分组id -->
						<div class="row">
							<div class="col-xs-4 col-sm-3 col-md-2 col-lg-2 text-right margin-top-xs">
								分组:</div>
							<c:forEach var="item" items="${student_grouplist}" varStatus="i">
<!-- checkbox begin -->
								<div class="col-xs-4 col-sm-3 col-md-3 col-lg-3">
									<div class="checkbox3 checkbox-${tagpostfixlist[i.count%fn:length(tagpostfixlist)]} checkbox-check checkbox-light">
										<input value="${item.id}" id="student_group_id${i.count}"
class="student_group_id_ck" type="checkbox"
<c:if test="${student_group_id_map.get(item.id) != null}">checked</c:if>>
										<label for="student_group_id${i.count}"> ${item.name} </label>
									</div>
								</div>
<!-- checkbox end -->
<!-- radio begin -->
			<div class="radio3 radio-check radio-${tagpostfixlist[i.count%fn:length(tagpostfixlist)]} radio-inline">
				<input type="radio" id="student_group_id${i.count}" name="student_group_id" value="${item.id}" <c:if test="${student_group_id_map.get(item.id) != null}">checked</c:if>> <label for="student_group_id${i.count}">${item.name}</label>
			</div>
<!-- radio end -->
								<div class="visible-lg-block">
									<c:if test="${i.count % 3==0}">
										<div class="clearfix"></div>
										<div class="col-lg-2"></div>
									</c:if>
								</div>
								<div class="visible-md-block">
									<c:if test="${i.count % 3==0}">
										<div class="clearfix"></div>
										<div class="col-md-2"></div>
									</c:if>
								</div>
								<div class="visible-sm-block">
									<c:if test="${i.count % 3==0}">
										<div class="clearfix"></div>
										<div class="col-sm-3"></div>
									</c:if>
								</div>
								<div class="visible-xs-block">
									<c:if test="${i.count % 2==0}">
										<div class="clearfix"></div>
										<div class="col-xs-4"></div>
									</c:if>
								</div>
							</c:forEach>
						</div>
						<div class="clearfix showborder-top"></div>
						<!-- /搜索条件：分组id -->

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
						<div class="panel panel-default  visible-md-block visible-lg-block">
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
														<th>主键</th>
														<th>用户id</th>
														<th>分组id</th>
														<th>冻结账号</th>
														<th>是否可以登录</th>
														<th>学号</th>
														<th>用户名称</th>
														<th>电子邮件</th>
														<th>手机号码</th>
														<th>QQ号码</th>
														<th>学校的班级号，不是系统内的班级号</th>
														<th>状态：用户名密码登录0,微信登录1,两者同时可用2</th>
														<th>组名</th>
														<th>分组编号，8位字符</th>
														<th>操作</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="it" items="${list}" varStatus="index">
														<tr>
															<td id="t${it.id}">${index.count+(pager.current - 1) * pager.size}</td>
															<td>${it.id}</td>
															<td>${studentmap[it.student_id]}</td>
															<td>${student_groupmap[it.student_group_id]}</td>
															<td>
	<c:choose>
		<c:when test="${it.student_is_actived}">
			<a href="${basePath}${pagename}?operate=hideitem&id=${it.id}" ><img alt="" src="bssets/i/greendot.png"></a>
		</c:when>
		<c:when test="${it.student_is_actived==false}">
			<a href="${basePath}${pagename}?operate=showitem&id=${it.id}" ><img alt="" src="bssets/i/reddot.png"></a>
		</c:when>
	</c:choose>
</td>
															<td>
	<c:choose>
		<c:when test="${it.student_group_is_actived}">
			<a href="${basePath}${pagename}?operate=hideitem&id=${it.id}" ><img alt="" src="bssets/i/greendot.png"></a>
		</c:when>
		<c:when test="${it.student_group_is_actived==false}">
			<a href="${basePath}${pagename}?operate=showitem&id=${it.id}" ><img alt="" src="bssets/i/reddot.png"></a>
		</c:when>
	</c:choose>
</td>
															<td>${it.sno}</td>
															<td>${it.name}</td>
															<td>${it.email}</td>
															<td>${it.mobile}</td>
															<td>${it.qq}</td>
															<td>${it.classcode}</td>
															<td>${it.login_method}</td>
															<td>${it.student_group_name}</td>
															<td>${it.student_group_code}</td>
															<td>
<a href="${basePath}${pagename}?operate=moveup&id=${it.id}" class="btn btn-sm btn-info" <c:if test="${index.count==1}">disabled</c:if>>上移</a>
<a href="${basePath}${pagename}?operate=movedown&id=${it.id}" class="btn btn-sm btn-warning" <c:if test="${index.count==pager.total}">disabled</c:if>>下移</a>
<a href="${basePath}${pagename}?operate=edit&id=${it.id}" class="btn btn-sm btn-success">编辑</a>
<button value="${it.id}" class="deleteitem btn btn-sm btn-danger">删除</button>
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
<!-- div表格  (小屏幕，类卡片显示) begin -->
<div class="panel-body visible-xs-block visible-sm-block">
<div class="row">
	<c:forEach var="it" items="${list}" varStatus="index">
		<div class="col-xs-12 col-sm-6">
			<div class="row showbd margin-xs borderradius05">
				<div class="col-xs-12 col-sm-12 bg-primary padding-xs borderradius05" id="t${it.id}"><h3>${index.count+(pager.current - 1) * pager.size}</h3></div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.id}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.student_id}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.student_group_id}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.student_is_actived}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.student_group_is_actived}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.sno}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.name}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.email}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.mobile}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.qq}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.classcode}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.login_method}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.student_group_name}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.student_group_code}</div>
				<div class="clearfix"></div>

				<div class="col-xs-12 col-sm-12">
<a href="${basePath}${pagename}?operate=moveup&id=${it.id}" class="btn btn-sm btn-info" <c:if test="${index.count==1}">disabled</c:if>>上移</a>
<a href="${basePath}${pagename}?operate=movedown&id=${it.id}" class="btn btn-sm btn-warning" <c:if test="${index.count==pager.total}">disabled</c:if>>下移</a>
<a href="${basePath}${pagename}?operate=edit&id=${it.id}" class="btn btn-sm btn-success">编辑</a>
<button value="${it.id}" class="deleteitem btn btn-sm btn-danger">删除</button>
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
<input type="hidden" name="operate" value="addform" /> 
<input type="hidden" name="referer" value="${referer}" />
<!-- selectbox begin -->
<div class="form-group">
	<label for="student_id" class="col-sm-3 control-label">用户id</label>
	<div class="col-sm-9">
		<select class="selectbox" id="student_id" name="student_id"  style="width:100%;">
			<c:forEach var="item" items="${studentlist}" varStatus="i">
				<option value="${item.id}" >${item.name}</option>
			</c:forEach>
		</select>
	</div>
</div>
<!-- selectbox end -->
<!-- radio begin -->
			<c:forEach var="item" items="${studentlist}" varStatus="i">
			<div class="radio3 radio-check radio-${tagpostfixlist[i.count%fn:length(tagpostfixlist)]} radio-inline">
				<input type="radio" id="student_id${i.count}" name="student_id" value="${item.id}" <c:if test="${i.count==1}">checked</c:if>> <label for="student_id${i.count}">${item.name}</label>
			</div>
			</c:forEach>
<!-- radio end -->
<!-- selectbox begin -->
<div class="form-group">
	<label for="student_group_id" class="col-sm-3 control-label">分组id</label>
	<div class="col-sm-9">
		<select class="selectbox" id="student_group_id" name="student_group_id"  style="width:100%;">
			<c:forEach var="item" items="${student_grouplist}" varStatus="i">
				<option value="${item.id}" >${item.name}</option>
			</c:forEach>
		</select>
	</div>
</div>
<!-- selectbox end -->
<!-- radio begin -->
			<c:forEach var="item" items="${student_grouplist}" varStatus="i">
			<div class="radio3 radio-check radio-${tagpostfixlist[i.count%fn:length(tagpostfixlist)]} radio-inline">
				<input type="radio" id="student_group_id${i.count}" name="student_group_id" value="${item.id}" <c:if test="${i.count==1}">checked</c:if>> <label for="student_group_id${i.count}">${item.name}</label>
			</div>
			</c:forEach>
<!-- radio end -->
<div class="form-group">
	<label for="student_is_actived" class="col-sm-3 control-label">冻结账号</label>
	<div class="col-sm-9">
		<div
			class="checkbox3 checkbox-round checkbox-check checkbox-light">
					<input type="checkbox" id="student_is_actived" name="student_is_actived">
			<label for="student_is_actived"> 冻结账号 </label>
		</div>
	</div>
</div>
<div class="form-group">
	<label for="student_group_is_actived" class="col-sm-3 control-label">是否可以登录</label>
	<div class="col-sm-9">
		<div
			class="checkbox3 checkbox-round checkbox-check checkbox-light">
					<input type="checkbox" id="student_group_is_actived" name="student_group_is_actived">
			<label for="student_group_is_actived"> 是否可以登录 </label>
		</div>
	</div>
</div>
<div class="form-group">
	<label for="sno" class="col-sm-3 control-label">学号</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="sno"
			name="sno" placeholder="学号">
	</div>
</div>
<div class="form-group">
	<label for="name" class="col-sm-3 control-label">用户名称</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="name"
			name="name" placeholder="用户名称">
	</div>
</div>
<div class="form-group">
	<label for="email" class="col-sm-3 control-label">电子邮件</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="email"
			name="email" placeholder="电子邮件">
	</div>
</div>
<div class="form-group">
	<label for="mobile" class="col-sm-3 control-label">手机号码</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="mobile"
			name="mobile" placeholder="手机号码">
	</div>
</div>
<div class="form-group">
	<label for="qq" class="col-sm-3 control-label">QQ号码</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="qq"
			name="qq" placeholder="QQ号码">
	</div>
</div>
<div class="form-group">
	<label for="classcode" class="col-sm-3 control-label">学校的班级号，不是系统内的班级号</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="classcode"
			name="classcode" placeholder="学校的班级号，不是系统内的班级号">
	</div>
</div>
<div class="form-group">
	<label for="login_method" class="col-sm-3 control-label">状态：用户名密码登录0,微信登录1,两者同时可用2</label>
	<div class="col-sm-9">
		<input type="text" class="form-control restrictnumber" id="login_method"
			name="login_method" placeholder="状态：用户名密码登录0,微信登录1,两者同时可用2" >
	</div>
</div>
<div class="form-group">
	<label for="student_group_name" class="col-sm-3 control-label">组名</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="student_group_name"
			name="student_group_name" placeholder="组名">
	</div>
</div>
<div class="form-group">
	<label for="student_group_code" class="col-sm-3 control-label">分组编号，8位字符</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="student_group_code"
			name="student_group_code" placeholder="分组编号，8位字符">
	</div>
</div>
<div class="form-group">
	<div class="col-sm-offset-3 col-sm-9">
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
	type="hidden" name="id" value="${it.id}" />
<input type="hidden" name="referer" value="${referer}" />
<div class="form-group">
	<label for="id" class="col-sm-3 control-label">主键</label>
	<div class="col-sm-9">
		<input type="text" class="form-control restrictnumber" id="id"
			name="id" placeholder="主键" value="${it.id}">
	</div>
</div>
<!-- selectbox begin -->
<div class="form-group">
	<label for="student_id" class="col-sm-3 control-label">用户id</label>
	<div class="col-sm-9">
		<select class="selectbox" id="student_id" name="student_id"  style="width:100%;">
			<c:forEach var="item" items="${studentlist}" varStatus="i">
				<option value="${item.id}"
					<c:if test="${item.id==it.student_id}">selected</c:if>>${item.name}</option>
			</c:forEach>
		</select>
	</div>
</div>
<!-- selectbox end -->
<!-- radio begin -->
			<c:forEach var="item" items="${studentlist}" varStatus="i">
			<div class="radio3 radio-check radio-${tagpostfixlist[i.count%fn:length(tagpostfixlist)]} radio-inline">
				<input type="radio" id="student_id${i.count}" name="student_id" value="${item.id}" <c:if test="${item.id==it.student_id}">checked</c:if>> <label for="student_id${i.count}">${item.name}</label>
			</div>
			</c:forEach>
<!-- radio end -->
<!-- selectbox begin -->
<div class="form-group">
	<label for="student_group_id" class="col-sm-3 control-label">分组id</label>
	<div class="col-sm-9">
		<select class="selectbox" id="student_group_id" name="student_group_id"  style="width:100%;">
			<c:forEach var="item" items="${student_grouplist}" varStatus="i">
				<option value="${item.id}"
					<c:if test="${item.id==it.student_group_id}">selected</c:if>>${item.name}</option>
			</c:forEach>
		</select>
	</div>
</div>
<!-- selectbox end -->
<!-- radio begin -->
			<c:forEach var="item" items="${student_grouplist}" varStatus="i">
			<div class="radio3 radio-check radio-${tagpostfixlist[i.count%fn:length(tagpostfixlist)]} radio-inline">
				<input type="radio" id="student_group_id${i.count}" name="student_group_id" value="${item.id}" <c:if test="${item.id==it.student_group_id}">checked</c:if>> <label for="student_group_id${i.count}">${item.name}</label>
			</div>
			</c:forEach>
<!-- radio end -->
<div class="form-group">
	<label for="student_is_actived" class="col-sm-3 control-label">冻结账号</label>
	<div class="col-sm-9">
		<div
			class="checkbox3 checkbox-round checkbox-check checkbox-light">
			<c:choose>
				<c:when test="${it.student_is_actived == true}">
					<input type="checkbox" id="student_is_actived" name="student_is_actived"
						checked>
				</c:when>
				<c:when test="${it.student_is_actived == false}">
					<input type="checkbox" id="student_is_actived" name="student_is_actived">
				</c:when>
			</c:choose>
			<label for="student_is_actived"> 冻结账号 </label>
		</div>
	</div>
</div>
<div class="form-group">
	<label for="student_group_is_actived" class="col-sm-3 control-label">是否可以登录</label>
	<div class="col-sm-9">
		<div
			class="checkbox3 checkbox-round checkbox-check checkbox-light">
			<c:choose>
				<c:when test="${it.student_group_is_actived == true}">
					<input type="checkbox" id="student_group_is_actived" name="student_group_is_actived"
						checked>
				</c:when>
				<c:when test="${it.student_group_is_actived == false}">
					<input type="checkbox" id="student_group_is_actived" name="student_group_is_actived">
				</c:when>
			</c:choose>
			<label for="student_group_is_actived"> 是否可以登录 </label>
		</div>
	</div>
</div>
<div class="form-group">
	<label for="sno" class="col-sm-3 control-label">学号</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="sno"
			name="sno" placeholder="学号" value="${it.sno}">
	</div>
</div>
<div class="form-group">
	<label for="name" class="col-sm-3 control-label">用户名称</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="name"
			name="name" placeholder="用户名称" value="${it.name}">
	</div>
</div>
<div class="form-group">
	<label for="email" class="col-sm-3 control-label">电子邮件</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="email"
			name="email" placeholder="电子邮件" value="${it.email}">
	</div>
</div>
<div class="form-group">
	<label for="mobile" class="col-sm-3 control-label">手机号码</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="mobile"
			name="mobile" placeholder="手机号码" value="${it.mobile}">
	</div>
</div>
<div class="form-group">
	<label for="qq" class="col-sm-3 control-label">QQ号码</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="qq"
			name="qq" placeholder="QQ号码" value="${it.qq}">
	</div>
</div>
<div class="form-group">
	<label for="classcode" class="col-sm-3 control-label">学校的班级号，不是系统内的班级号</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="classcode"
			name="classcode" placeholder="学校的班级号，不是系统内的班级号" value="${it.classcode}">
	</div>
</div>
<div class="form-group">
	<label for="login_method" class="col-sm-3 control-label">状态：用户名密码登录0,微信登录1,两者同时可用2</label>
	<div class="col-sm-9">
		<input type="text" class="form-control restrictnumber" id="login_method"
			name="login_method" placeholder="状态：用户名密码登录0,微信登录1,两者同时可用2" value="${it.login_method}">
	</div>
</div>
<div class="form-group">
	<label for="student_group_name" class="col-sm-3 control-label">组名</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="student_group_name"
			name="student_group_name" placeholder="组名" value="${it.student_group_name}">
	</div>
</div>
<div class="form-group">
	<label for="student_group_code" class="col-sm-3 control-label">分组编号，8位字符</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="student_group_code"
			name="student_group_code" placeholder="分组编号，8位字符" value="${it.student_group_code}">
	</div>
</div>
<div class="form-group">
	<div class="col-sm-offset-3 col-sm-9">
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
					if ($("#student_id").val() == '') {
						alert("用户id必须输入！");
						$("#student_id").focus();
						return false;
					}
					if ($("#student_group_id").val() == '') {
						alert("分组id必须输入！");
						$("#student_group_id").focus();
						return false;
					}
					if ($("#student_is_actived").val() == '') {
						alert("冻结账号必须输入！");
						$("#student_is_actived").focus();
						return false;
					}
					if ($("#student_group_is_actived").val() == '') {
						alert("是否可以登录必须输入！");
						$("#student_group_is_actived").focus();
						return false;
					}
					if ($("#sno").val() == '') {
						alert("学号必须输入！");
						$("#sno").focus();
						return false;
					}
					if ($("#name").val() == '') {
						alert("用户名称必须输入！");
						$("#name").focus();
						return false;
					}
					if ($("#email").val() == '') {
						alert("电子邮件必须输入！");
						$("#email").focus();
						return false;
					}
					if ($("#mobile").val() == '') {
						alert("手机号码必须输入！");
						$("#mobile").focus();
						return false;
					}
					if ($("#qq").val() == '') {
						alert("QQ号码必须输入！");
						$("#qq").focus();
						return false;
					}
					if ($("#classcode").val() == '') {
						alert("学校的班级号，不是系统内的班级号必须输入！");
						$("#classcode").focus();
						return false;
					}
					if ($("#login_method").val() == '') {
						alert("状态：用户名密码登录0,微信登录1,两者同时可用2必须输入！");
						$("#login_method").focus();
						return false;
					}
					if ($("#student_group_name").val() == '') {
						alert("组名必须输入！");
						$("#student_group_name").focus();
						return false;
					}
					if ($("#student_group_code").val() == '') {
						alert("分组编号，8位字符必须输入！");
						$("#student_group_code").focus();
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
	var student_id = "";
	$('input:checkbox[class=student_id_ck]:checked').each(function(k) {
		if (k == 0) {
			student_id = $(this).val();
		} else {
			student_id += ',' + $(this).val();
		}
	});
	// alert('student_id=' + student_id);
	if (student_id != '') {
		qs += and + "student_id=" + student_id;
		and = "&";
	}
	var student_group_id = "";
	$('input:checkbox[class=student_group_id_ck]:checked').each(function(k) {
		if (k == 0) {
			student_group_id = $(this).val();
		} else {
			student_group_id += ',' + $(this).val();
		}
	});
	// alert('student_group_id=' + student_group_id);
	if (student_group_id != '') {
		qs += and + "student_group_id=" + student_group_id;
		and = "&";
	}
	if (kw != '') {
		qs += and + "kw=" + kw;
		and = "&";
	}
	// alert("qs=" + qs);
	$(location).attr('href', '${basePath}${pagename}' + qs);
});

		</script>
	</c:if>
	
</body>
</html>


