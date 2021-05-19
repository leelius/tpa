<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
<jsp:include page="../inc/bsa_head.jsp"></jsp:include>

<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title></title>

<!-- HTML5 shim 和 Respond.js 是为了让 IE8 支持 HTML5 元素和媒体查询（media queries）功能 -->
<!-- 警告：通过 file:// 协议（就是直接将 html 页面拖拽到浏览器中）访问页面时 Respond.js 不起作用 -->
<!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body class="margin-xs">
	<!--  content start   -->

	<!--  读取一个对象，直接添加   -->
	<hr>
	<h2>读取一个json对象，直接添加</h2>
	<button id="readbookinfojson" class="btn btn-success">读取书本信息json</button>
	<br>
	<br>
	<br>编号：
	<span id="id"></span>
	<br>书名：
	<span id="name"></span>
	<br>作者：
	<span id="author"></span>
	
    <!--  读取一个对象，直接添加   -->
    <hr>
    <h2>读取一个xml对象，直接添加</h2>
    <button id="readbookinfoxml" class="btn btn-success">读取书本信息xml</button>
    <button id="clearbookxml" class="btn btn-danger">清空书本列表</button>
    <br>
    <br>
    <table border="1" style="border-collapse: collapse;">
        <tr align="center">
            <th>编号</th>
            <th>书名</th>
            <th>作者</th>
        </tr>
        <tbody id="booktablexml"></tbody>
    </table>
	
	


	<!--  读取一个列表，通过each添加   -->
	<hr>
	<h2>读取一个列表，通过each添加</h2>
	<button id="readbooklist" class="btn btn-success">读取书本列表</button>
	<button id="clearbooklist" class="btn btn-danger">清空书本列表</button>
	<br>
	<br>
	<table border="1" style="border-collapse: collapse;">
		<tr align="center">
			<th>编号</th>
			<th>书名</th>
			<th>作者</th>
		</tr>
		<tbody id="booktablelist"></tbody>
	</table>

	<hr>
	<!--  读取一个input对象，不必放在表单中，通过id读取内容   -->
	<h2>读取一个input对象，不必放在表单中，通过id读取内容</h2>
	<input type="text" name="username" id="username" />
	<input id="loginbtn" class="btn btn-success" type="button" value="登录" />
	<div id="result">请输入123</div>
	<hr>
	
	
	
	<!--  content end   -->
	<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
	<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
	<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
	<script
		src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="bssets/js/json2.js"></script>
	<script>
		$(document).ready(function() {

			//读取一本书信息开始
			$("#readbookinfojson").click(function() {
				$.ajax("demo/ajaxjson", {
					dataType : "json",//预期服务器返回的数据类型
					type : "post",
					contentType : "application/json",
					data : JSON.stringify({
						id : 1,
						name : "世界简史",
						author : "赫伯特·乔治·威尔斯"
					}),
					async : true,
					success : function(data) {
						//console.log(data);
						$("#id").html(data.id);
						$("#name").html(data.name);
						$("#author").html(data.author);
					},
					// 请求出错时调用的函数
					error : function() {
						alert("数据发送失败");
					}
				});
			});
			//读取一本书信息结束			
	         //清除列表
            $("#clearbookxml").click(function() {
                $('#booktablexml').empty();
            });
			//xml begin
			$("#readbookinfoxml").click(function() {
				var xml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><book><id>1</id><name>Java编程思想</name><author>Bruce Eckel</author></book>";
				$.ajax("demo/ajaxxml", {
                    dataType : "text",//预期服务器返回的数据类型 xml->text
                    type : "post",
                    contentType : "application/xml",
                    data : xml,
                    async : true,
                    success : function(data) {
                        console.log(data);
                        var id=$("id",data).text();
                        var name=$("name",data).text();
                        var author=$("author",data).text();
                        var tr = $("<tr align='center'/>");
                        $("<td/>").html(id).appendTo(tr);
                        $("<td/>").html(name).appendTo(tr);
                        $("<td/>").html(author).appendTo(tr);
                        $("#booktablexml").append(tr);
                    },
                    // 请求出错时调用的函数
                    error : function() {
                        alert("数据发送失败");
                    }
                });
			});
			//xml end
			//清除列表
			$("#clearbooklist").click(function() {
                $('#booktablelist').empty();
            });
			//读取多本书信息开始
			$("#readbooklist").click(function() {
				$.ajax("demo/ajaxlist", {
					dataType : "json",//预期服务器返回的数据类型
					type : "get",
					contentType : "application/json",
					//data : JSON.stringify({id : 1,name : "Spring MVC企业应用实战",author : " "}),
					async : true,
					success : function(data) {
						console.log(data);
						$.each(data, function() {
							var tr = $("<tr align='center'/>");
							$("<td/>").html(this.id).appendTo(tr);
							$("<td/>").html(this.name).appendTo(tr);
							$("<td/>").html(this.author).appendTo(tr);
							$("#booktablelist").append(tr);
						});
					},
					// 请求出错时调用的函数
					error : function() {
						alert("数据发送失败");
					}
				});
			});
			//读取多本书信息结束  

			//loginbtn_begin
			$("#loginbtn").click(function() {
				$.ajax({
					data : "username=" + $("#username").val(),
					type : "GET",
					dataType : 'json',
					url : "demo/ajaxform",
					error : function(data) {
						alert("出错了！！:" + data.msg);
					},
					success : function(data) {
						//alert("success:"+data.msg);  
						$("#result").html(data.msg);
					}
				});
			});
			//loginbtn_end
		});
	</script>
</body>
</html>