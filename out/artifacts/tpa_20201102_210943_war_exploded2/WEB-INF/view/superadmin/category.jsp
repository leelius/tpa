<!------------------------------->
<!---- Generated By Denny(luhox@qq.com), cslite for sm -->
<!---- Remarks: ItemListHTML-->
<!---- Date Generated: 2018-07-02 22:50:07-->
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
<link href="bssets/css/gjyj.css" rel="stylesheet" />
<link href="bssets/css/menutree.css" rel="stylesheet" />
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
				<h1 class="page-header">${pagetitle}</h1>
			</div>
			<div id="page-inner">
				<!-- content start -->
				<c:choose>
					<c:when test="${operate == null}">
						<%-- 
						<!-- 演示菜单 -->
						<div id="nav" class="row margin-xs">
							<ul>
								<li class="active"><a href="index.html">首页</a></li>
								<li><a href="news">本院概况</a>
									<ul>
										<li><a href="case">发展历程</a></li>
										<li><a href="case">发展定位</a></li>
										<li><a href="case">愿望使命</a></li>
										<li><a href="case">组织架构</a></li>
										<li><a href="case">新闻动态</a></li>
										<li><a href="case">通知公告</a></li>
									</ul></li>
								<li><a href="solution">研究基地</a></li>
								<li><a href="service">研究队伍</a></li>
								<li><a href="case">学术活动</a></li>
								<li><a href="honor">党群建设</a></li>
								<li><a href="honor">研究成果</a></li>
								<li><a href="honor">人才招聘</a></li>
							</ul>
						</div>
						<!--/演示菜单-->
 --%>
						<!-- 控制菜单 -->
						<div class="row">
							<div class="col-sm-9 col-md-9">
								<fieldset>
									<legend>${tabletitle}</legend>
									<%-- 参考代码 --%>
									<%-- 
		<div class="tree well">
			<ul>
				<li><span><i class="icon-folder-open"></i> Parent</span>
					<a href="">Goes somewhere</a>
					<ul>
						<li><span><i class="icon-minus-sign"></i> Child</span>
							<a href="">Goes somewhere</a>
							<ul>
								<li><span><i class="icon-leaf"></i> Grand
										Child</span> <a href="">Goes somewhere</a></li>
							</ul></li>
					</ul></li>
			</ul>
		</div>
		 --%>
									<div class="tree well">
										<a href='${pagename}?operate=add&id=0'><span>新增一级栏目</span>
										</a>
										<ul>
											<c:forEach var="it" items="${menu}" varStatus="i">
												<li>
													<%-- 已经隐藏的菜单，显示为灰色 --%> <c:choose>
														<c:when test="${it.is_actived==false}">
															<span class="menuname text-danger">${it.name}&nbsp;&nbsp;(${fn:length(it.submenu)})</span>
														</c:when>
														<c:when test="${it.is_actived}">
															<span class="menuname text-disable">${it.name}&nbsp;&nbsp;(${fn:length(it.submenu)})</span>
														</c:when>
													</c:choose> <%-- 控制按钮 --%> &nbsp;&nbsp;&nbsp;&nbsp;<a
													class="btn btn-primary"
													href="${pagename}?operate=edit&id=${it.id}">修改</a>&nbsp; <c:choose>
														<c:when test="${it.is_actived}">
															<a class="btn btn-success"
																href='${pagename}?operate=hide&id=${it.id}'>隐藏</a>&nbsp;
</c:when>
														<c:when test="${it.is_actived==false}">
															<a class="btn btn-default"
																href='${pagename}?operate=show&id=${it.id}'>显示</a>&nbsp;
</c:when>
													</c:choose> <c:choose>
														<c:when test="${i.count==1}">
															<span class="text-gray" title="第1个，不能上移">上移</span>&nbsp;
</c:when>
														<c:otherwise>
															<a class="btn btn-info"
																href='${pagename}?operate=moveup&id=${it.id}'>上移</a>&nbsp;
</c:otherwise>
													</c:choose> <c:choose>
														<c:when test="${i.count==fn:length(menu)}">
															<span class="text-gray" title="最后1个，不能下移">下移</span>&nbsp;
</c:when>
														<c:otherwise>
															<a class="btn btn-warning"
																href='${pagename}?operate=movedown&id=${it.id}'>下移</a>&nbsp;
</c:otherwise>
													</c:choose> <a class="btn btn-primary"
													href='${pagename}?operate=add&id=${it.id}'
													title='在当前一级栏目下，新增二级栏目'>新增</a>&nbsp; <c:choose>
														<c:when test="${fn:length(it.submenu)>0}">
															<span class="text-disable" title="有子栏目，不能删除">删除</span>&nbsp;
