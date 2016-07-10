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
					<td><button type="submit" role="change_permission" data-id="${user.id}">Change Permissions</button></td>
			</c:forEach>
		</tbody>
	</table>
</form>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		$("button[type='submit']");
	});
</script>
</body>
</html>