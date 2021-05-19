<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="../inc/bsa_head.jsp"></jsp:include>
    <!-- Custom Styles-->
    <link href="bssets/css/bsat1-style.css" rel="stylesheet"/>
    <title>${pagetitle}-${appName}</title>
    <style type="text/css">
        .panel {
            height: 100px;
        }
    </style>
</head>
<body>
<div id="wrapper">
    <jsp:include page="../inc/bsa_navtop.jsp"></jsp:include>
    <!--/. NAV TOP  -->
    <jsp:include page="../inc/bsa_navside.jsp"></jsp:include>
    <!-- /. NAV SIDE  -->

    <div id="page-wrapper">
        <div class="header">
            <h1 class="page-header">
                ${pagetitle} <small>${subtitle}</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="superadmin/">首页</a></li>
                <li class="active">${pagetitle}</li>
            </ol>
        </div>

        <div id="page-inner">
            <!-- content start -->
            <div class="row">
                <div class="col-md-3 col-sm-12 col-xs-12">
                    <div class="board">
                        <div class="panel panel-primary">
                            <div class="number">
                                <h4>文章数：${articleNumber}</h4>
                            </div>
                            <div class="icon">
                                <i class="fa fa-file-text fa-3x red"></i>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 col-sm-12 col-xs-12">
                    <div class="board">
                        <div class="panel panel-primary">
                            <div class="number">
                                <h4>栏目数：${columnNumber}</h4>
                            </div>
                            <div class="icon">
                                <i class="fa fa-pencil-square-o fa-3x blue"></i>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 col-sm-12 col-xs-12">
                    <div class="board">
                        <div class="panel panel-primary">
                            <div class="number">
                                <h4>
                                    访问量：${visitToday}
                                </h4>
                            </div>
                            <div class="icon">
                                <i class="fa fa-line-chart fa-3x green"></i>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 col-sm-12 col-xs-12">
                    <div class="board">
                        <div class="panel panel-primary">
                            <div class="number">
                                <h4>当前在线：${visitorCount}</h4>
                            </div>
                            <div class="icon">
                                <i class="fa fa-users fa-3x yellow"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-3 col-sm-12 col-xs-12">
                    <!-- 判断当前用户是否拥有指定的权限 -->
                            <!--
                        <shiro:hasPermission name="article:insert">
                            当前用户拥有许可article:insert
                        </shiro:hasPermission>
                        <shiro:hasPermission name="user:insert">
                            当前用户拥有许可user:insert
                        </shiro:hasPermission>
                           -->
                </div>
            </div>
            <!-- content end -->

            <jsp:include page="../inc/bsa_foot.jsp"></jsp:include>
        </div>
        <!-- /. PAGE INNER  -->
    </div>
    <!-- /. PAGE WRAPPER  -->
</div>
<!-- /. WRAPPER  -->
</body>
<script type="text/javascript">

</script>
</html>