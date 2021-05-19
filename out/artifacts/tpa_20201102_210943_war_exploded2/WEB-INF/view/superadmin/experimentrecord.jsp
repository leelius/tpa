<!------------------------------->
<!---- Generated By Denny(luhox@qq.com), cslite for sm -->
<!---- Remarks: ItemListHTML-->
<!---- Date Generated: 2019-07-10 09:01:33-->
<!------------------------------->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:set var="searchpanel" value="true"></c:set>
<c:set var="uploadfile" value="true"></c:set>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<jsp:include page="../inc/bsa_head.jsp"></jsp:include>
<link href="bssets/css/bsat1-style.css" rel="stylesheet" />
<title>${pagetitle}-${appName}</title>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="../inc/bsa_navtop.jsp"></jsp:include>
		<jsp:include page="../inc/bsa_navside.jsp"></jsp:include>
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
							<div class="row margin-xs">
								<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
									<div>
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
													<c:if test="${kw != null  || reviewstate_map != null  ||  curriculum_id_map != null  || student_group_id_map != null }">class="collapse in form-inline" </c:if>
													<c:if test="${kw == null  && reviewstate_map == null  &&  curriculum_id_map == null  && student_group_id_map == null }">class="collapse form-inline" </c:if>>
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
														<!-- 搜索条件：已评/未评 -->
														<div class="row">
															<div
																class="col-xs-4 col-sm-3 col-md-2 col-lg-2 text-right margin-top-xs">
																评阅状态:</div>
															<c:forEach var="item" items="${reviewstatelist}"
																varStatus="i">
																<div class="col-xs-4 col-sm-3 col-md-3 col-lg-3">
																	<div
																		class="checkbox3 checkbox-round checkbox-check checkbox-light">
																		<input value="${item.id}"
																			id="reviewstate_id${i.count}"
																			class="reviewstate_id_ck" type="checkbox"
																			<c:if test="${reviewstate_map.get(item.id) != null}">checked</c:if>>
																		<label for="reviewstate_id${i.count}">
																			${item.name} </label>
																	</div>
																</div>
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
														<!-- /搜索条件：已评/未评 -->														
														<!-- 搜索条件：课程 -->
														<div class="row">
															<div
																class="col-xs-4 col-sm-3 col-md-2 col-lg-2 text-right margin-top-xs">
																课程:</div>
															<c:forEach var="item" items="${curriculumlist}"
																varStatus="i">
																<!-- checkbox begin -->
																<div class="col-xs-4 col-sm-3 col-md-3 col-lg-3">
																	<div
																		class="checkbox3 checkbox-${tagpostfixlist[i.count%fn:length(tagpostfixlist)]} checkbox-check checkbox-light">
																		<input value="${item.id}" id="curriculum_id${i.count}"
																			class="curriculum_id_ck" type="checkbox"
																			<c:if test="${curriculum_id_map.get(item.id) != null}">checked</c:if>>
																		<label for="curriculum_id${i.count}">
																			${item.subject_name} </label>
																	</div>
																</div>
																<!-- checkbox end -->

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
														<!-- /搜索条件：课程 -->
														<!-- 搜索条件：分组id -->
														<div class="row">
															<div
																class="col-xs-4 col-sm-3 col-md-2 col-lg-2 text-right margin-top-xs">
																分组:</div>
															<c:forEach var="item" items="${student_grouplist}"
																varStatus="i">
																<!-- checkbox begin -->
																<div class="col-xs-4 col-sm-3 col-md-3 col-lg-3">
																	<div
																		class="checkbox3 checkbox-${tagpostfixlist[i.count%fn:length(tagpostfixlist)]} checkbox-check checkbox-light">
																		<input value="${item.id}"
																			id="student_group_id${i.count}"
																			class="student_group_id_ck" type="checkbox"
																			<c:if test="${student_group_id_map.get(item.id) != null}">checked</c:if>>
																		<label for="student_group_id${i.count}">
																			${item.name} </label>
																	</div>
																</div>
																<!-- checkbox end -->

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
															<th>课程/班级/分组</th>
															<th>学生</th>
															<th>实验</th>
															<th>实验成绩</th>
															<th>评阅状态</th>
															<th>操作</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach var="it" items="${list}" varStatus="index">
															<tr>
																<td id="t${it.id}">${index.count+(pager.current - 1) * pager.size}</td>
																<td>${it.subject_name}<br>${it.student_group_name}<br>${it.student_group_code}</td>
																<td>${it.truename}<br>${it.sno}</td>
																<td>${it.experiment_title}<c:choose>
																		<c:when test="${it.closing_gmt < now}">
																			<img alt="实验已经结束，可以评阅啦!" src="bssets/i/greendot.png">
																		</c:when>
																		<c:when test="${it.closing_gmt >= now}">
																			<img alt="实验尚未结束哦!" src="bssets/i/reddot.png">
																		</c:when>
																	</c:choose><br> <c:if test="${it.submit !=0}">
																		<fmt:formatDate pattern="yyyy-MM-dd"
																			value="${it.submit_date}" />
																	</c:if>
																</td>
																<td>${it.score}</td>
																<td>${it.scorescript}<c:choose>
																		<c:when test="${it.scorescript =='已评'}">
																			<img alt="已阅" src="bssets/i/greendot.png">
																		</c:when>
																		<c:when test="${it.scorescript =='待评'}">
																			<img alt="待阅" src="bssets/i/reddot.png">
																		</c:when>
																	</c:choose>
																</td>
																<td><a
																	href="${basePath}${pagename}?operate=edit&id=${it.id}"
																	class="btn btn-sm btn-success">评阅</a></td>
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

												<div class="col-xs-12 col-sm-12">${it.student_id}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.experiment_id}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.curriculum_id}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.truename}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.sno}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.sno_exp}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.classcode}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.score}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.student_group_is_actived}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.student_is_actived}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.curriculum_is_actived}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.experiment_curriculum_is_actived}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.subject_is_actived}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.student_group_code}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.student_group_name}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">
													<c:if test="${it.submit !=0}">
														<fmt:formatDate pattern="yyyy-MM-dd"
															value="${it.submit_date}" />
													</c:if>
												</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.scorescript}</div>
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

												<div class="col-xs-12 col-sm-12">${it.wechat_openid}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.experiment_title}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.subject_name}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.subject_id}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.user_id}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">
													<c:if test="${it.closing_gmt !=0}">
														<fmt:formatDate pattern="yyyy-MM-dd"
															value="${it.closing_gmt_date}" />
													</c:if>
												</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">
													<c:if test="${it.opening_gmt !=0}">
														<fmt:formatDate pattern="yyyy-MM-dd"
															value="${it.opening_gmt_date}" />
													</c:if>
												</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.curriculum_student_groupid}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.student_group_id}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.reviewstate}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">${it.experiment_curriculumid}</div>
												<div class="clearfix"></div>

												<div class="col-xs-12 col-sm-12">
													<a href="${basePath}${pagename}?operate=moveup&id=${it.id}"
														class="btn btn-sm btn-info"
														<c:if test="${index.count==1}">disabled</c:if>>上移</a> <a
														href="${basePath}${pagename}?operate=movedown&id=${it.id}"
														class="btn btn-sm btn-warning"
														<c:if test="${index.count==pager.total}">disabled</c:if>>下移</a>
													<a href="${basePath}${pagename}?operate=edit&id=${it.id}"
														class="btn btn-sm btn-success">编辑</a>
													<button value="${it.id}"
														class="deleteitem btn btn-sm btn-danger">删除</button>
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
										<a class="btn btn-warning" href="${pagename}">放弃评阅，直接返回列表</a>

										<div class="form-group">
											<label class="col-sm-2 control-label">实验信息</label>
											<div class="col-sm-10">
												<h3>${it.subject_name},${it.student_group_name}</h3>
												<h3>${it.truename}——${it.sno}</h3>
												<hr>
												<h3>实验标题：${it.experiment_title}</h3>
												<br>
											</div>
										</div>
										<!-- 
										<div class="form-group">
											<label class="col-sm-2 control-label">一、实验目的及要求：</label>
											<div class="col-sm-10">
												${it.experiment_purposes_requirement}</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">二、实验内容：</label>
											<div class="col-sm-10">${it.experiment_content}</div>
										</div>
										 -->
										<div class="form-group">
											<label class="col-sm-2 control-label">三、实验主要流程、基本操作或核心代码、算法片段：</label>
											<div class="col-sm-10">${it.mainflow}</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">四、实验结果的分析与评价：</label>
											<div class="col-sm-10">${it.result}</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">实验上传的文件附件：</label>
											<div class="col-sm-10">
												<c:choose>
													<c:when test="${fn:length(filelist)==0}">该同学没有上传文件！</c:when>
													<c:when test="${fn:length(filelist)>0}">
											
														上传了${fn:length(filelist)}个文件，大约占用了${filesize}字节存储空间，还可以上传${exp.filesize-filesize}字节（
														<fmt:formatNumber
															value="${(exp.filesize-filesize)/1024/1024}"
															type="number" groupingUsed="false" maxFractionDigits="2" />
														MB）的内容 <br />文件列表如下：<br />
														系统文件上传要求：单个文件大小不能超过10M，如果总量超出10M，压缩时分卷，多次上传 <br />

														<ul>
															<c:forEach var="item" items="${filelist}" varStatus="xh">
																<li class="am-margin-vertical"><span
																	class="am-text-danger">第${xh.count}个文件：${item.filename};
																		&nbsp;&nbsp;&nbsp;&nbsp;文件大小：${item.filesize}字节</span>
																	&nbsp;&nbsp;&nbsp;&nbsp;<a
																	href="superadmin/experimentrecord/download?filehash=${item.filehash}&s=${it.subject_id}&e=${it.experiment_id}&u=${it.sno}"
																	class="am-btn am-btn-default am-round am-btn-sm">下载</a></li>
															</c:forEach>
														</ul>
													</c:when>
												</c:choose>
											</div>
										</div>
									</div>
									<form id="form1" class="form-horizontal" method="post"
										action="${pagename}">
										<input type="hidden" name="operate" value="editform" /> <input
											type="hidden" name="id" value="${it.id}" />
										<div class="form-group">
											<label for="score" class="col-sm-2 control-label">评阅结果</label>
											<div class="col-sm-1">
												<span id="score2" style="color: red; font-size: 24px;">
													<c:choose>
														<c:when test="${it.scorescript =='待评'}">
		A+
	</c:when>
														<c:when test="${it.scorescript =='已评' && it.score >=80}">
		A+
	</c:when>
														<c:when test="${it.scorescript =='已评' && it.score >=75}">
		A-
	</c:when>
														<c:when test="${it.scorescript =='已评' && it.score >=65}">
		B+
	</c:when>
														<c:when test="${it.scorescript =='已评' && it.score >=60}">
		B-
	</c:when>
														<c:when test="${it.scorescript =='已评' && it.score >=50}">
		C
	</c:when>
														<c:otherwise>D</c:otherwise>
													</c:choose>
												</span>
											</div>
											<div class="col-sm-4">
												<input type="number" class="form-control" id="score" min="0"
													max="100" style="color: red; font-size: 24px;" name="score"
													<c:choose>
	<c:when test="${it.scorescript =='已评'}">
		value="${it.score}"
	</c:when>
	<c:when test="${it.scorescript =='待评'}">
		value="80"
	</c:when>
