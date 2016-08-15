<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tags/customTagLibrary" prefix="cf" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<html>
<head>
 <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Mapping Violence</title>

    <!-- Bootstrap Core CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" type="text/css">

    <!-- Custom Fonts -->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>
    <link href="https://fonts.googleapis.com/css?family=Crimson+Text:400i,400|Montserrat:400,700" rel="stylesheet">

    <!-- Plugin CSS -->
    <link rel="stylesheet" href="static/css/animate.min.css" type="text/css">

    <!-- Bootstrap Material Design -->
    <link rel="stylesheet" type="text/css" href="static/css/bootstrap-material-design.css">
    <link rel="stylesheet" type="text/css" href="static/css/ripples.css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="static/css/corrections.css" type="text/css">
    <link rel="stylesheet" href="static/css/mappingviolence.css" type="text/css">
    
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.1.0.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    
    <script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB3rwW9biPQiijfhhR9YZagdNrf_f3duvM&callback=initMap"></script>

    <jsp:include page="/WEB-INF/tags/mapjs.jspf"/>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body>

<t:header page="" /> 

<div class="container"> 
	<div class="form-group">
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
									<button class="btn btn-default btn-lg" type="submit" role="change-permission" data-id="${user.id}">Change Permissions</button>
								</c:if>
							</td>
							<td><button class="btn btn-default btn-lg" type="submit" role="remove-user" data-id="${user.id}">Remove User</button></td>
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
				<button class="btn btn-default btn-lg" type="submit" data-role="add-user">Add User</button>
			</div>
		</form>
	</div>
</div>
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