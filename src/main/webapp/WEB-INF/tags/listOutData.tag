<%@ tag description="A table of data contained in a wiki page" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="peopleList" required="true" type="java.util.List" %>


<tr class="mergedtoprow"> 
	<td><h3>${title}</h3></td> 
</tr> 
<c:forEach var="person" items="${peopleList}"> 
	<%-- what if there are no names? --%>
	<tr class="mergedrow"> 
		<td>${person.name}</td> 
	</tr> 
	<c:forEach var="identity" items="${person.identities}">
		<tr class="mergedrow"> 
			<td>${identity.title}</td> 
			<td>${identity.body}</td> 
		</tr> 
	</c:forEach>
</c:forEach> 
<tr class="mergedbottomrow"></tr>