</c:when>
														<c:otherwise>
															<button value="${it.id}"
																class="deleteitem btn btn-default">删除</button>

														</c:otherwise>
													</c:choose> <c:if test="${fn:length(it.submenu)>0}">
														<ul>
															<%-- 第2级菜单开始 --%>
															<c:forEach var="it2" items="${it.submenu}" varStatus="j">
																<li>
																	<%-- 已经隐藏的菜单，显示为灰色 --%> <c:choose>
																		<c:when test="${it2.is_actived==false}">
																			<span class="menuname am-text-danger">${it2.name}&nbsp;&nbsp;(${fn:length(it2.submenu)})</span>
																		</c:when>
																		<c:when test="${it2.is_actived}">
																			<span class="menuname text-disable">${it2.name}&nbsp;&nbsp;(${fn:length(it2.submenu)})</span>
																		</c:when>
																	</c:choose> <%-- 控制按钮 --%> &nbsp;&nbsp;&nbsp;&nbsp;<a
																	class="btn btn-primary"
																	href="${pagename}?operate=edit&id=${it2.id}">修改</a>&nbsp;
																	<c:choose>
																		<c:when test="${it2.is_actived}">
																			<a class="btn btn-success"
																				href='${pagename}?operate=hide&id=${it2.id}'>隐藏</a>&nbsp;
</c:when>
																		<c:when test="${it2.is_actived==false}">
																			<a class="btn btn-default"
																				href='${pagename}?operate=show&id=${it2.id}'>显示</a>&nbsp;
</c:when>
																	</c:choose> <c:choose>
																		<c:when test="${j.count==1}">
																			<span class="text-gray" title="第1个，不能上移">上移</span>&nbsp;
</c:when>
																		<c:otherwise>
																			<a class="btn btn-info"
																				href='${pagename}?operate=moveup&id=${it2.id}'>上移</a>&nbsp;
</c:otherwise>
																	</c:choose> <c:choose>
																		<c:when test="${j.count==fn:length(it.submenu)}">
																			<span class="text-gray" title="最后1个，不能下移">下移</span>&nbsp;
</c:when>
																		<c:otherwise>
																			<a class="btn btn-warning"
																				href='${pagename}?operate=movedown&id=${it2.id}'>下移</a>&nbsp;
</c:otherwise>
																	</c:choose> <%-- 
																	 <a href='${pagename}?operate=add&id=${it2.id}'
																	title='在当前一级栏目下，新增二级栏目'>新增</a>&nbsp;
																	 --%> <c:choose>
																		<c:when test="${fn:length(it2.submenu)>0}">
																			<span class="text-disable" title="有子栏目，不能删除">删除</span>&nbsp;
</c:when>
																		<c:otherwise>
																			<button value="${it2.id}"
																				class="deleteitem btn btn-default">删除</button>
																		</c:otherwise>
																	</c:choose>
																	<ul>
																		<%-- 第3级内容begin --%>
																		<c:forEach var="it3" items="${it2.submenu}"
																			varStatus="k">
																			<li>
																				<%-- 已经隐藏的菜单，显示为灰色 --%> <c:choose>
																					<c:when test="${it3.is_actived}">
																						<span class="am-text-danger">${it3.name}&nbsp;&nbsp;(${fn:length(it3.submenu)})</span>
																					</c:when>
																					<c:when test="${it3.is_actived==false}">
																						<span class="text-gray">${it3.name}&nbsp;&nbsp;(${fn:length(it3.submenu)})</span>
																					</c:when>
																				</c:choose> <%-- 控制按钮 --%> &nbsp;&nbsp;&nbsp;&nbsp;<a
																				href="${pagename}?operate=edit&id=${it3.id}">修改</a>&nbsp;
																				<c:choose>
																					<c:when test="${it3.is_actived}">
																						<a href='${pagename}?operate=hide&id=${it3.id}'>隐藏</a>&nbsp;
</c:when>
																					<c:when test="${it3.is_actived==false}">
																						<a href='${pagename}?operate=show&id=${it3.id}'>显示</a>&nbsp;
</c:when>
																				</c:choose> <c:choose>
																					<c:when test="${k.count==1}">
																						<span class="text-gray" title="第1个，不能上移">上移</span>&nbsp;
</c:when>
																					<c:otherwise>
																						<a href='${pagename}?operate=moveup&id=${it3.id}'>上移</a>&nbsp;
