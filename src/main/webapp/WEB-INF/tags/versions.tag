<%-- previous versions --%>
<%@ tag description="Table of previous versions" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="versions" required="true" type="java.util.List" %>
<%@ attribute name="poiId" required="true" %>

<table class="table"> 
	<caption><h2>Versions:</h2></caption> 	
	<thead>
		<tr>
			<th>Title</th>
			<th>Editor</th>
			<th>Date Modified</th> 
		</tr>
	</thead>
	<tbody>
	<%-- 
	<c:choose>
		<c:when test="${fn:length(wikiList) gt 0}"> --%> 
			<c:forEach var="version" items="${versions}" varStatus="num"> 
				<tr>
					<c:choose>
		          		<c:when test="${empty version.data.title.value}">
		          			<td><a href="wikipage?id=${poiId}&versionId=${version.id}">Untitled</a></td>
		          		</c:when>
		          		<c:otherwise>
		          			<td><a href="wikipage?id=${poiId}&versionId=${version.id}">${version.data.title.value}</a></td>
		          		</c:otherwise>
		          	</c:choose>
					<td>${version.editor.email}</td>
					<td>${version.dateModified}</td> 
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