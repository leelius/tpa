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
<jsp:include page="../inc/ket.jsp"></jsp:include>
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
					<c:if test="${kw != null  || subject_id_map != null }">class="collapse in form-inline" </c:if>
					<c:if test="${kw == null  && subject_id_map == null }">class="collapse form-inline" </c:if>>
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
						<!-- 搜索条件：题目所属科目 -->
						<div class="row">
							<div class="col-xs-4 col-sm-3 col-md-2 col-lg-2 text-right margin-top-xs">
								题目所属科目:</div>
							<c:forEach var="item" items="${subjectlist}" varStatus="i">
<!-- checkbox begin -->
								<div class="col-xs-4 col-sm-3 col-md-3 col-lg-3">
									<div class="checkbox3 checkbox-${tagpostfixlist[i.count%fn:length(tagpostfixlist)]} checkbox-check checkbox-light">
										<input value="${item.id}" id="subject_id${i.count}"
class="subject_id_ck" type="checkbox"
<c:if test="${subject_id_map.get(item.id) != null}">checked</c:if>>
										<label for="subject_id${i.count}"> ${item.name} </label>
									</div>
								</div>
<!-- checkbox end -->
<!-- radio begin -->
			<div class="radio3 radio-check radio-${tagpostfixlist[i.count%fn:length(tagpostfixlist)]} radio-inline">
				<input type="radio" id="subject_id${i.count}" name="subject_id" value="${item.id}" <c:if test="${subject_id_map.get(item.id) != null}">checked</c:if>> <label for="subject_id${i.count}">${item.name}</label>
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
						<!-- /搜索条件：题目所属科目 -->

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
														<th>题目所属科目</th>
														<th>是否可用</th>
														<th>考核类型：选择/填空/正则填空/判断/文件</th>
														<th>难度</th>
														<th>考核点内容</th>
														<th>创建时间</th>
														<th>修改时间</th>
														<th>科目标题</th>
														<th></th>
														<th>是否显示</th>
														<th>操作</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="it" items="${list}" varStatus="index">
														<tr>
															<td id="t${it.id}">${index.count+(pager.current - 1) * pager.size}</td>
															<td>${it.id}</td>
															<td>${subjectmap[it.subject_id]}</td>
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
															<td>${it.type}</td>
															<td>${it.difficulty}</td>
															<td>${it.content}</td>
															<td><c:if test="${it.create_gmt !=0}"><fmt:formatDate pattern="yyyy-MM-dd" value="${it.create_gmt_date}" /></c:if></td>
															<td><c:if test="${it.update_gmt !=0}"><fmt:formatDate pattern="yyyy-MM-dd" value="${it.update_gmt_date}" /></c:if></td>
															<td>${it.subject_name}</td>
															<td>${it.evaluation_type_name}</td>
															<td>
	<c:choose>
		<c:when test="${it.subject_is_actived}">
			<a href="${basePath}${pagename}?operate=hideitem&id=${it.id}" ><img alt="" src="bssets/i/greendot.png"></a>
		</c:when>
		<c:when test="${it.subject_is_actived==false}">
			<a href="${basePath}${pagename}?operate=showitem&id=${it.id}" ><img alt="" src="bssets/i/reddot.png"></a>
		</c:when>
	</c:choose>
</td>
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

			<div class="col-xs-12 col-sm-12">${it.subject_id}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.is_actived}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.type}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.difficulty}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.content}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12"><c:if test="${it.create_gmt !=0}"><fmt:formatDate pattern="yyyy-MM-dd" value="${it.create_gmt_date}" /></c:if></div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12"><c:if test="${it.update_gmt !=0}"><fmt:formatDate pattern="yyyy-MM-dd" value="${it.update_gmt_date}" /></c:if></div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.subject_name}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.evaluation_type_name}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.subject_is_actived}</div>
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
	<label for="subject_id" class="col-sm-3 control-label">题目所属科目</label>
	<div class="col-sm-9">
		<select class="selectbox" id="subject_id" name="subject_id"  style="width:100%;">
			<c:forEach var="item" items="${subjectlist}" varStatus="i">
				<option value="${item.id}" >${item.name}</option>
			</c:forEach>
		</select>
	</div>
