<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<!-- content-begin -->
<div class="container">
    <div class="row">
        <img src="bssets/tpa/top.jpg" class="img-responsive center-block"/>
    </div>
</div>
<!-- Fixed navbar -->
<div class="container ">
    <div class="row ">
        <nav class="navbar navbar-default navbar-inverse ">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed"
                            data-toggle="collapse" data-target="#navbar" aria-expanded="false"
                            aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span> <span
                            class="icon-bar"></span> <span class="icon-bar"></span> <span
                            class="icon-bar"></span>
                    </button>
                </div>
                <div id="navbar" class="navbar-collapse collapse ">
                    <ul class="nav navbar-nav center-block "
                        style="position: relative; width: 760px; margin: 0 15%;">
                        <li class="active"><a href="${basePath}">网站首页</a></li>
                        <c:forEach var="it" items="${menus}" varStatus="i">
                            <c:choose>
                                <c:when test="${it.outlink == '' }">
                                    <li><a href="${basePath}c/${it.id}" > ${it.name}</a></li>
                                </c:when>
                                <c:when test="${it.outlink != '' }">
                                    <li><a href="${it.outlink}" > ${it.name}</a></li>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </ul>
                </div>
                <!--/.nav-collapse -->
            </div>
        </nav>
    </div>
</div>
<!-- /Fixed navbar -->