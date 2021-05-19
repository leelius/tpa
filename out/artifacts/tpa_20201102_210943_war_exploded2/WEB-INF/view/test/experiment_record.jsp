<!------------------------------->
<!---- Generated By Denny(luhox@qq.com), cslite for sm -->
<!---- Remarks: ItemListHTML-->
<!---- Date Generated: 2020-10-09 10:47:59-->
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
					<c:if test="${kw != null  || student_id_map != null  || experiment_id_map != null }">class="collapse in form-inline" </c:if>
					<c:if test="${kw == null  && student_id_map == null  && experiment_id_map == null }">class="collapse form-inline" </c:if>>
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
						<!-- 搜索条件： -->
						<div class="row">
							<div class="col-xs-4 col-sm-3 col-md-2 col-lg-2 text-right margin-top-xs">
								:</div>
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
						<!-- /搜索条件： -->
						<!-- 搜索条件：实验id -->
						<div class="row">
							<div class="col-xs-4 col-sm-3 col-md-2 col-lg-2 text-right margin-top-xs">
								实验:</div>
							<c:forEach var="item" items="${experimentlist}" varStatus="i">
<!-- checkbox begin -->
								<div class="col-xs-4 col-sm-3 col-md-3 col-lg-3">
									<div class="checkbox3 checkbox-${tagpostfixlist[i.count%fn:length(tagpostfixlist)]} checkbox-check checkbox-light">
										<input value="${item.id}" id="experiment_id${i.count}"
class="experiment_id_ck" type="checkbox"
<c:if test="${experiment_id_map.get(item.id) != null}">checked</c:if>>
										<label for="experiment_id${i.count}"> ${item.name} </label>
									</div>
								</div>
<!-- checkbox end -->
<!-- radio begin -->
			<div class="radio3 radio-check radio-${tagpostfixlist[i.count%fn:length(tagpostfixlist)]} radio-inline">
				<input type="radio" id="experiment_id${i.count}" name="experiment_id" value="${item.id}" <c:if test="${experiment_id_map.get(item.id) != null}">checked</c:if>> <label for="experiment_id${i.count}">${item.name}</label>
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
						<!-- /搜索条件：实验id -->

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
														<th></th>
														<th></th>
														<th>学号</th>
														<th>实验id</th>
														<th>对实验报告的评语，100字以内！</th>
														<th>实验主要流程、基本操作或核心代码、算法片段</th>
														<th>实验报告，用xml记录实验结果</th>
														<th>实验结果的分析与评价</th>
														<th>实验成绩</th>
														<th>实验提交时间</th>
														<th>成绩描述：优秀/良好/中等/及格/不及格</th>
														<th>评阅状态1已评,2待评</th>
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
															<td>${studentmap[it.student_id]}</td>
															<td>${it.sno}</td>
															<td>${experimentmap[it.experiment_id]}</td>
															<td>${it.comment}</td>
															<td>${it.mainflow}</td>
															<td>${it.report}</td>
															<td>${it.result}</td>
															<td>${it.score}</td>
															<td><c:if test="${it.submit !=0}"><fmt:formatDate pattern="yyyy-MM-dd" value="${it.submit_date}" /></c:if></td>
															<td>${it.scorescript}</td>
															<td>${it.reviewstate}</td>
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

			<div class="col-xs-12 col-sm-12">${it.student_id}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.sno}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.experiment_id}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.comment}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.mainflow}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.report}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.result}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.score}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12"><c:if test="${it.submit !=0}"><fmt:formatDate pattern="yyyy-MM-dd" value="${it.submit_date}" /></c:if></div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.scorescript}</div>
				<div class="clearfix"></div>

			<div class="col-xs-12 col-sm-12">${it.reviewstate}</div>
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
<!-- selectbox begin -->
<div class="form-group">
	<label for="student_id" class="col-sm-3 control-label"></label>
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
<div class="form-group">
	<label for="sno" class="col-sm-3 control-label">学号</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="sno"
			name="sno" placeholder="学号">
	</div>
</div>
<!-- selectbox begin -->
<div class="form-group">
	<label for="experiment_id" class="col-sm-3 control-label">实验id</label>
	<div class="col-sm-9">
		<select class="selectbox" id="experiment_id" name="experiment_id"  style="width:100%;">
			<c:forEach var="item" items="${experimentlist}" varStatus="i">
				<option value="${item.id}" >${item.name}</option>
			</c:forEach>
		</select>
	</div>
</div>
<!-- selectbox end -->
<!-- radio begin -->
			<c:forEach var="item" items="${experimentlist}" varStatus="i">
			<div class="radio3 radio-check radio-${tagpostfixlist[i.count%fn:length(tagpostfixlist)]} radio-inline">
				<input type="radio" id="experiment_id${i.count}" name="experiment_id" value="${item.id}" <c:if test="${i.count==1}">checked</c:if>> <label for="experiment_id${i.count}">${item.name}</label>
			</div>
			</c:forEach>
