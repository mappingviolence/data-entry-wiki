<%-- edit page --%> 
<%@ tag description="Form to edit a wiki page" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="thisPOI" required="true" type="org.mappingviolence.poi.POI" %>


<form> 
	<%-- title --%> 
	<div class="row">
		<div class="col-sm-8 col-sm-offset-0 col-md-8 col-md-offset-1">
			<t:simpleInput name="title" label="Title:"
				helpText="Enter a short, concise phrase that identifies this point of interest (POI). The event title is visible to the public."/>
		</div>
		<div class="col-sm-4 col-md-3">
			<t:comments name="title" comments="${thisPOI.title.comments}" />
		</div>
	</div>

	<%-- date --%> 
	<div class="row">
		<div class="col-sm-8 col-sm-offset-0 col-md-8 col-md-offset-1">
			<t:simpleInput name="date" label="Date:" >
				<jsp:attribute name="helpText">
		            Enter the date the event happened using the following options without brackets: [Year], [Modifier1 Year], [Year Month], [Year Modifier1 Month], [Year Month Date], or [Year Month Modifier2 Date].<br> 
					<br> 
					Examples: "2001", "Late 2001", "2001 October", "2001 Mid October", "2001 October 27", "2001 October Around 27".<br>
					<br>
					- Year must be an integer between 1850 and 9999 (inclusive).<br>
					- Month is the non-abbreviated spelling of one of the twelve months (e.g. January).<br>
					- Date is an integer between 1 and 31 (or 30).<br>
					- Modifier1 is either: "Early", "Mid", "Late" (without quotes).<br>
					- Modifier2 is "Around" (without quotes).
		      	</jsp:attribute>
		    </t:simpleInput>
		</div>
		<div class="col-sm-4 col-md-3">
			<t:comments name="date" comments="${thisPOI.date.comments}" />
		</div>
	</div>


	<%-- description --%> 
	<div class="row">
		<div class="col-sm-8 col-sm-offset-0 col-md-8 col-md-offset-1">
			<t:textArea name="description" label="Description:" 
				helpText="Explain why you selected this location for the event and whether this is an exact or general location."/>
		</div>
		<div class="col-sm-4 col-md-3">
			<t:comments name="description" comments="${thisPOI.description.comments}" />
		</div>
	</div>

	<%-- location --%> 
	<div class="row">
		<div class="col-sm-8 col-sm-offset-0 col-md-8 col-md-offset-1">
			<div class="row">
				<div class="col-xs-12">
					<input id="locationInput" class="form-control" />
					<div id="mapEdit" style="width:100%;height:380px;"></div>
				</div>
				<div class="col-xs-6">
					<t:simpleInput htmlId="latEdit" name="lat" label="Latitude:"/>
				</div> 
				<div class="col-xs-6">
					<t:simpleInput htmlId="lngEdit" name="lng" label="Longitude:"/>
				</div>
			</div>
		</div>
		<div class="col-sm-4 col-md-3">
			<t:comments name="location" comments="${thisPOI.location.comments}" />
		</div>
	</div>
	
	<%-- location rationale --%>
	<div class="row">
		<div class="col-sm-8 col-sm-offset-0 col-md-8 col-md-offset-1">
			<t:textArea name="locationrationale" label="Location Rationale:" 
				helpText="Explain why you selected this location for the event and whether this is an exact or general location."/>
		</div>
		<div class="col-sm-4 col-md-3">
			<t:comments name="locationrationale" comments="${thisPOI.locationRationale.comments}" />
		</div>
	</div>

	<%-- victim identities --%> 

	<t:person name="victim" label="Victim" />


	<%-- aggressor identities --%> 
	<t:person name="aggressor" label="Aggressor"/>

	<%-- tags --%>
	<t:source name="tag" label="Tags:" helpText="Tag your POI with commonly-searched phrases. Soon, we'll work together to create a standard list of good tags."/> 

	<%-- Shouldn't these be tags --%>
		<%-- action type --%> 
	
		<%-- outcomes --%> 
	<%-- END --%>

	<%-- primary sources --%> 
	<t:source name="primarysource" label="Primary Sources:" helpText=""/>

	<%-- secondary sources --%> 
	<t:source name="secondarysource" label="Secondary Sources:" helpText=""/>

	<%-- research notes --%>
	<t:textArea name="researchnotes" label="Research Notes:" helpText="This is to document important considerations about the research you have done. This will not be public information, but it will be used by the internal research team and the admins."/>

	<button id="save" type="submit" class="btn btn-primary btn-lg">Save</button> 

</form> 
