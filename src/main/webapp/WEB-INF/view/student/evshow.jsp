<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<jsp:include page="head.jsp" />
<title>${pagetitle}-${appName}</title>
<meta content="" name="description" />
<link href="${basePath}bssets/jebox/skin/jebox.css" rel="stylesheet" />
<link href="bssets/css/font-awesome.min.css" rel="stylesheet" />
</head>
<body>

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
		</div>
		<div class="row">
			<div class="col-md-12">
				<h3 class="margin-vertical-sm text-center">${evaluation.title}</h3>
			</div>
			<div class="col-md-12">
				<span class="itemIntro">答卷说明：</span>
				<ol>
					<li>本试卷4个大题，满分100分，90分钟完卷</li>
					<li>开始时间<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
							value="${evaluation.begin_gmt_date}" />，结束时间<fmt:formatDate
							pattern="yyyy-MM-dd HH:mm:ss" value="${evaluation.end_gmt_date}" /></li>
					<li>本次考试为闭卷考试</li>
					<li>本试卷适用于${evaluation.student_group_name}</li>
				</ol>
			</div>
			<div class="col-md-12">
				<span id="clock" class="text-danger"></span>
				<input type="button" value="交卷"  id="submitbtn"
					class="btn btn-danger" />
			</div>
			<div class="col-md-12">
				<c:forEach var="it" items="${mqlist}" varStatus="i">
					<div class="margin-vertical-xs">
						<h3>第${i.count}题：${it.type.name}（共计${it.number*it.score}分，共${it.number}题，每题${it.score}分）</h3>
					</div>
					<span>必须在弹出窗口作答！</span>
					<br><br>
					<c:forEach var="ep" items="${it.pevplist}" varStatus="j">
						<div class="dtb">						
						<c:if test="${ep.submited==false}">
							<button class="evprbtn btn btn-info" value="${ep.id}"><i class="fa fa-check-square-o" aria-hidden="true">作答</i></button>
						</c:if>
						<c:if test="${ep.submited}">
							<button class="evprbtn btn btn-warning" value="${ep.id}"><i class="fa fa-circle-o-notch" aria-hidden="true">重新作答</i></button>
						</c:if>
						
						<a class="btn btn-info" target="_blank" href="${basePath}student/evprshow/${ep.id}">在新窗口作答</a>
						<%--  --%>
						<h4>${j.count}. ${ep.title}</h4>
							<c:if test="${ep.submited}">
								<c:if test="${it.type.id >= 5}">
									<h5 class="text-warning">你已作答！</h5>									
								</c:if>
								<c:if test="${it.type.id < 5}">
									<h5 class="text-warning">你已作答：${ep.reply}</h5>
								</c:if>
							</c:if>
						</div>
						<hr>
					</c:forEach>
				</c:forEach>


			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
	<!-- /content -->
	<jsp:include page="foot.jsp" />
	<script src="${basePath}bssets/jebox/jquery.jebox.js"></script>
	<script>
		$(document).ready(function() {
			$(".evprbtn").click(function(){
				//alert("url="+"${basePath}student/evprshow/"+$(this).val());
				jeBox({
						title : "考核回答面板",
						type : 'iframe',
						maxBtn : true,
						padding : "0",
						boxSize : [ "90%", "90%" ],
						content : [ "${basePath}student/evprshow/"+$(this).val(),
								'no' ], //iframe的url，no代表不显示滚动条
						maskLock : true
					});
			});
			displayTime();
			$("#submitbtn").click(function() {
				if (confirm('你确定要结束考试，提交试卷了吗？')) {
					window.location.href = "${basePath}student/evsubmit/${evid}";
				}
			});
		});
		function displayTime() {
			var elt = document.getElementById("clock");
			if (leftTime < 0) {
				elt.innerHTML = "考试已经结束啦！";
				submitform();
			} else {
				//var endTime = new Date("2017/06/21 15:58:00");
				var endTime = new Date(
						"<fmt:formatDate	pattern='yyyy/MM/dd HH:mm:59' value='${end}' />");
				var now = new Date();
				var nowYear = now.getFullYear();
				var nowMonth = now.getMonth() + 1;
				var nowDay = now.getDate();

				var leftTime = endTime.getTime() - now.getTime();
				var ms = parseInt(leftTime % 1000).toString();
				leftTime = parseInt(leftTime / 1000);
				var o = Math.floor(leftTime / 3600);
				var d = Math.floor(o / 24);
				var m = Math.floor(leftTime / 60 % 60);
				var s = leftTime % 60;

				//elt2.innerHTML = nowYear + " 年 " + nowMonth + " 月 " + nowDay + " 日 ";
				//elt.innerHTML = nowYear + " 年 " + nowMonth + " 月 " + nowDay + " 日 " + o + "小时:" + m + "分:" + s + "秒:" + ms.charAt(0);
				elt.innerHTML = "距离考试结束，还剩：" + o + "小时:" + m + "分:" + s + "秒:"
						+ ms.charAt(0);
				setTimeout(displayTime, 100);
			}
		}
	</script>
</body>
</html>