<!-- radio end -->
<div class="form-group">
	<label for="comment" class="col-sm-3 control-label">对实验报告的评语，100字以内！</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="comment"
			name="comment" placeholder="对实验报告的评语，100字以内！">
	</div>
</div>
<div class="form-group">
	<label for="mainflow" class="col-sm-3 control-label">实验主要流程、基本操作或核心代码、算法片段</label>
	<div class="col-sm-9">
<textarea class="form-control" id="mainflow" name="mainflow" style="width:100%;height:400px;visibility:hidden;"></textarea>
	</div>
</div>
<div class="form-group">
	<label for="report" class="col-sm-3 control-label">实验报告，用xml记录实验结果</label>
	<div class="col-sm-9">
<textarea class="form-control" id="report" name="report" style="width:100%;height:400px;visibility:hidden;"></textarea>
	</div>
</div>
<div class="form-group">
	<label for="result" class="col-sm-3 control-label">实验结果的分析与评价</label>
	<div class="col-sm-9">
<textarea class="form-control" id="result" name="result" style="width:100%;height:400px;visibility:hidden;"></textarea>
	</div>
</div>
<div class="form-group">
	<label for="score" class="col-sm-3 control-label">实验成绩</label>
	<div class="col-sm-9">
		<input type="text" class="form-control restrictnumber" id="score"
			name="score" placeholder="实验成绩" >
	</div>
</div>
<div class="form-group d_t_dater">
	<label for="submit_date" class="col-sm-3 control-label">实验提交时间</label>
	<div class="col-sm-9">
		<div class="input-group">
	<span class="input-group-addon" style="cursor: pointer;"
	onclick="$.jeDate('#submit_date',{trigger:false,format: 'YYYY-MM-DD hh:mm:ss'})">实验提交时间</span>
<input placeholder="实验提交时间" type="text"
	class="form-control" id="submit_date"
	name="submit_date" readonly>
		</div>
	</div>
</div>
<div class="form-group">
	<label for="scorescript" class="col-sm-3 control-label">成绩描述：优秀/良好/中等/及格/不及格</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="scorescript"
			name="scorescript" placeholder="成绩描述：优秀/良好/中等/及格/不及格">
	</div>
</div>
<div class="form-group">
	<label for="reviewstate" class="col-sm-3 control-label">评阅状态1已评,2待评</label>
	<div class="col-sm-9">
		<input type="text" class="form-control restrictnumber" id="reviewstate"
			name="reviewstate" placeholder="评阅状态1已评,2待评" >
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
<!-- selectbox begin -->
<div class="form-group">
	<label for="student_id" class="col-sm-3 control-label"></label>
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
<div class="form-group">
	<label for="sno" class="col-sm-3 control-label">学号</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="sno"
			name="sno" placeholder="学号" value="${it.sno}">
	</div>
</div>
<!-- selectbox begin -->
<div class="form-group">
	<label for="experiment_id" class="col-sm-3 control-label">实验id</label>
	<div class="col-sm-9">
		<select class="selectbox" id="experiment_id" name="experiment_id"  style="width:100%;">
			<c:forEach var="item" items="${experimentlist}" varStatus="i">
				<option value="${item.id}"
					<c:if test="${item.id==it.experiment_id}">selected</c:if>>${item.name}</option>
			</c:forEach>
		</select>
	</div>
</div>
<!-- selectbox end -->
<!-- radio begin -->
			<c:forEach var="item" items="${experimentlist}" varStatus="i">
			<div class="radio3 radio-check radio-${tagpostfixlist[i.count%fn:length(tagpostfixlist)]} radio-inline">
				<input type="radio" id="experiment_id${i.count}" name="experiment_id" value="${item.id}" <c:if test="${item.id==it.experiment_id}">checked</c:if>> <label for="experiment_id${i.count}">${item.name}</label>
			</div>
			</c:forEach>
<!-- radio end -->
<div class="form-group">
	<label for="comment" class="col-sm-3 control-label">对实验报告的评语，100字以内！</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="comment"
			name="comment" placeholder="对实验报告的评语，100字以内！" value="${it.comment}">
	</div>
</div>
<div class="form-group">
	<label for="mainflow" class="col-sm-3 control-label">实验主要流程、基本操作或核心代码、算法片段</label>
	<div class="col-sm-9">
<textarea class="form-control" id="mainflow" name="mainflow" style="width:100%;height:400px;visibility:hidden;">${it.mainflow}</textarea>
	</div>
</div>
<div class="form-group">
	<label for="report" class="col-sm-3 control-label">实验报告，用xml记录实验结果</label>
	<div class="col-sm-9">
