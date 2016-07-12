<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Creative - Start Bootstrap Theme</title>

    <!-- Bootstrap Core CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">

    <!-- Custom Fonts -->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="font-awesome/css/font-awesome.min.css" type="text/css">

    <!-- Plugin CSS -->
    <link rel="stylesheet" href="css/animate.min.css" type="text/css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="css/creative.css" type="text/css">
    <link rel="stylesheet" href="css/corrections.css" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body id="page-top">
    <t:header /> 

    <div class="container" id="header">     
      <div class="data-element"> 
        <div id="title"> 
         <h1> ${thisPOI.title} </h1>
         </div> 
        <button type="button" class="btn btn-default btn-lg">
          <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Edit
        </button>  
      </div> 
    </div> 

    <div class="container" id="main">
      <div class="row">
       <div class="col-xs-6">
        <div class="data-element"> 
          <p>${thisPOI.description}</p>
        </div>
        <div class="data-element"> 
          <h2> Citations </h2>
      <%--    <c:forEach var="citation" items="${thisPOI.citations}">
            <p> ${citation} </p>
          </c:forEach>  --%>
        </div> 
        <div class="data-element"> 
          <h2> Research Notes </h2> 
          <p> ${thisPOI.notes} </p>
        </div>

      </div>
      <t:infoBox thisPOI="${thisPOI}"/>
    </div>
  </div>

    <script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB3rwW9biPQiijfhhR9YZagdNrf_f3duvM&callback=initMap">
    </script>

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="js/jquery.easing.min.js"></script>
    <script src="js/jquery.fittext.js"></script>
    <script src="js/wow.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="js/creative.js"></script>

</body>

</html>