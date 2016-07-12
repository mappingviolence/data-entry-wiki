<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tags/customTagLibrary" prefix="cf" %>

<html>
<head>
<title>Update Users</title>
</head>
<body>
<form>
	<table>
		<thead>
			<tr><th>Current Users</th></tr>
			<tr>
				<th>User email</th>
				<th>Current Role</th>
				<th>Change Permissions</th>
				<th>Remove Users</th>
			</tr>
		</thead>
		<tbody>
			<c:catch var ="catchException">
			<c:forEach var="user" items="${users}">
				<tr>
					<td>${user.email}</td>
					<td>${user.role}</td>
					<td>
						<c:if test="${not empty currentUser and not empty user and cf:compareUsers(currentUser, user) and currentUser.isAdmin()}">
							<select name="changeRole">
								<option value="VIEWER">Viewer</option>
								<option value="COMMENTOR">Commentor</option>
								<option value="EDITOR">Editor</option>
								<c:if test="${currentUser.isAdmin()}">
									<option value="ADMIN">Admin</option>
								</c:if>
								<c:if test="${currentUser.isPublisher()}">
									<option value="PUBLISHER">Publisher</option>
								</c:if>
							</select>
							<button type="submit" role="change-permission" data-id="${user.id}">Change Permissions</button>
						</c:if>
					</td>
					<td><button type="submit" role="remove-user" data-id="${user.id}">Remove User</button></td>
				</tr>
			</c:forEach>
			</c:catch>
		</tbody>
	</table>
</form>
<form role="">
	<div>
		<input type="text" name="email" />
		<select name="role">
			<option value="VIEWER">Viewer</option>
			<option value="COMMENTOR">Commentor</option>
			<option value="EDITOR">Editor</option>
			<c:if test="${currentUser.isAdmin()}">
				<option value="ADMIN">Admin</option>
			</c:if>
			<c:if test="${currentUser.isPublisher()}">
				<option value="PUBLISHER">Publisher</option>
			</c:if>
		</select>
		<button type="submit" data-role="add-user">Add User</button>
	</div>
</form>
<c:if test = "${catchException != null}">
   <p>The exception is : ${catchException} <br />
   There is an exception: ${catchException.message}</p>
</c:if>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<script>
$(document).ready(function() {
	$("button[type='submit']").on("click", function() {
        var $this = $(this);
		switch ($this.attr("role")) {
		case "add-user":
			$.ajax(
            {
            	method: "POST",
    			data: {email : $("input[name='email']").val(),
    				   role : $("input[name='role']").val()}
            })
            .done(function(data, textStatus, httpResponse) {
            	var responseObject = JSON.parse(data);
            	alert(responseObject.success.msg);
            	document.location.reload();
            })
            .fail(function(httpReponse, errText, errThrown) {
            	var responseObject = JSON.parse(errText);
            	alert(responseObject.err.msg);
            });
			break;
        case "change-permission":
        	$.ajax(
            {
            	method: "PUT",
    			data: {id : $this.attr("data-id"),
    				   role : $this.attr("data-role")}
            })
            .done(function(data, textStatus, httpResponse) {
            	var responseObject = JSON.parse(data);
            	alert(responseObject.success.msg);
            	document.location.reload();
            })
            .fail(function(httpReponse, errText, errThrown) {
            	var responseObject = JSON.parse(errText);
            	alert(responseObject.err.msg);
            });
        	break;
        case "remove-user":
        	$.ajax(
            {
            	method: "DELETE",
    			data: {id : $this.attr("data-id")}
            })
            .done(function(data, textStatus, httpResponse) {
            	var responseObject = JSON.parse(data);
            	alert(responseObject.success.msg);
            	document.location.reload();
            })
            .fail(function(httpReponse, errText, errThrown) {
            	var responseObject = JSON.parse(errText);
            	alert(responseObject.err.msg);
            });
        	break;
        default:
           console.log("no");
        }
		return false;
	});
});
</script>
</body>
</html>