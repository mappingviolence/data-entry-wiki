<%@ tag description="Table of wiki pages" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="wikiList" required="true" type="java.util.List" %>


<div class="container"> 
	<table class="table"> 
		<caption>${name}</caption> 	
		<tr> 
			<td><b>Title</b></td> 
			<td><b>Creator</b></td> 
		</tr>
		<c:forEach var="wiki" items="${wikiList}"> 
			<tr>
				<%-- This supposes that a wiki page is sent, but then the 
				title attribute assumes that it is a POI --%>
				<td>${wiki.current.data.title}</td>
				<td>${wiki.creator}</td> 
			</tr>
		</c:forEach>
	</table>
</div> 