<%-- simple input form element --%> 
<%@ tag description="A simple input" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="label" required="true" %>
<%@ attribute name="helpText" required="false" %>
<%@ attribute name="people" required="true" type="java.util.List"%>


<div class="form-group">
    <div class="row">
        <div class="col-sm-8 col-sm-offset-0 col-md-8 col-md-offset-1">
            <label for="${name}hidden"> ${label}s 
                <span id="${name}Help" role="helpBtn" class="glyphicon glyphicon-info-sign"></span>
            </label>
            <input id="${name}hidden" class="hidden" type="hidden" class="form-control" name="${name}Identity">
        </div> 
    </div> 
    <!-- person --> 
    <div class="row">
        <div class="hidden">
        	<div class="${name}IdentitesContainer"></div>
            <div data-id="${name}"> 
                <div class="text-center col-sm-8 col-sm-offset-0 col-md-8 col-md-offset-1">
                    <button type="button" class="${name}identityBtn btn btn-default add_field_button">Add ${label} Identity</button>      
                    <a class="removebutton" data-parent="2" href="#">remove ${name}</a>
                    <hr>  
                </div>
            </div>
        </div> 
    </div> 
    <!-- END person -->
    <!-- hidden identity --> 
    <div class="hidden">
        <div data-id="${name}identity">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-0 col-md-8 col-md-offset-1">
                    <div class="container-fluid">
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
                                        <option value="Name" selected>Name</option>
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
                    </div>
                    <input type="hidden" class="hidden" />
                    <a class="removebutton" data-parent="3" href="#">remove ${name} identity</a>    
                </div>
                <!-- comments -->
                <%--  There is no way to save comments on new form elements before they are saved, so we are removing the comments section for the hidden input

                <div class="col-sm-4 col-md-3">
                    <t:comments name="${name}" id="${identity.id}" />
                </div> --%> 
                <!-- END comments --> 
            </div>
        </div>   
    </div> 
    <!-- END hidden identity -->

    <!-- END populate identities and comments --> 
    <div class="row">
        <div id="${name}Container">
            <!-- populate identities and comments --> 
            <c:forEach var="person" items="${people}"> 
                <div data-id="${name}">
                    <c:forEach var="identity" items="${person.identities}">
                        <div data-id="${name}identity">
                            <div class="row">
                                <div class="col-sm-8 col-sm-offset-0 col-md-8 col-md-offset-1">
                                    <div class="container-fluid">
                                        <div class="row">
                                            <div class="col-xs-6">
                                                <div class="form-group">
                                                    <label for="${name}Category">${label} Identity Category</label>
                                                    <select class="btn btn-default btn-lg dropdown-toggle" id="${name}Category" name="${name}Category" class="form-control">
                                                        <option value="Race" <c:if test="${identity.category eq 'Race'}">selected</c:if> >Race</option>
                                                        <option value="Nationality"<c:if test="${identity.category eq 'Nationality'}">selected</c:if>>Nationality</option>
                                                        <option value="Occupation" <c:if test="${identity.category eq 'Occupation'}">selected</c:if>>Occupation</option>
                                                        <option value="Ethnicity" <c:if test="${identity.category eq 'Ethnicity'}">selected</c:if>>Ethnicity</option>
                                                        <option value="Gender"<c:if test="${identity.category eq 'Gender'}">selected</c:if>>Gender</option>
                                                        <option value="Age" <c:if test="${identity.category eq 'Age'}">selected</c:if>>Age</option>
                                                        <option value="Name" <c:if test="${identity.category eq 'Name'}">selected</c:if>>Name</option>
                                                    </select>
                                                </div>
                                            </div>
                        
                                            <div class="col-xs-6">
                                                <div class="form-group">
                                                    <label for="${name}">${label} Identity</label>
                                                    <input id="${name}" type="text" class="form-control" name="${name}Identity" value="${identity.value}">
                                                </div>
                                            </div>  
                                        </div>
                                    </div>
                                    <a class="removebutton" data-parent="3" href="#">remove ${name} identity</a>    
                                </div>
                                <!-- comments --> 
                                <div class="col-sm-4 col-md-3">
                                    <t:comments name="${name}" comments="${identity.comments}" id="${identity.id}" />
                                </div>
                                <!-- END comments --> 
                            </div>
                        </div>   
                    </c:forEach> <!-- identity forEach--> 

                    <div class="text-center col-sm-8 col-sm-offset-0 col-md-8 col-md-offset-1">
                        <button type="button" class="${name}identityBtn btn btn-default add_field_button">Add ${label} Identity</button>      
                        <a class="removebutton" data-parent="2" href="#">remove ${name}</a>
                        <hr>  
                    </div>
                </div> 
            </c:forEach> <!-- person forEach--> 
        </div> 
        <div class="text-center col-sm-8 col-sm-offset-0 col-md-8 col-md-offset-1">           
            <button id="${name}Btn" type="button" class="btn btn-default add_field_button">Add ${label}</button>
        </div>
    </div>

</div>