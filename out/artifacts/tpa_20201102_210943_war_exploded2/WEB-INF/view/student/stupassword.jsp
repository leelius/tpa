<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2021/4/11
  Time: 12:49
  To change this template use File | Settings | File Templates.
--%>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="head.jsp"/>
    <!-- Custom Styles-->
    <link href="bssets/css/bsat1-style1.css" rel="stylesheet" />
    <title>${pagetitle}-${appName}</title>
</head>
<body>
<div id="wrapper">
    <div id="page-wrapper">
        <div class="header">
            <h1 class="page-header">
                ${pagetitle}
            </h1>
        </div>
        <div id="page-inner">
            <!-- content start -->
            <div class="row">
                <div class="col-xs-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div class="card-title">
                                <div class="title">${formtitle}</div>
                            </div>
                        </div>
                        <div class="panel-body">
                            <form id="form1" class="form-horizontal" method="post" action="${pagename}">
                                <div class="form-group">
                                    <label for="password2" class="col-sm-2 control-label">新密码</label>
                                    <div class="col-sm-10">
                                        <input type="password" class="form-control" id="password2"
                                               name="password2" placeholder="新密码" autocomplete="off">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="password3" class="col-sm-2 control-label">再次输入</label>
                                    <div class="col-sm-10">
                                        <input type="password" class="form-control" id="password3"
                                               name="password3" placeholder="再次输入" autocomplete="off">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="submit" class="btn btn-success">更新密码</button>
                                        <%--window.history.back(-1)，后退到上一页--%>
<%--                                        <button class="btn btn-success" style="float: right;color: #fff" onclick="window.history.back(-1);">--%>
<%--                                        返回--%>
<%--                                        </button>--%>
                                    </div>
                                </div>
                            </form>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <%--window.history.back(-1)，后退到上一页--%>
                                    <button class="btn btn-success" style="float: right;color: #fff" onclick="window.history.back(-1);">
                                        返回
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- content end -->
        </div>
        <!-- /. PAGE INNER  -->
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        $("#form1").submit(function(e) {
            if ($("#password2").val() == '') {
                alert("新密码必须输入！");
                $("#password2").focus();
                return false;
            }
            if ($("#password3").val() == '') {
                alert("新密码必须再次输入！");
                $("#password3").focus();
                return false;
            }
        });
    });
</script>
</body>
</html>