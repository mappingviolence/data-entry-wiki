<%@ tag description="Table of wiki pages" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="wikiList" required="true" type="java.util.List" %>


<div class="container"> 
	<table class="table"> 
		<caption><h2>${name}</h2></caption> 	
		<thead> 
			<tr>
				<th>Title</th>
				<th>Creator</th>
			</tr>
		</thead>
		<tbody>
		<c:choose>
			<c:when test="${fn:length(wikiList) gt 0}">
				<c:forEach var="wikiPage" items="${wikiList}"> 
					<c:set var="wikiData" value="wikiPage.current.data" />
					<tr>
						<td>${wikiData[title]}</td>
						<td>${wikiData[creator]}</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td colspan="2">No POIs in this section</td>
				</tr>
			</c:otherwise>
		</c:choose>
		</tbody>
	</table>
</div> 