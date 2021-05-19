<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="${basePath}" />
<title>jstl常用标签</title>
</head>
<body>
<shiro:guest>shiro:guest</shiro:guest>
<shiro:user>shiro:user</shiro:user>
<shiro:hasRole name="admin">shiro:admin
</shiro:hasRole>
<c:forEach var="it" items="${list}" varStatus="i">

</c:forEach>


	<fmt:formatDate pattern="yyyy-MM-dd HHmmss" value="${it.submitDate}" />
	<fmt:formatDate value="${row.borrowtime}" type="both"
		pattern="MM月 dd HH:mm" />
	小数点后面 保留两位
	<fmt:formatNumber value="${it.price}" type="number" pattern="0.00"
		maxFractionDigits="2" />
	去掉小数点后面多余的0
	<fmt:formatNumber value="${row.price}" type="number"
		groupingUsed="false" maxFractionDigits="3" />
	java里面去掉小数点后面多余的0
	<%
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
		out.print("3.000=" + nf.format(3.000));
	%>
	限制字符显示长度： ${fn:substring(content, 0, 10)}
	<c:if test="${fn:length(content)>10}">…</c:if>
	字符的子串${fn:substring(string, begin, end)} 字符长度，数组长度 ${fn:length(item)}
	<c:if test="${fn:length(item)>10}">…</c:if>

	<c:forEach var="it" items="${list}" varStatus="i">
		<tr>
			<td>${i.count}</td>
			<td>${it}</td>
		</tr>
	</c:forEach>
	jstl中列表的嵌套
	<c:forEach var="it" items="${list}" varStatus="i">
		列表中嵌入的对象的属性${it.subject.subjTitle}
		先将嵌套的列表保存到临时列表
		<c:set value="${it.explist}" var="list2" />
		<c:forEach var="ite" items="${list2}" varStatus="i">
			${ite.expOrderId}----${ite.expIntro}
		</c:forEach>
	</c:forEach>


	<!-- 检测session并跳转 -->
	<c:if test="${sessionScope.gzh == null}">
		<c:redirect url="test.html">
			<c:param name="un">aaaa</c:param>
			<c:param name="upsw">aaaa</c:param>
		</c:redirect>
	</c:if>
	gzh=${sessionScope.gzh}
	<hr />
	<table>
<c:set var="arr" value="只是菜单栏,新闻,商城" />
<c:forEach var="item" items="${fn:split(arr,',')}" varStatus="i">
	<div class="radio3 radio-check radio-${tagpostfixlist[i.count%fn:length(tagpostfixlist)]} radio-inline">
		<input type="radio" id="category${i.count-1}" name="category" value="${i.count-1}" <c:if test="${0 == (i.count-1)}">checked</c:if>> <label for="category${i.count-1}">${item}</label>
	</div>
</c:forEach>

<c:set var="arr" value="5,10,20,40,80" />
<c:forEach var="it" items="${fn:split(arr,',')}" begin="0"
			end="${fn:length(fn:split(arr,','))}" step="2" varStatus="i">
	${it}
