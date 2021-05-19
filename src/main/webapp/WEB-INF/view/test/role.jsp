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
														<th>角色ID</th>
														<th>角色名称</th>
														<th>角色别名</th>
														<th>系统数据 1=是,只有超级管理员能修改/0=否,拥有角色修改人员的权限能都修改</th>
														<th>状态 0=冻结/1=正常</th>
														<th>备注信息</th>
														<th>创建者</th>
														<th>更新者</th>
														<th>创建时间</th>
														<th>修改时间</th>
														<th>操作</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="it" items="${list}" varStatus="index">
														<tr>
															<td id="t${it.id}">${index.count+(pager.current - 1) * pager.size}</td>
															<td>${it.id}</td>
															<td>${it.name}</td>
															<td>${it.alias}</td>
															<td>
	<c:choose>
		<c:when test="${it.is_system}">
			<a href="${basePath}${pagename}?operate=hideitem&id=${it.id}" ><img alt="" src="bssets/i/greendot.png"></a>
		</c:when>
		<c:when test="${it.is_system==false}">
			<a href="${basePath}${pagename}?operate=showitem&id=${it.id}" ><img alt="" src="bssets/i/reddot.png"></a>
		</c:when>
	</c:choose>
</td>
															<td>
	<c:choose>
		<c:when test="${it.is_actived}">
			<a href="${basePath}${pagename}?operate=hideitem&id=${it.id}" ><img alt="" src="bssets/i/greendot.png"></a>
		</c:when>
		<c:when test="${it.is_actived==false}">
			<a href="${basePath}${pagename}?operate=showitem&id=${it.id}" ><img alt="" src="bssets/i/reddot.png"></a>
		</c:when>
	</c:choose>
</td>
															<td>${it.remark}</td>
															<td>${it.create_userid}</td>
															<td>${it.update_userid}</td>
															<td><c:if test="${it.create_gmt !=0}"><fmt:formatDate pattern="yyyy-MM-dd" value="${it.create_gmt_date}" /></c:if></td>
															<td><c:if test="${it.update_gmt !=0}"><fmt:formatDate pattern="yyyy-MM-dd" value="${it.update_gmt_date}" /></c:if></td>
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

			<div class="col-xs-12 col-sm-12">${it.name}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.alias}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.is_system}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.is_actived}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.remark}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.create_userid}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.update_userid}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12"><c:if test="${it.create_gmt !=0}"><fmt:formatDate pattern="yyyy-MM-dd" value="${it.create_gmt_date}" /></c:if></div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12"><c:if test="${it.update_gmt !=0}"><fmt:formatDate pattern="yyyy-MM-dd" value="${it.update_gmt_date}" /></c:if></div>
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
<div class="form-group">
	<label for="name" class="col-sm-3 control-label">角色名称</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="name"
			name="name" placeholder="角色名称">
	</div>
</div>
<div class="form-group">
	<label for="alias" class="col-sm-3 control-label">角色别名</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="alias"
			name="alias" placeholder="角色别名">
	</div>
</div>
<div class="form-group">
	<label for="is_system" class="col-sm-3 control-label">系统数据 1=是,只有超级管理员能修改/0=否,拥有角色修改人员的权限能都修改</label>
	<div class="col-sm-9">
		<div
			class="checkbox3 checkbox-round checkbox-check checkbox-light">
					<input type="checkbox" id="is_system" name="is_system">
			<label for="is_system"> 系统数据 1=是,只有超级管理员能修改/0=否,拥有角色修改人员的权限能都修改 </label>
		</div>
	</div>
</div>
<div class="form-group">
	<label for="is_actived" class="col-sm-3 control-label">状态 0=冻结/1=正常</label>
	<div class="col-sm-9">
		<div
			class="checkbox3 checkbox-round checkbox-check checkbox-light">
					<input type="checkbox" id="is_actived" name="is_actived">
			<label for="is_actived"> 状态 0=冻结/1=正常 </label>
		</div>
	</div>
</div>
<div class="form-group">
	<label for="remark" class="col-sm-3 control-label">备注信息</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="remark"
			name="remark" placeholder="备注信息">
	</div>
</div>
<div class="form-group">
	<label for="create_userid" class="col-sm-3 control-label">创建者</label>
	<div class="col-sm-9">
		<input type="text" class="form-control restrictnumber" id="create_userid"
			name="create_userid" placeholder="创建者" >
	</div>
</div>
<div class="form-group">
	<label for="update_userid" class="col-sm-3 control-label">更新者</label>
	<div class="col-sm-9">
		<input type="text" class="form-control restrictnumber" id="update_userid"
			name="update_userid" placeholder="更新者" >
	</div>
</div>
<div class="form-group d_t_dater">
	<label for="create_gmt_date" class="col-sm-3 control-label">创建时间</label>
	<div class="col-sm-9">
		<div class="input-group">
	<span class="input-group-addon" style="cursor: pointer;"
	onclick="$.jeDate('#create_gmt_date',{trigger:false,format: 'YYYY-MM-DD hh:mm:ss'})">创建时间</span>
<input placeholder="创建时间" type="text"
	class="form-control" id="create_gmt_date"
	name="create_gmt_date" readonly>
		</div>
	</div>
