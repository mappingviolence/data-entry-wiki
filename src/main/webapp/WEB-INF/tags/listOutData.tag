<%@ tag description="A table of data contained in a wiki page" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="peopleList" required="true" type="java.util.Collection" %>


<tr class="mergedtoprow"> 
	<td><h3><strong>${title}</strong></h3></td> 
</tr> 
<c:forEach var="person" items="${peopleList}"> 
	<%-- what if there are no names? --%>
	<%-- <tr class="mergedrow"> 
		<td>${person.name}</td> 
	</tr>  --%>

	<!-- name on top --> 
	<tr class="mergedrow"> 
	<c:forEach var="identity" items="${person.identities}">
		<c:choose> 
			<c:when test="${identity.category eq 'Name'}">
				<td colspan="2"> <i> ${identity.value} </i> </td> 
	        </c:when>
	        <c:otherwise> 
	   		</c:otherwise> 
	    </c:choose> 
	</c:forEach>
	</tr> 

	<!-- name on top --> 
	<c:forEach var="identity" items="${person.identities}">
		<tr class="mergedrow">
			<c:choose> 
				<c:when test="${identity.category ne 'Name'}"> 
					<td>${identity.category}</td> 
					<td>${identity.value}</td>
					<td><input type="hidden" class="hidden" value="${identity.id}"/></td>
	      	 	</c:when>
		        <c:otherwise> 
		   		</c:otherwise> 
	    	</c:choose> 
		</tr> 
	</c:forEach>
</c:forEach> 
<tr class="mergedbottomrow"></tr>
