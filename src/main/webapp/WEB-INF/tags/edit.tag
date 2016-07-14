<%-- edit page --%> 
<%@ tag description="Form to edit a wiki page" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<div class="container"> 
	<%-- title --%> 
	<t:simpleInput name="title" label="Title:" 
		helpText="Enter a short, concise phrase that identifies this point of interest (POI). The event title is visible to the public."/>

	<%-- date --%> 
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


	<%-- description --%> 
	<t:textArea name="description" label="Description:" 
		helpText="Explain why you selected this location for the event and whether this is an exact or general location."/>

	<%-- location --%> 

	<%-- location rationale --%>
	<t:textArea name="locationRationale" label="Location Rationale:" 
		helpText="Explain why you selected this location for the event and whether this is an exact or general location."/>

	<%-- victim identities --%> 

	<%-- aggressor identities --%> 

	<%-- action type --%> 

	<%-- outcomes --%> 

	<%-- primary sources --%> 

	<%-- secondary sources --%> 

	<t:textArea name="notes" label="Research Notes:" helpText="This is to document important considerations about the research you have done. This will not be public information, but it will be used by the internal research team and the admins."/>

</div> 