</div>
<div class="form-group d_t_dater">
	<label for="update_gmt_date" class="col-sm-3 control-label">修改时间</label>
	<div class="col-sm-9">
		<div class="input-group">
	<span class="input-group-addon" style="cursor: pointer;"
	onclick="$.jeDate('#update_gmt_date',{trigger:false,format: 'YYYY-MM-DD hh:mm:ss'})">修改时间</span>
<input placeholder="修改时间" type="text"
	class="form-control" id="update_gmt_date"
	name="update_gmt_date" readonly>
		</div>
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
	<label for="name" class="col-sm-3 control-label">角色名称</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="name"
			name="name" placeholder="角色名称" value="${it.name}">
	</div>
</div>
<div class="form-group">
	<label for="alias" class="col-sm-3 control-label">角色别名</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="alias"
			name="alias" placeholder="角色别名" value="${it.alias}">
	</div>
</div>
<div class="form-group">
	<label for="is_system" class="col-sm-3 control-label">系统数据 1=是,只有超级管理员能修改/0=否,拥有角色修改人员的权限能都修改</label>
	<div class="col-sm-9">
		<div
			class="checkbox3 checkbox-round checkbox-check checkbox-light">
			<c:choose>
				<c:when test="${it.is_system == true}">
					<input type="checkbox" id="is_system" name="is_system"
						checked>
				</c:when>
				<c:when test="${it.is_system == false}">
					<input type="checkbox" id="is_system" name="is_system">
				</c:when>
			</c:choose>
			<label for="is_system"> 系统数据 1=是,只有超级管理员能修改/0=否,拥有角色修改人员的权限能都修改 </label>
		</div>
	</div>
</div>
<div class="form-group">
	<label for="is_actived" class="col-sm-3 control-label">状态 0=冻结/1=正常</label>
	<div class="col-sm-9">
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
			<label for="is_actived"> 状态 0=冻结/1=正常 </label>
		</div>
	</div>
</div>
<div class="form-group">
	<label for="remark" class="col-sm-3 control-label">备注信息</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="remark"
			name="remark" placeholder="备注信息" value="${it.remark}">
	</div>
</div>
<div class="form-group">
	<label for="create_userid" class="col-sm-3 control-label">创建者</label>
	<div class="col-sm-9">
		<input type="text" class="form-control restrictnumber" id="create_userid"
			name="create_userid" placeholder="创建者" value="${it.create_userid}">
	</div>
</div>
<div class="form-group">
	<label for="update_userid" class="col-sm-3 control-label">更新者</label>
	<div class="col-sm-9">
		<input type="text" class="form-control restrictnumber" id="update_userid"
			name="update_userid" placeholder="更新者" value="${it.update_userid}">
	</div>
</div>
<div class="form-group d_t_dater">
	<label for="create_gmt_date" class="col-sm-3 control-label">创建时间</label>
	<div class="col-sm-9">
		<div class="input-group">
	<span class="input-group-addon" style="cursor: pointer;"
	onclick="$.jeDate('#create_gmt_date',{trigger:false,format: 'YYYY-MM-DD hh:mm:ss'})">创建时间</span>
<input placeholder="创建时间" type="text"
	class="form-control" id="create_gmt_date"
	name="create_gmt_date"
	<c:if test="${it.create_gmt != 0}">
	value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss' value='${it.create_gmt_date}' />"
	</c:if>
	readonly>
		</div>
	</div>
</div>
<div class="form-group d_t_dater">
	<label for="update_gmt_date" class="col-sm-3 control-label">修改时间</label>
	<div class="col-sm-9">
		<div class="input-group">
	<span class="input-group-addon" style="cursor: pointer;"
	onclick="$.jeDate('#update_gmt_date',{trigger:false,format: 'YYYY-MM-DD hh:mm:ss'})">修改时间</span>
<input placeholder="修改时间" type="text"
	class="form-control" id="update_gmt_date"
	name="update_gmt_date"
	<c:if test="${it.update_gmt != 0}">
	value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss' value='${it.update_gmt_date}' />"
	</c:if>
	readonly>
		</div>
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
						alert("角色ID必须输入！");
						$("#id").focus();
						return false;
					}
					if ($("#name").val() == '') {
						alert("角色名称必须输入！");
						$("#name").focus();
						return false;
					}
					if ($("#alias").val() == '') {
						alert("角色别名必须输入！");
						$("#alias").focus();
						return false;
					}
					if ($("#is_system").val() == '') {
						alert("系统数据 1=是,只有超级管理员能修改/0=否,拥有角色修改人员的权限能都修改必须输入！");
						$("#is_system").focus();
						return false;
					}
					if ($("#is_actived").val() == '') {
						alert("状态 0=冻结/1=正常必须输入！");
						$("#is_actived").focus();
						return false;
					}
					if ($("#remark").val() == '') {
						alert("备注信息必须输入！");
						$("#remark").focus();
						return false;
					}
					if ($("#create_userid").val() == '') {
						alert("创建者必须输入！");
						$("#create_userid").focus();
						return false;
					}
					if ($("#update_userid").val() == '') {
						alert("更新者必须输入！");
						$("#update_userid").focus();
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
	// alert("qs=" + qs);
	$(location).attr('href', '${basePath}${pagename}' + qs);
});

		</script>
	</c:if>
	
</body>
</html>


