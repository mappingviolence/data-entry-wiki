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
		<c:forEach var="wikiPage" items="${wikiList}"> 
			<c:set var="wikiData" value="wikiPage.current.data"
			<tr>
				<td>${wikiData[title]}</td>
				<td>${wikiData[creator]}</td> 
			</tr>
		</c:forEach>
	</table> 
</div> 