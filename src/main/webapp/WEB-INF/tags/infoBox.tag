<%@ tag description="A table of data contained in a wiki page" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="thisPOI" required="true" type="org.mappingviolence.poi.POI" %>


<div class="infobox">
	<table class="infoboxtable"> 
		<tr class="mergedbottomrow"> 
			<td colspan="2" id="date">
				<h3><strong>${thisPOI.date.value}</strong></h3>
				<input type="hidden" class="hidden" value="${thisPOI.date.id}" /> 
			</td> 
		</tr> 
		<tr class="map"> 
			<td colspan="2" id="location"> 
				<div id="locationAddress"></div>
				<div id="map" style="width:100%;height:400px;"></div>
				<div id="lat">${thisPOI.location.value.getLatitude()}</div>
				<div id="lng">${thisPOI.location.value.getLongitude()}</div>
				<input type="hidden" class="hidden" value="${thisPOI.location.id}" /> 
			</td> 
		</tr> 
		<tr> 
			<td colspan="2" id="locationrationale">
				<p>${thisPOI.locationRationale.value}</p>
				<input type="hidden" class="hidden" value="${thisPOI.locationRationale.id}" />
			</td>
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
