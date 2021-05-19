<%@page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!--container end-->
<footer>
	<div class="row">
		<div class="col-xs-12 text-center">2017-2021.${appName} 版权所有 All rights reserved.</div>
	</div>
	<div class="row">
		<div class="col-xs-12 text-center"></div>
	</div>
</footer>
<%-- 如果断网测试，请使用本地资源 --%>
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script	src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<%-- 
<script src="bssets/js/jquery.min.1.12.4.js"></script>
<script	src="bssets/js/bootstrap.min.js"></script>
 --%> 

<!-- Metis Menu Js -->
<script src="bssets/js/jquery.metisMenu.js"></script>
<!-- sidenav Js -->
<script src="bssets/js/sidenav.js"></script>
<!-- restrictnumber Js -->
<script src="bssets/js/restrictnumber.js"></script>
<!-- jebox Js -->
<script src="bssets/jebox/jquery.jebox.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#bindingwechat").click(function() {
			//alert("bindingwechat");
			jeBox({
				title : "绑定微信请扫描",
				type : 'iframe',
				padding : "0",
				boxSize : [ "400px", "61%" ],
				content : "${basePath}admin/bindwechat",
				maskLock : true,
				closefun: function(){window.location.reload()}
			});
			return false;
		});
		$("#unbindingwechat").click(function() {
			var r=confirm("确定要解除与微信的绑定吗？");
			if (r==true) {
				jeBox({
					title : "解除绑定微信",
					type : 'iframe',
					padding : "0",
					boxSize : [ "400px", "61%" ],
					content : "${basePath}admin/bindwechatundo",
					maskLock : true,
					closefun: function(){window.location.reload()}
				});
			}
			return false;
		});
		//记录侧边栏
		$("#sideNav").click(function() {
			//alert("sidenav");
			//console.log('点击了折叠侧边栏按钮 大屏');
			if ($(this).hasClass('closed')) {
				$.post("recordsidenav?name=close");
			} else {
				$.post("recordsidenav?name=open");
			}
		});
		$("#sideNavBtn").click(function() {
			//alert("sidenav");
			//console.log('点击了折叠侧边栏按钮 小屏');
			if ($(this).hasClass('closed')) {
				$.post("recordsidenav?name=close");
			} else {
				$.post("recordsidenav?name=open");
			}
		});
		$(window).bind("load resize", function() {
			//console.log('width:'+$(this).width());
			if ($(this).width() < 768) {
				//console.log('sidenav:'+'${sessionScope.sidenav}');                
				//$('div.sidebar-collapse').addClass('collapse');
				$('.navbar-side').css({
					left : '0px'
				});/* 侧边栏宽度 */
				$('#page-wrapper').css({
					'margin-left' : '0px'
				});
				//$("#sideNav").addClass('closed');
			} else {
				$('div.sidebar-collapse').removeClass('collapse');
				//设置侧边栏
				//console.log('sidenav:'+'${sessionScope.sidenav}');
				if ('${sessionScope.sidenav}' == 'close') {

					$('.navbar-side').css({
						left : '-260px'
					});/* 侧边栏宽度 */
					$('#page-wrapper').css({
						'margin-left' : '0px'
					});
					$("#sideNav").addClass('closed');
				} else {

					$('.navbar-side').css({
						left : '0px'
					});
					$('#page-wrapper').css({
						'margin-left' : '260px'
					});/* 侧边栏宽度 */
					$("#sideNav").removeClass('closed');
				}
			}
		});
	});
</script>
<c:if test="${operate == null}">
	<script type="text/javascript">
		//编辑按钮
		$(".edititem").click(
				function() {

					$(location).attr(
							'href',
							'${basePath}${pagename}?operate=edit&id='
									+ $(this).val());
				});
		$(".deleteitem").click(
				function() {
					if (confirm('确定要删除选择的信息吗？')) {
						$(location).attr(
								'href',
								'${basePath}${pagename}?operate=delete&id='
										+ $(this).val());
					}
				});
		$(".moveup").click(
				function() {
					
						$(location).attr(
								'href',
								'${basePath}${pagename}?operate=moveup&id='
										+ $(this).val());
					
				});
		$(".movedown").click(
				function() {
					
						$(location).attr(
								'href',
								'${basePath}${pagename}?operate=movedown&id='
										+ $(this).val());
					
				});
		//-----------------------pager start-----------------------
		$("#pagesizer").change(
				function() {
					//alert($("#pagesizer").val());
					$(location).attr(
							'href',
							'${basePath}${pagename}?${qs}ps='
									+ $("#pagesizer").val());
				});
		$('#pninput').keypress(
				function(event) {
					var keynum = (event.keyCode ? event.keyCode : event.which);
					if ($('#pninput').val() != '') {
						if (keynum == '13') {
							//alert('pn='+$('#pninput').val());
							$(location).attr(
									'href',
									'${basePath}${pagename}?${qs}pn='
											+ $("#pninput").val());
						}
					}
				});
		$('#pninput').keydown(
				function(event) {
					var keynum = event.which; //获取键值          
					if (keynum == '37') {
						$(location).attr('href',
								'${basePath}${pagename}?${qs}pn=${pager.pre}');
					}
					if (keynum == '39') {
						//alert('pn='+$('#pninput').val());
						$(location).attr('href',
								'${basePath}${pagename}?${qs}pn=${pager.next}');
					}
				});
		//焦点放在页码输入框
		$("#pninput").focus();
		$(".canclesearch").click(function() {
			$(location).attr('href', '${basePath}${pagename}?clrkw=true');
		});		
		//-----------------------pager end-----------------------
	</script>
</c:if>
<c:if test="${operate != null}">
	<!-- 动态提交表单，上传图片 -->
	<script src="bssets/js/jquery.form.js"></script>
	<script src="bssets/js/select2.full.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//更新select元素外观
			$(".selectbox").select2();
		});
	</script>
</c:if>