</div>
<!-- selectbox end -->
<!-- radio begin -->
			<c:forEach var="item" items="${subjectlist}" varStatus="i">
			<div class="radio3 radio-check radio-${tagpostfixlist[i.count%fn:length(tagpostfixlist)]} radio-inline">
				<input type="radio" id="subject_id${i.count}" name="subject_id" value="${item.id}" <c:if test="${i.count==1}">checked</c:if>> <label for="subject_id${i.count}">${item.name}</label>
			</div>
			</c:forEach>
<!-- radio end -->
<div class="form-group">
	<label for="is_actived" class="col-sm-3 control-label">是否可用</label>
	<div class="col-sm-9">
		<div
			class="checkbox3 checkbox-round checkbox-check checkbox-light">
					<input type="checkbox" id="is_actived" name="is_actived">
			<label for="is_actived"> 是否可用 </label>
		</div>
	</div>
</div>
<div class="form-group">
	<label for="type" class="col-sm-3 control-label">考核类型：选择/填空/正则填空/判断/文件</label>
	<div class="col-sm-9">
		<input type="text" class="form-control restrictnumber" id="type"
			name="type" placeholder="考核类型：选择/填空/正则填空/判断/文件" >
	</div>
</div>
<div class="form-group">
	<label for="difficulty" class="col-sm-3 control-label">难度</label>
	<div class="col-sm-9">
		<input type="text" class="form-control restrictnumber" id="difficulty"
			name="difficulty" placeholder="难度" >
	</div>
</div>
<div class="form-group">
	<label for="content" class="col-sm-3 control-label">考核点内容</label>
	<div class="col-sm-9">
<textarea class="form-control" id="content" name="content" style="width:100%;height:400px;visibility:hidden;"></textarea>
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
	<label for="subject_name" class="col-sm-3 control-label">科目标题</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="subject_name"
			name="subject_name" placeholder="科目标题">
	</div>
</div>
<div class="form-group">
	<label for="evaluation_type_name" class="col-sm-3 control-label"></label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="evaluation_type_name"
			name="evaluation_type_name" placeholder="">
	</div>
</div>
<div class="form-group">
	<label for="subject_is_actived" class="col-sm-3 control-label">是否显示</label>
	<div class="col-sm-9">
		<div
			class="checkbox3 checkbox-round checkbox-check checkbox-light">
					<input type="checkbox" id="subject_is_actived" name="subject_is_actived">
			<label for="subject_is_actived"> 是否显示 </label>
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
	<label for="id" class="col-sm-3 control-label">主键</label>
	<div class="col-sm-9">
		<input type="text" class="form-control restrictnumber" id="id"
			name="id" placeholder="主键" value="${it.id}">
	</div>
</div>
<!-- selectbox begin -->
<div class="form-group">
	<label for="subject_id" class="col-sm-3 control-label">题目所属科目</label>
	<div class="col-sm-9">
		<select class="selectbox" id="subject_id" name="subject_id"  style="width:100%;">
			<c:forEach var="item" items="${subjectlist}" varStatus="i">
				<option value="${item.id}"
					<c:if test="${item.id==it.subject_id}">selected</c:if>>${item.name}</option>
			</c:forEach>
		</select>
	</div>
</div>
<!-- selectbox end -->
<!-- radio begin -->
			<c:forEach var="item" items="${subjectlist}" varStatus="i">
			<div class="radio3 radio-check radio-${tagpostfixlist[i.count%fn:length(tagpostfixlist)]} radio-inline">
				<input type="radio" id="subject_id${i.count}" name="subject_id" value="${item.id}" <c:if test="${item.id==it.subject_id}">checked</c:if>> <label for="subject_id${i.count}">${item.name}</label>
			</div>
			</c:forEach>
<!-- radio end -->
<div class="form-group">
	<label for="is_actived" class="col-sm-3 control-label">是否可用</label>
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
			<label for="is_actived"> 是否可用 </label>
		</div>
	</div>
