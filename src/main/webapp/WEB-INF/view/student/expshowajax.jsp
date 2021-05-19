<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<jsp:include page="head.jsp" />
<jsp:include page="../inc/ket.jsp"></jsp:include>
<title>${pagetitle}-${appName}</title>
<meta content="" name="description" />
</head>
<body>
	<jsp:include page="header.jsp" />
	<!-- content -->
	<div class="container">
		<div class="row">
			<div class="col-md-6">
				当前登录：${sessionScope.tpauser.real_name}[${sessionScope.tpauser.alias}]
			</div>
			<div class="col-md-6 text-right">
				<a href="${basePath}logout" class="btn btn-warning">修改密码</a>&nbsp;&nbsp;<a
					href="${basePath}logout" class="btn btn-info">退出登录状态</a>
			</div>
			<div class="hidden">show_experiment=${show_experiment.value},
				show_evaluation=${show_evaluation.value}</div>
		</div>

		<!-- 实验 -->
		<c:if test="${show_experiment.value=='true'}">

			<div class="row">
				<div class="col-md-12">
					<a class="btn btn-info" href="${basePath}/student">返回实验列表</a>
					<h2>实验：${exp.title}</h2>
					<c:if test="${exprecord != null}">
						<div>你已经完成该实验${exprecord.scorescript}</div>
					</c:if>
				</div>
				<hr />
				<div class="col-md-12">
					<!-- 表单开始 -->
					<form action="${basePath}student/expresult" id="form1"
						method="post" enctype="multipart/form-data">
						<input type="hidden" name="lecid" value="${lec.id}" /> <input
							type="hidden" name="expid" value="${exp.id}" /> <input
							type="hidden" id="operate" name="operate" value="clicksubmit" />
						<input type="hidden" id="changedflag" name="changedflag"
							value="nochanged" /> <input type="hidden" id="mainflow"
							name="mainflow" /> <input type="hidden" id="result"
							name="result" />
						<div class="itemIntro am-margin-vertical-xl">
							<h3>一． 实验目的及要求：</h3>
						</div>
						<div class="itemIntrotitle am-margin-left-xl">
							<p>${exp.purposes_requirement}</p>
						</div>
						<hr />
						<div class="itemIntro am-margin-vertical-xl">
							<h3>二． 实验内容：</h3>
						</div>
						<div class="itemIntrotitle am-margin-left-xl">
							<p>${exp.content}</p>
						</div>
						<hr />
						<div id="mainflowtitle" class="itemIntro am-margin-vertical-xl">
							<h2>三． 实验主要流程、基本操作：</h2>
						</div>
						<div id="itemContent">
							<div class="itemIntrotitle">
								按步骤完成以上实验内容，并用你自己的语言记录实验主要流程、基本操作
								<c:if test="${closetime>now}">
									<textarea class="form-control" id="mainflowet"
										name="mainflowet"
										style="width: 100%; height: 300px; visibility: hidden;">${exprecord.mainflow}</textarea>
								</c:if>
								<c:if test="${closetime<now}">
									<div style="background-color: #eee;">${exprecord.mainflow}</div>
								</c:if>
							</div>
						</div>
						<div class="expResultdiv">
							<h3>文件上传</h3>
							<c:if test="${closetime>now}">
								<div class="stuuploadfiles">
									你上传了${fn:length(filelist)}个文件，大约占用了${filesize}字节存储空间，还可以上传${exp.filesize-filesize}字节（
									<fmt:formatNumber value="${(exp.filesize-filesize)/1024/1024}"
										type="number" groupingUsed="false" maxFractionDigits="2" />
									MB）的内容 <br />文件列表如下：<br />
									系统文件上传要求：单个文件大小不能超过10M，如果总量超出10M，压缩时分卷，多次上传 <br />
									<hr>
									<ul>
										<c:forEach var="it" items="${filelist}" varStatus="xh">
											<%-- 
									<li class="am-margin-vertical"><a
										href="user/expshow?operate=deletefile&expid=${expid}&fileid=${it.filehash}"
										class="am-btn am-btn-warning am-round am-btn-xs">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
										target="_blank"
										href="expfiles/s${exp.subjectId}/e${exp.id}/u${exprecord.username}/${it.filename}">${xh.count}.${it.filename}</a>&nbsp;&nbsp;&nbsp;&nbsp;大小：${it.filesize}字节</li>
									--%>
											<li class="am-margin-vertical"><span
												class="am-text-danger">第${xh.count}个文件：${it.filename};
													&nbsp;&nbsp;&nbsp;&nbsp;文件大小：${it.filesize}字节</span>
												&nbsp;&nbsp;&nbsp;&nbsp;<a
												href="student/expshow/${lec.id}?operate=deletefile&fileid=${it.filehash}"
												class="btn btn-default">删除</a></li>
										</c:forEach>
									</ul>
									<hr>
								</div>
								<p>
									<c:set var="itemnumber" scope="page" value="1" />
									<input type="hidden" id="itemnumber" name="itemnumber"
										value="${itemnumber}" />
								</p>
								<br />
								<div id="uploadfiles">
									上传文件 ： <span class="text-danger">请注意：上传文件如果和已有文件同名，会自动覆盖原文件！！！</span>
									<div>
										<input type="file" id="file1" name="uploadfile" />
									</div>
								</div>
								<hr />

							</c:if>
							<c:if test="${closetime<now && fn:length(filelist)>0}">
						文件列表如下：<br />
								<hr>
								<ul>
									<c:forEach var="it" items="${filelist}" varStatus="xh">
										<%-- 
								<li class="am-margin-vertical"><a
									href="user/expshow?operate=deletefile&expid=${expid}&fileid=${it.filehash}"
									class="am-btn am-btn-warning am-round am-btn-xs">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
									target="_blank"
									href="expfiles/s${exp.subjectId}/e${exp.id}/u${exprecord.username}/${it.filename}">${xh.count}.${it.filename}</a>&nbsp;&nbsp;&nbsp;&nbsp;大小：${it.filesize}字节</li>
								--%>
										<li class="am-margin-vertical"><span
											class="am-text-danger">第${xh.count}个文件：${it.filename};
												&nbsp;&nbsp;&nbsp;&nbsp;文件大小：${it.filesize}字节</span></li>
									</c:forEach>
								</ul>
								<hr>
							</c:if>
						</div>
						<hr />
						<div id="resulttitle" class="itemIntro am-margin-vertical-xl">
							<h2>四．实验结果的分析与评价</h2>
						</div>
						<div class="itemIntrotitle">
							用你自己的语言记录你对实验结果的分析与评价
							<c:if test="${closetime>now}">
								<textarea class="form-control" id="resultet" name="resultet"
									style="width: 100%; height: 300px; visibility: hidden;">${exprecord.result}</textarea>
							</c:if>
							<c:if test="${closetime<now}">
								<div style="background-color: #eee;">${exprecord.result}</div>
							</c:if>
						</div>
						<br /> <input type="button" value="保存" id="submitbtn"
							class="btn btn-secondary" />
					</form>
					<!-- 表单结束 -->
				</div>
			</div>
		</c:if>
		<!-- /实验 -->
		<div class="row">
			<div class="col-md-12"></div>
		</div>

		<jsp:include page="footer.jsp" />
	</div>
	<!-- /content -->
	<jsp:include page="foot.jsp" />
	<c:if test="${closetime>now}">
		<script type="text/javascript">
			//add your script at here!
			//实例化编辑器
			var editor1;
			var editor2;
			KindEditor
					.ready(function(K) {
						editor1 = K
								.create(
										'textarea[name="mainflowet"]',
										{
											cssPath : '${basePath}bssets/ket/plugins/code/prettify.css',
											uploadJson : '${basePath}admin/uploadfile',
											fileManagerJson : '${basePath}admin/filemanager',
											allowFileManager : true,
											afterCreate : function() {
											}
										});
						editor2 = K
								.create(
										'textarea[name="resultet"]',
										{
											cssPath : '${basePath}bssets/ket/plugins/code/prettify.css',
											uploadJson : '${basePath}admin/uploadfile',
											fileManagerJson : '${basePath}admin/filemanager',
											allowFileManager : true,
											afterCreate : function() {
											}
										});
					});

			function submitform() {
				//alert("submitform");
				var mainflow = editor1.html();
				//alert("mainflow="+mainflow);
				if (mainflow == null || mainflow == "") {
					mainflow = " ";
				}
				var result = editor2.html();
				if (result == null || result == "") {
					result = " ";
				}
				$("#mainflow").val(mainflow);
				$("#result").val(result);
				$("#form1").submit();
			}
			$(document)
					.ready(
							function() {
								$("#submitbtn").click(function() {
									//alert("submitbtn_click");
									submitform();
								});								
								$("#update_btn").click(function() {			
							        var uploadfile = $("#uploadfile").val();
							        //alert('准备更新用户头像:' + uploadfile);
							        if(uploadfile==''){
							            $("#uploadinfo").html("请先选择需要上传的文件！");
							        }else{                  
							            //构建新的表单提交数据（只提交文本域）
							            var form  = $("<form id='formUpload' method='post' enctype='multipart/form-data'></form>");
							            // 设置属性  
							            form.attr('action', 'admin/ajaxheadimgupload');  
							            form.attr('method', 'post');                
							            var uploadfile = $("#uploadfile");
							            form.append(uploadfile.clone()); //获取文件选择控件添加到新表单
							            $("#uploadinfo").before(uploadfile); //克隆添加到原来表单
							            form.ajaxSubmit({
							                //提交前的回调函数
							                beforeSubmit: function () {
							                    $("#uploadinfo").html("正在上传图片...");
							                    $("#uploadinfo").after("<img src='bssets/i/loadingAnimation.gif'  id='loading'/>").hide();
							                },
							                //提交后的回调函数
							                success: function (data) {
							                    //alert('fileName='+data.fileName);
							                    $('#wechat_headimg').attr('src',data.fileName);
							                    $('#wechat_headimgurl').val(data.fileName);
							                    $('#myModal').modal('hide');
							                    $("#loading").remove();
							                },
							                error: function (error) { alert(error); },
							                url: 'admin/ajaxheadimgupload', /*设置post提交到的页面*/
							                type: "post", /*设置表单以post方法提交*/
							                dataType: "json" /*设置返回值类型为文本*/
							            });
							        }
							    });
							});
		</script>
	</c:if>
</body>
</html>