<%-- comments --%> 
<%@ tag description="edit sources" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="comments" type="java.util.List"%> 
<%@ attribute name="id" required="true"%> 

<div class="comments" id="${name}comments"> 
	<div class="form-group"> 
		
		<label for="hidden${name}commentlabel">comments</label>
		<!-- hidden --> 
		<div class="hidden"> 
			<input class="form-control" type="text" name="hidden${name}commentlabel">
		</div> 

		<!-- END hidden --> 
		<div class="commentswrapper">
			<c:forEach var="comment" items="${comments}">
				<div class="row comment">
					<div class="col-xs-10">
						<i class="commentName">${comment.author.givenName} ${comment.author.familyName}:</i> 
						<span class="commentText">${comment.commentText}</span>
						<input type="hidden" class="hidden commentId" value="${comment.id}" />
						<input type="hidden" class="hidden userId" value="${comment.author.id}" />
						<input type="hidden" class="hidden userEmail" value="${comment.author.email}" />
						<input type="hidden" class="hidden userRole" value="${comment.author.role}" />
					</div> 
					<div class="col-xs-2"> 
						<button id="delete${name}comment" data-id="${id}" data-commentId="${comment.id}" type="submit" class="btn btn-default btn-sm deletecomment"> 
							<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
						</button>
					</div> 
				</div> 
			</c:forEach>
			<%--<c:choose> 
				<c:when test="not empty ${comments}"> 
					<c:forEach var="comment" items="${comments}">
						<li> 
							<i>${comment.author.givenName} ${comment.author.familyName}</i> 
							${comment.commentText}
						</li> 
					</c:forEach>
				</c:when> 
				<c:otherwise> 
					<p> OTHERWISE </p>
				</c:otherwise> 
			</c:choose>  --%>
		</div> 

		<div class="row">
			<div class="col-xs-10">
				<textarea class="form-control expanding"
				 name="add${name}comment" placeholder="add a new comment" rows="1"></textarea>
			</div> 
			<div class="col-xs-2"> 
				<button id="add${name}comment" data-id="${id}" data-name="${name}" type="submit" class="btn btn-default btn-lg addcomment">
					<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
				</button> 
			</div> 
		</div> 
	</div> 
</div> 