</c:forEach>

		<c:forTokens items="5,10,20,40,80" delims="," var="it">
			<c:choose>
				<c:when test="${it==pager.pagesize}">
					<option value="${it}" selected>${it}</option>
				</c:when>
				<c:when test="${it!=pagesize}">
					<option value="${it}">${it}</option>
				</c:when>
			</c:choose>
		</c:forTokens>

	</table>

	<c:set var="arr" value="党群部门,行政机关,学院,教辅,其他" />
	<select id="categoryId" name="categoryId">
		<c:forEach var="it" items="${fn:split(arr,',')}" varStatus="index">
			<c:choose>
				<c:when test="${it==item.category}">
					<option value="${index.count}" selected>${it}</option>
				</c:when>
				<c:when test="${it!=item.category}">
					<option value="${index.count}">${it}</option>
				</c:when>
			</c:choose>
		</c:forEach>
	</select>




	<label class="am-checkbox-inline"> <input type="radio"
		name="sat.sex" id="male" value="男" required
		<c:if test="${sat.sex=='男'}">checked</c:if> />&nbsp;&nbsp;男&nbsp;&nbsp;
	</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<label class="am-checkbox-inline"><input type="radio"
		name="sat.sex" id="female" value="女"
		<c:if test="${sat.sex=='女'}">checked</c:if> />&nbsp;&nbsp;女&nbsp;&nbsp;</label>

	<!-- choose标记，只选择一个 -->
	<c:choose>
		<c:when test="${1==1}">1==1</c:when>
		<c:when test="${1 eq 1}">1 eq 1</c:when>
		<c:otherwise></c:otherwise>
	</c:choose>
	<br />
	<c:if test="${1==1}">1==1</c:if>
	<br />
	<c:if test="${1!=2}">1!=2</c:if>
	<br />
	<c:if test="${1 eq 1}">1 eq 1</c:if>
	<br />
	<c:if test="${1 < 2}">1 &lt 2</c:if>
	<br />
	<c:if test="${1 lt 2}">1 < 2</c:if>
	<br />
	<c:if test="${1 le 2}">1 <= 2</c:if>
	<br />
	<c:if test="${2 le 2}">2 <= 2</c:if>
	<br />

	<br />
	<c:set value="张三" var="name1" scope="session"></c:set>
	<c:set var="name2" scope="session">李四</c:set>
	<ul>
		<li>从session中得到的值：${sessionScope.name1}</li>
		<li>从session中得到的值：${sessionScope.name2}</li>
	</ul>
	导入另一个页面
	<hr />
	<c:import url="http://www.baidu.com" />
	<hr />
	导入另一个页面结束
	<h3>Number Format:</h3>
	<c:set var="balance" value="120000.2309" />
	<p>
		Formatted Number (1):
		<fmt:formatNumber value="${balance}" type="currency" />
	</p>
	<p>
		Formatted Number (2):
		<fmt:formatNumber type="number" maxIntegerDigits="3"
			value="${balance}" />
	</p>
	<p>
		Formatted Number (3):
		<fmt:formatNumber type="number" maxFractionDigits="3"
			value="${balance}" />
	</p>
	<p>
		Formatted Number (4):
		<fmt:formatNumber type="number" groupingUsed="false"
			value="${balance}" />
	</p>
	<p>
		Formatted Number (5):
		<fmt:formatNumber type="percent" maxIntegerDigits="3"
			value="${balance}" />
	</p>
	<p>
		Formatted Number (6):
		<fmt:formatNumber type="percent" minFractionDigits="10"
			value="${balance}" />
	</p>
	<p>
		Formatted Number (7):
		<fmt:formatNumber type="percent" maxIntegerDigits="3"
			value="${balance}" />
	</p>
	<p>
		Formatted Number (8):
		<fmt:formatNumber type="number" pattern="###.###E0" value="${balance}" />
	</p>
	<p>
		Currency in USA :
		<fmt:setLocale value="en_US" />
		<fmt:formatNumber value="${balance}" type="currency" />
	</p>

	NUMBER FORMAT: Formatted Number (1): £120,000.23 Formatted Number (2):
	000.231 Formatted Number (3): 120,000.231 Formatted Number (4):
	120000.231 Formatted Number (5): 023% Formatted Number (6):
	12,000,023.0900000000% Formatted Number (7): 023% Formatted Number (8):
	120E3 Currency in USA : $120,000.23

	<fmt:formatDate value="<%=new Date()%>" type="both"
		pattern="EEEE, MMMM d, yyyy HH:mm:ss Z" />

	<fmt:formatDate value="<%=new Date()%>" type="date" dateStyle="full" />
	<!-- 2011年3月30日 星期三 -->

</body>
</html>
