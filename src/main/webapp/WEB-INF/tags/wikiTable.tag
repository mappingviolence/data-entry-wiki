<%@ tag description="Table of wiki pages" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="wikiList" required="true" %>


<table> 
	<caption> ${name} </caption> 	
	<tr> 
		<td> <b> Title </b> </td> 
		<td> <b> Creator </b> </td> 
	</tr> 
	<c:forEach var=”wiki” items=”${wikiList}”> 
		<tr>
			<td> ${wiki.title} </td>
			<td> ${wiki.creator} </td> 
		</tr>
	</forEach>
</table> 