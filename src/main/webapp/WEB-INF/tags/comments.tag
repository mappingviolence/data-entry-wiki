<%-- comments --%> 
<%@ tag description="edit sources" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="comments" required="true" type="java.util.List"%> 


<div class="comments" id="${name}comments"> 
	<div class="form-group"> 
		
		<label for"hidden${name}commentlabel">comments</label>
		<!-- hidden --> 
		<div class="hidden"> 
			<input class="form-control" type="text" name="hidden${name}commentlabel">
		</div> 
		<!-- hidden comment -->
		<div class="hidden"> 
			<li> 
				<i>${comment.author.email}</i> 
				${comment.commentText}
			</li> 
		</div> 
		<!-- END hidden --> 
		<div class="commentswrapper">
			<c:forEach var="comment" items="${comments}">
				<li> 
					<i>${comment.author.email}</i> 
					${comment.commentText}
				</li> 
			</c:forEach>
		</div> 

		<div class="row">
			<div class="col-xs-10">
				<input class="form-control" type="text" name="add${name}comment" placeholder="add a new comment">
			</div> 
			<div class="col-xs-2"> 
				<button id="add${name}comment" type="submit" class="btn btn-default btn-lg">
					<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
				</button> 
			</div> 
		</div> 
	</div> 
</div> 