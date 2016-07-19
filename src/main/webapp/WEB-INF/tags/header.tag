<%@ tag description="Table of wiki pages" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

  <div style="background: #ffffff;"> 
     <nav class="navbar navbar-default">
       <div class="container-fluid">
         <!-- Brand and toggle get grouped for better mobile display -->
         <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
            <span class="sr-only">Toggle navigation</span>
             <span class="icon-bar"></span>
             <span class="icon-bar"></span>
             <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="/dashboard">Mapping Violence Data Wiki</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav">
            <li class="active"><a href="/pool">Database<span class="sr-only">(current)</span></a></li>
            <li><a href="#">Admin Panel</a></li>
          </ul>
          
          <ul class="nav navbar-nav navbar-right">
            <li><a href="/dashboard">My Dashboard</a></li>
	          <li><a href="#">Sign Out</a></li>
          </ul>
        </div> <!-- /.navbar-collapse -->
      </div> <!-- /.container-fluid -->
    </nav>
  </div>
