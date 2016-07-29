<%@ tag description="Table of wiki pages" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="wikiList" required="true" type="java.util.List" %>


 
<table class="table"> 
	<caption><h2>${name}</h2></caption> 	
	<thead>
		<tr>
			<th>Title</th>
			<th>Creator</th>
		</tr>
	</thead>
	<tbody>
	<%-- 
	<c:choose>
		<c:when test="${fn:length(wikiList) gt 0}"> --%> 
			<c:forEach var="wikiPage" items="${wikiList}"> 
				<c:set var="wikiData" value="${wikiPage.getCurrentData()}" />
				<tr>
					<c:choose>
		          		<c:when test="${empty wikiData.title.value}">
		          			<td><a href="wikipage?id=${wikiPage.id}">Untitled</a></td>
		          		</c:when>
		          		<c:otherwise>
		          			<td><a href="wikipage?id=${wikiPage.id}">${wikiData.title.value}</a></td>
		          		</c:otherwise>
		          	</c:choose>
					<td>${wikiPage.getCurrentVersion().editor.email}</td>
				</tr>
			</c:forEach>
		<%--
		</c:when>
		<c:otherwise>
			<tr>
				<td colspan="2">No POIs in this section</td>
			</tr>
		</c:otherwise>
	</c:choose> --%>
	</tbody>
</table>
