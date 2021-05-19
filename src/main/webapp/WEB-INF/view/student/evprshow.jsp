<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<jsp:include page="head.jsp" />
<link href="bssets/css/font-awesome.min.css" rel="stylesheet" />
<link href="bssets/css/checkbox3.min.css" rel="stylesheet">
<title>${pagetitle}-${appName}</title>
<meta content="" name="description" />
</head>
<body>
	<!-- content -->
	<div class="container">
		<div class="row">
			<div class="col-md-6">
				<strong class="am-text-primary am-text-lg">${pagetitle}-作答</strong>
			</div>
			<div class="col-md-6 text-right"></div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<c:choose>
					<c:when test="${item.type==1}">
						<%-- 填空题修改 --%>
						<form class="form-horizontal" method="post">
							<%-- 指定操作类型 --%>
							<input type="hidden" name="operate" value="submitform" />
							<%-- 当前题号 --%>
							<input type="hidden" name="evprid" value="${evpr.id}" />
							<%-- 填空题类型1 --%>
							<input type="hidden" name="evType" value="1" />
							<div class="form-group margin-bottom-sm">
								<div class="col-sm-10">
									<input type="submit" class="btn btn-success" value="提交" />
								</div>
							</div>
							<div id="form1">
								<div class="form-group">
									<label class="col-sm-2 fontsize15em">问题： </label>
									<div class="col-sm-8  text-left">
										<span class="fontsize15em">${item.title}</span>
									</div>
									<div class="col-sm-2"></div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 fontsize15em">答案： </label>
									<div class="col-sm-8 fontsize15em">
										<input type="text" id="answer" name="answer"
											placeholder="请输入答案" required />
									</div>
									<div class="col-sm-2"></div>
								</div>
							</div>
							<div class="form-group margin-bottom-sm">
								<div class="col-sm-10">
									<input type="submit" class="btn btn-success" value="提交" />
								</div>
							</div>
						</form>
						<%-- 填空题 --%>
					</c:when>
					<c:when test="${item.type==3}">
						<form class="form-horizontal" method="post">
							<%-- 指定操作类型 --%>
							<input type="hidden" name="operate" value="submitform" />
							<%-- 选择题类型3 --%>
							<input type="hidden" name="evType" value="3" />
							<%-- 选择题默认4个选项，可以调整为2~8个 --%>
							<input type="hidden" id="itnum3" name="itnum3"
								value="${fn:length(item.answers)}" />
							<%-- 当前题号 --%>
							<input type="hidden" name="evprid" value="${evpr.id}" />
							<div class="form-group margin-bottom-sm">
								<div class="col-sm-10">
									<input type="submit" class="btn btn-success" value="提交" />
								</div>
							</div>
							<div id="form3">
								<div class="form-group">
									<label for="evpContent" class="col-sm-2 control-label">
										<span class="fontsize20em">问题：</span>
									</label>
									<div class="col-sm-8 ">
										<span class="fontsize15em">${item.title}</span>
									</div>
									<div class="col-sm-2"></div>
								</div>
								<c:forEach var="it" items="${item.answers}" varStatus="i">
									<div class="form-group">
										<label for="evpContent" class="col-sm-2">备选答案${i.count}：
										</label>
										<div class="col-sm-10  text-left">
											<div
												class="checkbox3 checkbox-${tagpostfixlist[i.count%fn:length(tagpostfixlist)]} checkbox-inline checkbox-check checkbox-round  checkbox-light">
												<input type="checkbox" id="tevpa3${i.count}"
													name="tevpa3${i.count}"
													<c:if test="${it.reply}">checked</c:if>> <label
													for="tevpa3${i.count}"> &nbsp;&nbsp;&nbsp;&nbsp;
													${it.answer} &nbsp;&nbsp;&nbsp;&nbsp; </label>
											</div>
										</div>
									</div>
								</c:forEach>
							</div>
						</form>
					</c:when>
					<c:when test="${item.type==4}">
						<%-- 判断题的修改 --%>
						<form class="form-horizontal" method="post">
							<%-- 指定操作类型 --%>
							<input type="hidden" name="operate" value="submitform" />
							<%-- 判断题类型4 --%>
							<input type="hidden" name="evType" value="4" />
							<%-- 当前题号 --%>
							<input type="hidden" name="evprid" value="${evpr.id}" />
							<%-- 编号和一致evType --%>
							<div class="form-group margin-bottom-sm">
								<div class="col-sm-10 ">
									<input type="submit" class="btn btn-primary" value="提交" />
								</div>
							</div>
							<div id="form4">
								<div class="form-group">
									<label class="col-sm-2 fontsize15em">问题： </label>
									<div class="col-sm-10  text-left">
										<span class="fontsize15em">${item.title}</span>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 fontsize15em">正确请点击： </label>
									<div class="col-sm-8  text-left">
										<label class="fontsize15em"><input type="radio"
											id="tevpa41" name="tevpa41" value="true"> 正确 </label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 fontsize15em">错误请点击： </label>
									<div class="col-sm-8  text-left">
										<label class="fontsize15em"><input type="radio"
											id="tevpa41" name="tevpa41" value="false"> 错误 </label>
									</div>
								</div>
								<br>
							</div>
						</form>
					</c:when>
					<c:when test="${item.type==5}">
						<%-- 简答题 --%>
						<form class="form-horizontal" method="post"
							enctype="multipart/form-data">
							<%-- 指定操作类型 --%>
							<input type="hidden" name="operate" value="submitform" />
							<%-- 简答题类型5 --%>
							<input type="hidden" name="evType" value="5" />
							<%-- 当前题号 --%>
							<input type="hidden" name="evprid" value="${evpr.id}" />
							<%-- 题号：4-1，第4大题的第1小题 --%>
							<input type="hidden" name="evporderid" value="${evporderid}" />
							<%-- 编号和一致evType --%>
							<div class="form-group margin-bottom-sm">
								<div class="col-sm-10">
									<input type="submit" class="btn btn-success" value="提交" />
								</div>
							</div>
							<div id="form5">
								<div class="form-group">
									<label class="col-sm-2 fontsize15em">问题： </label>
									<div class="col-sm-10  fontsize15em">
										<h4 style="line-height: 2rem;">${item.title}</h4>
									</div>
								</div>
							</div>
							<c:if test="${item.answers[0].correct}">
								<div class="form-group">
									<label class="col-sm-2 fontsize15em">文件 </label>
									<c:if test="${fn:length(filelist)==0}">
										<div class="col-sm-10  text-left">
											提交文件<input type="file" id="file" name="file" placeholder="文件"
												required /> <small>（C语言文件直接上传）</small>
										</div>
									</c:if>
									<c:if test="${fn:length(filelist)>0}">
										<label for="difficulty" class="col-sm-2 "> </label>
										<div class="col-sm-10  text-left">
											你上传的文件，大约占用了${filesize}字节存储空间（最大可用空间
											<fmt:formatNumber value="${(5000000-filesize)/1024/1024}"
												type="number" groupingUsed="false" maxFractionDigits="2" />
											MB）的内容 <br />文件列表如下：<br />
											<ul>
												<c:forEach var="it" items="${filelist}" varStatus="xh">
													<li class="margin-vertical-sm">file${xh.count}: <span
														class="text-danger fontsize15em">${it.filename}</span>;
														&nbsp;&nbsp;&nbsp;&nbsp;文件大小：${it.filesize}字节
														&nbsp;&nbsp;&nbsp;&nbsp;<a
														href="${basePath}${pagename}/${evpr.id}?operate=deletefile&fileid=${it.filehash}"
														class="btn btn-default  btn-sm">删除</a></li>
												</c:forEach>
											</ul>
										</div>
									</c:if>
								</div>
							</c:if>
							<c:if test="${item.answers[1].correct}">
								<div class="form-group">
									<label for="difficulty" class="col-sm-2 ">文字回答： </label>
									<div class="col-sm-10  text-left">
										<span class="text-danger">请自行保存文字回答内容，如果提交未成功，下面文本框中内容会丢失！！！</span>
										<textarea id="answer" name="answer" rows="10" cols="50"
											required>${item.reply}</textarea>
									</div>
								</div>
							</c:if>
						</form>
					</c:when>
					<c:when test="${item.type==6}">
						<%-- 问答题的修改 --%>
						<form class="form-horizontal" method="post"
							enctype="multipart/form-data">
							<%-- 指定操作类型 --%>
							<input type="hidden" name="operate" value="submitform" />
							<%-- 问答题类型6 --%>
							<input type="hidden" name="evType" value="6" />
							<%-- 当前题号 --%>
							<input type="hidden" name="evprid" value="${evpr.id}" />
							<%-- 编号和一致evType --%>
							<div class="form-group margin-bottom-sm">
								<div class="col-sm-10">
									<input type="submit" class="btn btn-success" value="提交" />
								</div>
							</div>
							<div id="form5">
								<div class="form-group">
									<label for="evpContent" class="col-sm-2 ">问题： </label>
									<div class="col-sm-10  text-left">
										<span>${item.title}</span>
									</div>
								</div>
							</div>
							<c:if test="${item.answers[0].correct}">
								<div class="form-group">
									<label for="difficulty" class="col-sm-2 ">文件 </label>
									<c:if test="${fn:length(filelist)==0}">
										<div class="col-sm-10  text-left">
											提交文件<input type="file" id="file" name="file"
												placeholder="文件" required /> <small></small>
										</div>
									</c:if>
									<c:if test="${fn:length(filelist)>0}">
										<label for="difficulty" class="col-sm-2 "> </label>
										<div class="col-sm-10  text-left">
											你上传的文件，大约占用了${filesize}字节存储空间（最大可用空间
											<fmt:formatNumber value="${(5000000-filesize)/1024/1024}"
												type="number" groupingUsed="false" maxFractionDigits="2" />
											MB）的内容 <br />文件列表如下：<br />
											<ul>
												<c:forEach var="it" items="${filelist}" varStatus="xh">
													<li class="margin-vertical-sm">文件：<span
														class="text-danger fontsize15em">${it.filename}</span>;
														&nbsp;&nbsp;&nbsp;&nbsp;文件大小：${it.filesize}字节
														&nbsp;&nbsp;&nbsp;&nbsp;<a
														href="${basePath}${pagename}/${evpr.id}?operate=deletefile&evprid=${evpr.id}&fileid=${it.filehash}"
														class="btn btn-default  btn-sm">删除</a></li>
												</c:forEach>
											</ul>
										</div>
									</c:if>
								</div>
							</c:if>
							<c:if test="${item.answers[1].correct}">
								<div class="form-group">
									<label for="difficulty" class="col-sm-2 ">文字回答： </label>
									<div class="col-sm-10  text-left">
										<span class="text-danger">请自行保存文字回答内容，如果提交未成功，下面文本框中内容会丢失！！！</span>
										<textarea id="answer" name="answer" rows="10" cols="50"
											required>${item.reply}</textarea>
									</div>
								</div>
							</c:if>
						</form>
					</c:when>
					<c:when test="${item.type==7}">
						<%-- 问答题的修改 --%>
						<form class="form-horizontal" method="post"
							enctype="multipart/form-data">
							<%-- 指定操作类型 --%>
							<input type="hidden" name="operate" value="submitform" />
							<%-- 问答题类型6 --%>
							<input type="hidden" name="evType" value="7" />
							<%-- 当前题号 --%>
							<input type="hidden" name="evprid" value="${evpr.id}" />
							<%-- 编号和一致evType --%>
							<div class="form-group margin-bottom-sm">
								<div class="col-sm-10">
									<input type="submit" class="btn btn-success" value="提交" />
								</div>
							</div>
							<div id="form5">
								<div class="form-group">
									<label for="title" class="col-sm-2 ">标题： </label>
									<div class="col-sm-10  text-left">
										<span>${item.title}</span>
									</div>
								</div>
								<div class="form-group">
									<label for="evpcontent" class="col-sm-2 ">题目描述： </label>
									<div class="col-sm-10  text-left">
										<span>${item.evpcontent}</span>
									</div>
								</div>
								<div class="form-group">
									<label for="evpin" class="col-sm-2 ">输入： </label>
									<div class="col-sm-10  text-left">
										<span>${item.evpin}</span>
									</div>
								</div>
								<div class="form-group">
									<label for="evpout" class="col-sm-2 ">输出： </label>
									<div class="col-sm-10  text-left">
										<span>${item.evpout}</span>
									</div>
								</div>
								<div class="form-group">
									<label for="evpexamplein" class="col-sm-2 ">样例输入： </label>
									<div class="col-sm-10  text-left">
										<span>${item.evpexamplein}</span>
									</div>
								</div>
								<div class="form-group">
									<label for="evpexampleout" class="col-sm-2 ">样例输出： </label>
									<div class="col-sm-10  text-left">
										<span>${item.evpexampleout}</span>
									</div>
								</div>
								<div class="form-group">
									<label for="datalimit" class="col-sm-2 ">数据范围限制： </label>
									<div class="col-sm-10  text-left">
										<span>${item.datalimit}</span>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="difficulty" class="col-sm-2 ">文件 </label>
								
									<div class="col-sm-10  text-left">
										提交文件（C++编程题目，提交cpp文件）<input type="file" id="file" name="file"
											placeholder="文件" required /> <small></small>
									</div>
								
								<c:if test="${fn:length(filelist)>0}">
									<label for="difficulty" class="col-sm-2 "> </label>
									<div class="col-sm-10  text-left">
										本次考试你上传的文件列表如下：（包含其他小题的文件）<br />
										<ul>
											<c:forEach var="it" items="${filelist}" varStatus="xh">
												<li class="margin-vertical-sm">文件：<span
													class="text-danger fontsize15em">${it.filename}</span>;
													&nbsp;&nbsp;&nbsp;&nbsp;文件大小：${it.filesize}字节
													&nbsp;&nbsp;&nbsp;&nbsp;<a
													href="${basePath}${pagename}/${evpr.id}?operate=deletefile&evprid=${evpr.id}&fileid=${it.filehash}"
													class="btn btn-default  btn-sm">删除</a></li>
											</c:forEach>
										</ul>
									</div>
								</c:if>
							</div>


						</form>
					</c:when>
				</c:choose>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6"></div>
			<div class="col-md-6 text-right"></div>
		</div>
	</div>
	<!-- /content -->
	<jsp:include page="foot.jsp" />


</body>
</html>