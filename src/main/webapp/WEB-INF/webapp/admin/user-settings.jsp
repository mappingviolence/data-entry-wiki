<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
				<th>Change Permissions</th>
				<th>Remove Users</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="user" items="${users}">
				<tr>
					<td>${user.email}</td>
					<td><button type="submit" role="change-permission" data-id="${user.id}">Change Permissions</button></td>
					<td><button type="submit" role="remove-user" data-id="${user.id}">Remove User</button></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</form>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<script>
$(document).ready(function() {
	$("button[type='submit']").on("click", function() {
        var $this = $(this);
		switch ($this.attr("role")) {
        case "change-permission":
        	console.log("test");
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