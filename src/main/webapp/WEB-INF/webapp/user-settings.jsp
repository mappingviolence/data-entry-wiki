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
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
    
    <!-- Bootstrap Core JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    
    <script src="static/script/loading.js"></script>

	<script>
		$(document).ready(function() {
			$("button[type='submit']").on("click", function(e) {
				e.preventDefault();
				var $body = $("body");
				debugger;
				startLoading($body);
        		var $this = $(this);
				switch ($this.attr("data-action")) {
				case "add-user":
					console.log("in add-user");
					console.log(startLoading);
					addUser(function() {
						endLoading($("#loading"));
					});
					break;
		        case "change-permission":
		        	changePermissions($this, function() {
		        		endLoading($("#loading"));
		        	});
		        	break;
		        case "remove-user":
		        	removeUser($this, function() {
		        		endLoading($("#loading"))
		        	});
		        	break;
		        default:
		           console.log("click doesn't correspond with any valid action");
		        }
				return false;
			});
		});
		
		var addUser = function(callback) {
			var $form = $("form[role='add-user']");
			var email = $form.find("input[name='email']").val()
			var role = $form.find("select[name='role']").val()
			$.ajax(
				{
					method: "POST",
					data: {email : email,
						   role : role}
				}
			).done(function(data, textStatus, httpResponse) {
					alert(data.data);
					if (callback) {
						callback();
					}
					document.location.reload();
				}
			).fail(function(httpResponse, errText, errThrown) {
					if (callback) {
						callback();
					}
					alert(httpResponse.data);
				}
			);
		}
		
		var changePermissions = function($this, callback) {
			var $form = $("form[role='modify-user']");
			var id = $this.attr("data-id");
			var role = $this.prev().val();
			console.log(id, role);
			$.ajax(
				{
					method: "PUT",
					data: {id : id,
						   role : role}
				}
			).done(function(data, textStatus, httpResponse) {
					alert(data.data);
					if (callback) {
						callback();
					}
					document.location.reload();
				}
			).fail(function(httpResponse, errText, errThrown) {
					if (callback) {
						callback();
					}
					alert(errText, httpResponse.data);
				}
			);
		}
		
		var removeUser = function($this, callback) {
			var id = $this.attr("data-id");
			$.ajax(
	            {
	            	method: "DELETE",
	    			data: {id : id}
	            }
			).done(function(data, textStatus, httpResponse) {
					console.log(data, textStatus, httpResponse);
					if (callback) {
						callback();
					}
	            	alert(data.data);
	            	document.location.reload();
				}
			).fail(function(httpResponse, errText, errThrown) {
					if (callback) {
						callback();
					}
					alert(httpResponse.data);
				}
			);
		}
</script>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body>

<t:header page="admin"/> 

<div class="container"> 
	<form class="form-group" role="modify-user">
		<table class="table">
            <caption><h2>The Current Team</h2></caption> 
			<thead>
				<tr>
					<th>User Name</th>
					<th>User email</th>
					<th>Current Role</th>
					<c:if test="${not empty currentUser and not empty user and cf:compareUsers(currentUser, user) and currentUser.isAdmin()}">
						<th>Change Permissions</th>
						<th>Remove Users</th>
					</c:if>
				</tr>
			</thead>
			<tbody>
				<c:catch var ="catchException">
				<c:forEach var="user" items="${users}">
					<tr>
						<td>${user}</td>
						<td>${user.email}</td>
						<td>${user.role}</td>
						<c:if test="${not empty currentUser and not empty user and cf:compareUsers(currentUser, user) and currentUser.isAdmin()}">
							<td>
								<select class="btn btn-default btn-lg dropdown-toggle" name="changeRole">
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
								<button type="submit" class="btn btn-default btn-lg" data-action="change-permission" data-id="${user.id}">Change Permissions</button>
							</td>
							<td><button type="submit" class="btn btn-default btn-lg" data-action="remove-user" data-id="${user.id}">
	                            <span class="glyphicon glyphicon-remove"></span>
	                        </button></td>
						</c:if>
					</tr>
				</c:forEach>
				</c:catch>
			</tbody>
		</table>
	</form>
	<c:if test="${not empty currentUser and not empty user and cf:compareUsers(currentUser, user) and currentUser.isAdmin()}">
		<form class="form-group" role="add-user">
			<div class="row">
				<div class="col-md-4 col-sm-8 col-xs-12">
					<input class="form-control" type="text" name="email" />
				</div>
			</div>
			<div class="row">
				<div style="float:left">
					<select class="btn btn-default btn-lg dropdown-toggle" name="role">
						<option class="dropdown-item" value="VIEWER">Viewer</option>
						<option class="dropdown-item" value="COMMENTOR">Commentor</option>
						<option class="dropdown-item" value="EDITOR">Editor</option>
						<c:if test="${currentUser.isAdmin()}">
							<option value="ADMIN">Admin</option>
						</c:if>
						<c:if test="${currentUser.isPublisher()}">
							<option value="PUBLISHER">Publisher</option>
						</c:if>
					</select>
				</div>
				<div>
					<button type="submit" class="btn btn-default btn-lg" data-action="add-user">Add User</button>
				</div>
			</div>
		</form>
	</c:if>
</div>
<c:if test = "${catchException != null}">
   <p>The exception is : ${catchException} <br />
   There is an exception: ${catchException.message}</p>
</c:if>
</body>
</html>