<textarea class="form-control" id="report" name="report" style="width:100%;height:400px;visibility:hidden;">${it.report}</textarea>
	</div>
</div>
<div class="form-group">
	<label for="result" class="col-sm-3 control-label">实验结果的分析与评价</label>
	<div class="col-sm-9">
<textarea class="form-control" id="result" name="result" style="width:100%;height:400px;visibility:hidden;">${it.result}</textarea>
	</div>
</div>
<div class="form-group">
	<label for="score" class="col-sm-3 control-label">实验成绩</label>
	<div class="col-sm-9">
		<input type="text" class="form-control restrictnumber" id="score"
			name="score" placeholder="实验成绩" value="${it.score}">
	</div>
</div>
<div class="form-group d_t_dater">
	<label for="submit_date" class="col-sm-3 control-label">实验提交时间</label>
	<div class="col-sm-9">
		<div class="input-group">
	<span class="input-group-addon" style="cursor: pointer;"
	onclick="$.jeDate('#submit_date',{trigger:false,format: 'YYYY-MM-DD hh:mm:ss'})">实验提交时间</span>
<input placeholder="实验提交时间" type="text"
	class="form-control" id="submit_date"
	name="submit_date"
	<c:if test="${it.submit != 0}">
	value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss' value='${it.submit_date}' />"
	</c:if>
	readonly>
		</div>
	</div>
</div>
<div class="form-group">
	<label for="scorescript" class="col-sm-3 control-label">成绩描述：优秀/良好/中等/及格/不及格</label>
	<div class="col-sm-9">
		<input type="text" class="form-control" id="scorescript"
			name="scorescript" placeholder="成绩描述：优秀/良好/中等/及格/不及格" value="${it.scorescript}">
	</div>
</div>
<div class="form-group">
	<label for="reviewstate" class="col-sm-3 control-label">评阅状态1已评,2待评</label>
	<div class="col-sm-9">
		<input type="text" class="form-control restrictnumber" id="reviewstate"
			name="reviewstate" placeholder="评阅状态1已评,2待评" value="${it.reviewstate}">
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
						alert("必须输入！");
						$("#id").focus();
						return false;
					}
					if ($("#student_id").val() == '') {
						alert("必须输入！");
						$("#student_id").focus();
						return false;
					}
					if ($("#sno").val() == '') {
						alert("学号必须输入！");
						$("#sno").focus();
						return false;
					}
					if ($("#experiment_id").val() == '') {
						alert("实验id必须输入！");
						$("#experiment_id").focus();
						return false;
					}
					if ($("#comment").val() == '') {
						alert("对实验报告的评语，100字以内！必须输入！");
						$("#comment").focus();
						return false;
					}
					if ($("#mainflow").val() == '') {
						alert("实验主要流程、基本操作或核心代码、算法片段必须输入！");
						$("#mainflow").focus();
						return false;
					}
					if ($("#report").val() == '') {
						alert("实验报告，用xml记录实验结果必须输入！");
						$("#report").focus();
						return false;
					}
					if ($("#result").val() == '') {
						alert("实验结果的分析与评价必须输入！");
						$("#result").focus();
						return false;
					}
					if ($("#score").val() == '') {
						alert("实验成绩必须输入！");
						$("#score").focus();
						return false;
					}
					if ($("#submit_date").val() == '') {
						alert("实验提交时间必须输入！");
						$("#submit_date").focus();
						return false;
					}
					if ($("#scorescript").val() == '') {
						alert("成绩描述：优秀/良好/中等/及格/不及格必须输入！");
						$("#scorescript").focus();
						return false;
					}
					if ($("#reviewstate").val() == '') {
						alert("评阅状态1已评,2待评必须输入！");
						$("#reviewstate").focus();
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
KindEditor.ready(function(K) {
	var editor1 = K.create('textarea[name="mainflow"]', {
		cssPath : '${basePath}bssets/ket/plugins/code/prettify.css',
		uploadJson : '${basePath}admin/uploadfile',
		fileManagerJson : '${basePath}admin/filemanager',
		allowFileManager : true,
		afterCreate : function() {}
	});
	var editor1 = K.create('textarea[name="report"]', {
		cssPath : '${basePath}bssets/ket/plugins/code/prettify.css',
		uploadJson : '${basePath}admin/uploadfile',
		fileManagerJson : '${basePath}admin/filemanager',
		allowFileManager : true,
		afterCreate : function() {}
	});
	var editor1 = K.create('textarea[name="result"]', {
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
	var experiment_id = "";
	$('input:checkbox[class=experiment_id_ck]:checked').each(function(k) {
		if (k == 0) {
			experiment_id = $(this).val();
		} else {
			experiment_id += ',' + $(this).val();
		}
	});
	// alert('experiment_id=' + experiment_id);
	if (experiment_id != '') {
		qs += and + "experiment_id=" + experiment_id;
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


