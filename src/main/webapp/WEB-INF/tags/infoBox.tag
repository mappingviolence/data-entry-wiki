<%@ tag description="A table of data contained in a wiki page" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="thisPOI" required="true" type="java.util.List" %>


<div class="col-xs-4">
	<table class=”infobox” > 
		<tr class=”map”> 
			<td> ### This is where the map goes ### </td>
		</tr> 
		<tr> 
			<td> <h3>${thisPOI.date}</h3> </td> 
		</tr> 
		<t:listOutData title=”Victims” peopleList=”${thisPOI.victims}”> 
		<t:listOutData title=”Aggressors” peopleList=”${thisPOI.aggressors}”> 
		<tr> 
			<td>Type of Action</td> 
			<td>${thisPOI.actionType}</td> 
		</tr> 
		<tr> 
			<td>Outcome</td> 
			<td>${thisPOI.outcome}</td> 
		</tr> 
	</table> 
</div> 
