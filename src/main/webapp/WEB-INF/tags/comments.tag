<%-- comments --%> 
<%@ tag description="edit sources" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="comments" required="true" %> 


<div class="comments" id="${name}comments"> 
	<c:forEach var="comment" items="comments">
		<li> 
			<i>${comment.author.email}</i> 
			${comment.commentText}
		</li> 
	</c:forEach>
	<div class="form-group"> 
		<label for="add${name}comment" class="control-label col-sm-2" > 
		<div class="col-sm-10">
			<input class="form-control" type="text" name="add${name}comment">
		</div> 
	</div> 


</div> 