</div>
<div class="form-group">
	<label for="type" class="col-sm-3 control-label">考核类型：选择/填空/正则填空/判断/文件</label>
	<div class="col-sm-9">
		<input type="text" class="form-control restrictnumber" id="type"
			name="type" placeholder="考核类型：选择/填空/正则填空/判断/文件" value="${it.type}">
	</div>
</div>
<div class="form-group">
	<label for="difficulty" class="col-sm-3 control-label">难度</label>
	<div class="col-sm-9">
		<input type="text" class="form-control restrictnumber" id="difficulty"
			name="difficulty" placeholder="难度" value="${it.difficulty}">
	</div>
</div>
<div class="form-group">
	<label for="content" class="col-sm-3 control-label">考核点内容</label>
	<div class="col-sm-9">
<textarea class="form-control" id="content" name="content" style="width:100%;height:400px;visibility:hidden;">${it.content}</textarea>
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
	<label for="subject_name" class="col-sm-3 control-label">科目标题</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="subject_name"
			name="subject_name" placeholder="科目标题" value="${it.subject_name}">
	</div>
</div>
<div class="form-group">
	<label for="evaluation_type_name" class="col-sm-3 control-label"></label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="evaluation_type_name"
			name="evaluation_type_name" placeholder="" value="${it.evaluation_type_name}">
	</div>
</div>
<div class="form-group">
	<label for="subject_is_actived" class="col-sm-3 control-label">是否显示</label>
	<div class="col-sm-9">
		<div
			class="checkbox3 checkbox-round checkbox-check checkbox-light">
			<c:choose>
				<c:when test="${it.subject_is_actived == true}">
					<input type="checkbox" id="subject_is_actived" name="subject_is_actived"
						checked>
				</c:when>
				<c:when test="${it.subject_is_actived == false}">
					<input type="checkbox" id="subject_is_actived" name="subject_is_actived">
				</c:when>
			</c:choose>
			<label for="subject_is_actived"> 是否显示 </label>
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
						alert("主键必须输入！");
						$("#id").focus();
						return false;
					}
					if ($("#subject_id").val() == '') {
						alert("题目所属科目必须输入！");
						$("#subject_id").focus();
						return false;
					}
					if ($("#is_actived").val() == '') {
						alert("是否可用必须输入！");
						$("#is_actived").focus();
						return false;
					}
					if ($("#type").val() == '') {
						alert("考核类型：选择/填空/正则填空/判断/文件必须输入！");
						$("#type").focus();
						return false;
					}
					if ($("#difficulty").val() == '') {
						alert("难度必须输入！");
						$("#difficulty").focus();
						return false;
					}
					if ($("#content").val() == '') {
						alert("考核点内容必须输入！");
						$("#content").focus();
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
					if ($("#subject_name").val() == '') {
						alert("科目标题必须输入！");
						$("#subject_name").focus();
						return false;
					}
					if ($("#evaluation_type_name").val() == '') {
						alert("必须输入！");
						$("#evaluation_type_name").focus();
						return false;
					}
					if ($("#subject_is_actived").val() == '') {
						alert("是否显示必须输入！");
						$("#subject_is_actived").focus();
						return false;
					}

				});
			});
KindEditor.ready(function(K) {
	var editor1 = K.create('textarea[name="content"]', {
		cssPath : '${basePath}bssets/ket/plugins/code/prettify.css',
		uploadJson : '${basePath}admin/uploadfile',
		fileManagerJson : '${basePath}admin/filemanager',
		allowFileManager : true,
		afterCreate : function() {}
	});
	prettyPrint();
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
	var subject_id = "";
	$('input:checkbox[class=subject_id_ck]:checked').each(function(k) {
		if (k == 0) {
			subject_id = $(this).val();
		} else {
			subject_id += ',' + $(this).val();
		}
	});
	// alert('subject_id=' + subject_id);
	if (subject_id != '') {
		qs += and + "subject_id=" + subject_id;
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