</c:choose>
													placeholder="评阅结果">
											</div>
										</div>
										<div class="form-group">
											<div class="col-sm-offset-2 col-sm-10">
												<button type="submit" class="btn btn-success">记录评阅结果</button>
											</div>
										</div>
									</form>
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
		<script type="text/javascript">
			$("#score").focus();
			$("#score").keyup(function() {
				//alert("filesize="+$("#filesize").val());
				var score = $("#score").val();
				if (score >= 80) {
					$("#score2").html("A+");
				} else if (score >= 75) {
					$("#score2").html("A-");
				} else if (score >= 65) {
					$("#score2").html("B+");
				} else if (score >= 60) {
					$("#score2").html("B-");
				} else if (score >= 50) {
					$("#score2").html("C");
				} else {
					$("#score2").html("D");
				}

			});
		</script>
	</c:if>
	<c:if test="${operate == null}">
		<script type="text/javascript">
			//搜索框
			$("#searchbtn")
					.click(
							function() {
								var kw = $.trim($("#kw").val());
								var qs = "";
								var and = "?";
								var reviewstate_id = "";
								$(
										'input:checkbox[class=reviewstate_id_ck]:checked')
										.each(
												function(k) {
													if (k == 0) {
														reviewstate_id = $(this)
																.val();
													} else {
														reviewstate_id += ','
																+ $(this).val();
													}
												});
								// alert('reviewstate_id=' + reviewstate_id);
								if (reviewstate_id != '') {
									qs += and + "reviewstate_id="
											+ reviewstate_id;
									and = "&";
								}								
								var curriculum_id = "";
								$(
										'input:checkbox[class=curriculum_id_ck]:checked')
										.each(
												function(k) {
													if (k == 0) {
														curriculum_id = $(this)
																.val();
													} else {
														curriculum_id += ','
																+ $(this).val();
													}
												});
								// alert('curriculum_id=' + curriculum_id);
								if (curriculum_id != '') {
									qs += and + "curriculum_id="
											+ curriculum_id;
									and = "&";
								}
								
								var student_group_id = "";
								$(
										'input:checkbox[class=student_group_id_ck]:checked')
										.each(
												function(k) {
													if (k == 0) {
														student_group_id = $(
																this).val();
													} else {
														student_group_id += ','
																+ $(this).val();
													}
												});
								// alert('student_group_id=' + student_group_id);
								if (student_group_id != '') {
									qs += and + "student_group_id="
											+ student_group_id;
									and = "&";
								}
								if (kw != '') {
									qs += and + "kw=" + kw;
									and = "&";
								}
								// alert("qs=" + qs);
								$(location).attr('href',
										'${basePath}${pagename}' + qs);
							});
		</script>
	</c:if>
</body>
</html>