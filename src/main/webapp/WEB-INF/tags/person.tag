<%-- simple input form element --%> 
<%@ tag description="A simple input" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="label" required="true" %>
<%@ attribute name="helpText" required="false" %>


<div class="form-group">
    <div class="row">
        <div class="col-xs-12"> 
            <label for="${name}hidden"> ${label}s 
                <span id="${name}Help" role="helpBtn" class="glyphicon glyphicon-info-sign"></span>
            </label>
            <input id="${name}hidden" class="hidden" type="hidden" class="form-control" name="${name}Identity">
        </div> 
        <!-- person --> 
        <div class="hidden">
        	<div class="${name}IdentitesContainer"></div>
            <div data-id="${name}"> 
                <div class="text-center col-xs-12">
                    <button type="button" class="${name}identityBtn btn btn-default add_field_button">Add ${label} Identity</button>      
                </div>
                <a class="removebutton" href="#">remove ${name}</a>
            	<hr>  
            </div>
        </div> <!-- person -->
        <!-- identity --> 
        <div class="hidden">
            <div class="col-xs-12">
	            <div data-id="${name}identity">
	                <div class="row">
	                    <div class="col-xs-6">
	                        <div class="form-group">
	                            <label for="${name}Category">${label} Identity Category</label>
	                            <select class="btn btn-default btn-lg dropdown-toggle" id="${name}Category" name="${name}Category" class="form-control">
	                                <option value="Race">Race</option>
	                                <option value="Nationality">Nationality</option>
	                                <option value="Occupation">Occupation</option>
	                                <option value="Ethnicity">Ethnicity</option>
	                                <option value="Gender">Gender</option>
	                                <option value="Age">Age</option>
	                                <option value="Name">Name</option>
	                            </select>
	                        </div>
	                    </div>
	
	                    <div class="col-xs-6">
	                        <div class="form-group">
	                            <label for="${name}">${label} Identity</label>
	                            <input id="${name}" type="text" class="form-control" name="${name}Identity">
	                        </div>
	                    </div>  
	                </div>
	                <input type="hidden" class="hidden" />
	                <a class="removebutton" href="#">remove ${name} identity</a>    
	            </div>
            </div>  
        </div> <!-- identity-->
        <div class="text-center col-xs-12">
            <div id="${name}Container"></div>
            <button id="${name}Btn" type="button" class="btn btn-default add_field_button">Add ${label}</button>
        </div>
    </div> 
</div>