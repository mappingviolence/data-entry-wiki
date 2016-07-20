<%@ tag description="A list of citations" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="citations" required="true" type="java.util.Collection"%>

<h3>${title}</h3>
	<c:forEach var="citation" items="${citations}">
	    <p>${citation.value}</p>
	</c:forEach>