<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="head.jsp"/>
    <title>${pagetitle}-${appName}</title>
    <meta content="" name="description"/>
</head>
<body>
<jsp:include page="header.jsp"/>
<!-- content begin-->
<div class="container">
    <div class="row">
        <div class="col-md-6">
            当前登录：${sessionScope.tpauser.real_name}[${sessionScope.tpauser.alias}]
        </div>
        <div class="col-md-6 text-right">
            <a href="${basePath}student/stupassword" class="btn btn-warning">修改密码</a>&nbsp;&nbsp;
            <a href="${basePath}logout" class="btn btn-info">退出登录</a>
        </div>
        <div class="hidden">
            show_experiment=${show_experiment.value},
            show_evaluation=${show_evaluation.value}
        </div>
    </div>
    <!-- 实验 -->
    <c:if test="${show_experiment.value=='true'}">
        <div class="row">
            <div class="col-md-12">
                <c:forEach var="curr" items="${curriculums}" varStatus="i">
                    <div class="panel panel-default margin-vertical-sm">
                        <div class="panel-heading">
                            <h3 class="panel-title">实验&nbsp;&nbsp;&nbsp;&nbsp;课程名称：${curr.subject_name}</h3>
                        </div>
                        <div class="panel-body ">
                            <c:forEach var="exp" items="${experimentCurriculums}" varStatus="j">
<%--                                <c:set var="snoexpid" value="${sessionScope.studentuser.alias}${exp.experiment_id}"/>--%>
                                <%--  julius --%>
<%--                                <c:set var="snoexpid" value="${user.alias}${exp.experiment_id}"/>--%>
                                <c:set var="snoexpid" value="${sessionScope.tpauser.alias}${exp.experiment_id}"/>
                                <c:if test="${curr.id==exp.curriculum_id}">
                                    <div class="row">
                                        <div class="col-md-2 text-right">
                                            <c:if test="${exprecordmap[snoexpid]==null}">
                                                <span class="text-danger">尚未提交</span>
                                            </c:if>
                                            <%-- julius --%>
                                            <c:if test="${exprecordmap[snoexpid]!=null}">
                                                <span class="text-success">已提交</span>
                                            </c:if>
                                        </div>
                                        <div class="col-md-6 text-truncate">
                                            <a href="${basePath}student/expshow/${exp.id}"> 实验${j.count}-${exp.experiment_title} </a>
                                        </div>
                                        <div class="col-md-4">
                                            截止时间：<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${exp.closing_gmt_date}"/>
                                        </div>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </c:forEach>

            </div>
        </div>
    </c:if>
    <!-- /实验 -->

    <!-- 考核 -->
    <c:if test="${show_evaluation.value=='true'}">
        <div class="row">
            <div class="col-md-12">
                <c:forEach var="ev" items="${evaluations}" varStatus="i">
                    <div class="panel panel-default margin-vertical-sm">
                        <div class="panel-heading">
                            <h3 class="panel-title">考核&nbsp;&nbsp;&nbsp;&nbsp;课程名称：${ev.subject_name}</h3>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-12 text-center">${ev.title}</div>
                            </div>
                            <div class="row">
                                <div class="col-md-12 text-center">
                                    考核时间：<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
                                                         value="${ev.begin_gmt_date}"/>~<fmt:formatDate
                                        pattern="yyyy-MM-dd HH:mm:ss" value="${ev.end_gmt_date}"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12 text-center">
                                    <c:if test="${ev.begin_gmt < now && now < ev.end_gmt}">
                                        <a href="student/evshow/${ev.id}" class="btn btn-success">点击进入考试</a>
                                    </c:if>
                                </div>
                            </div>

                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </c:if>
    <!-- /考核 -->

    <div class="row">
        <div class="col-md-12"></div>
    </div>
    <jsp:include page="footer.jsp"/>
</div>
<!-- /content end-->

<jsp:include page="foot.jsp"/>

</body>
</html>