</c:otherwise>
																				</c:choose> <c:choose>
																					<c:when test="${k.count==fn:length(it2.submenu)}">
																						<span class="text-gray" title="最后1个，不能下移">下移</span>&nbsp;
</c:when>
																					<c:otherwise>
																						<a
																							href='${pagename}?operate=movedown&id=${it3.id}'>下移</a>&nbsp;
</c:otherwise>
																				</c:choose> <a href='${pagename}?operate=delete&id=${it3.id}'>删除</a>&nbsp;
																			</li>
																		</c:forEach>
																		<%-- 第3级内容end --%>
																	</ul>
																</li>
															</c:forEach>
														</ul>
													</c:if>
												</li>
											</c:forEach>
										</ul>

									</div>
								</fieldset>
							</div>
							<!-- 列表结束 -->
						</div>
						<!-- /控制菜单 -->
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
												type="hidden" name="category_parentid"
												value="${category_parentid}" />

											<!-- selectbox begin -->
											<div class="form-group">
												<label for="columninfo_id" class="col-sm-2 control-label">父栏目</label>
												<div class="col-sm-10">
													<span class="form-control" readonly>${parent_catename}</span>
												</div>
											</div>
											<!-- selectbox end -->
											<div class="form-group">
												<label for="name" class="col-sm-2 control-label">名称</label>
												<div class="col-sm-10">
													<input type="text" class="form-control" id="name"
														name="name" placeholder="名称">
												</div>
											</div>
											<div class="form-group">
												<label for="page_description" class="col-sm-2 control-label">页面描述</label>
												<div class="col-sm-10">
													<input type="text" class="form-control"
														id="page_description" name="page_description"
														placeholder="页面描述，可以省略">
												</div>
											</div>
											<div class="form-group">
												<label for="page_keyword" class="col-sm-2 control-label">页面关键词</label>
												<div class="col-sm-10">
													<input type="text" class="form-control" id="page_keyword"
														name="page_keyword" placeholder="页面关键词，可以省略">
												</div>
											</div>
											<div class="form-group">
												<label for="outlink" class="col-sm-2 control-label">外部链接url</label>
												<div class="col-sm-10">
													<input type="text" class="form-control" id="outlink"
														name="outlink"
														placeholder="外部链接url，可以省略，如果此项有内容，表示栏目直接定位到一个url">
												</div>
											</div>
											<div class="form-group">
												<div class="col-sm-offset-2 col-sm-10">
													<button type="submit" class="btn btn-success">添加栏目</button>
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
												type="hidden" name="category_parentid"
												value="${it.category_parentid}" />
											<!-- selectbox begin -->
											<div class="form-group">
												<label for="columninfo_id" class="col-sm-2 control-label">父栏目</label>
												<div class="col-sm-10">
													<span class="form-control" readonly>${parent_catename}</span>
												</div>
											</div>
											<!-- selectbox end -->

											<div class="form-group">
												<label for="name" class="col-sm-2 control-label">名称</label>
												<div class="col-sm-10">
													<input type="text" class="form-control" id="name"
														name="name" placeholder="名称" value="${it.name}">
												</div>
											</div>
											<div class="form-group">
												<label for="page_description" class="col-sm-2 control-label">页面描述</label>
												<div class="col-sm-10">
													<input type="text" class="form-control"
														id="page_description" name="page_description"
														placeholder="页面描述" value="${it.page_description}">
												</div>
											</div>
											<div class="form-group">
												<label for="page_keyword" class="col-sm-2 control-label">页面关键词</label>
												<div class="col-sm-10">
													<input type="text" class="form-control" id="page_keyword"
														name="page_keyword" placeholder="简称"
														value="${it.page_keyword}">
												</div>
											</div>
											<div class="form-group">
												<label for="outlink" class="col-sm-2 control-label">外部链接url</label>
												<div class="col-sm-10">
													<input type="text" class="form-control" id="outlink"
														name="outlink" placeholder="外部链接url" value="${it.outlink}">
												</div>
											</div>
											<div class="form-group">
												<div class="col-sm-offset-2 col-sm-10">
													<button type="submit" class="btn btn-success">更新分类信息</button>
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

				$("#form1").submit(function(e) {

					if ($("#name").val() == '') {
						alert("名称必须输入！");
						$("#name").focus();
						return false;
					}
				});
			});
		</script>
	</c:if>
	<c:if test="${operate == null}">
		<script type="text/javascript">
			
		</script>
	</c:if>

</body>
</html>


