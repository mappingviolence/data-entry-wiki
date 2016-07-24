<%@ tag description="A table of data contained in a wiki page" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="thisPOI" required="true" type="org.mappingviolence.poi.POI" %>


<div class="infobox">
	<table> 
		<tr class="map"> 
			<div id="locationAddress"></div>
			<div id="map" style="width:300px;height:200px;"></div>
			<div id="lat">${thisPOI.location.value.getLatitude()}</div>
			<div id="lng">${thisPOI.location.value.getLongitude()}</div>
		</tr> 
		<tr> 
			<td><p id="locationrationale">${thisPOI.locationRationale.value}</p></td>
		<tr> 
			<td> <h3 id="date">${thisPOI.date.value}</h3> </td> 
		</tr> 
		<t:listOutData title="Victims" peopleList="${thisPOI.victims}"/> 
		<t:listOutData title="Aggressors" peopleList="${thisPOI.aggressors}"/> 
		<%-- <tr> 
			<td>Type of Action</td> 
			<td>${thisPOI.actionType}</td> 
		</tr> 
		<tr> 
			<td>Outcome</td> 
			<td>${thisPOI.outcome}</td> 
		</tr> --%>
	</table> 
</div> 
