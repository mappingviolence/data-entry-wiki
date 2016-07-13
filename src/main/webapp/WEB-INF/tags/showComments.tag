<%@ tag description="comments tag for use in editPage" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="comments" required="true" type="java.util.List" %>

<div> 
	<ul> 
		<c:forEach var="comment" items="${comments}">
			<li> <b>${comment.author}</b> ${comment.commentText} </li> 
		</c:forEach> 
	</ul>
</div> 


