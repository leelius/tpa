<%@page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div class="row margin-horizontal-xs">
	<div class="col-sm-8">
		${pager.total}条记录，第<span class="text-danger fontsize15em">${pager.current}</span>页/共${pager.pages}页，每页显示
			<select id="pagesizer">
				<c:forTokens items="5,10,20,40,80" delims="," var="it">
					<c:choose>
						<c:when test="${it==pager.size}">
							<option value="${it}" selected>
									${it}
							</option>
						</c:when>
						<c:when test="${it!=pager.size}">
							<option value="${it}">
									${it}
							</option>
						</c:when>
					</c:choose>
				</c:forTokens>
			</select>个，转到第<input type="text" id="pninput" size="2" title="输入页码，并回车">页（左右方向键翻页）
	</div>
	<c:set var="pagebegin" value="${pager.current - 2 < 1 ? 1:pager.current -2}" />
	<c:set var="pageend" value="${pager.current + 1 > pager.total ? pager.total :pager.current +1}" />
	<div class="col-sm-4 text-right">
		<ul class="margin-0 pagination">
			<li>
				<a title="前一页" href="${basePath}${pagename}/${qs}/${pager.pre}"> &laquo; </a>
			</li>
			<li>
				<a title="后一页" href="${basePath}${pagename}/${qs}/${pager.next}"> &raquo; </a>
			</li>
			<c:forEach var="pn" begin="${pagebegin}" end="${pageend}" varStatus="index">
				<c:if test="${pn <= pager.pages}">
					<li>
						<a href="${basePath}${pagename}/${qs}/${pn}">
								${pn}
						</a>
					</li>
				</c:if>
			</c:forEach>
		</ul>
	</div>
</div>

