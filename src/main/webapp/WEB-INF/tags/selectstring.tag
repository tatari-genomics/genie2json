<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="name" required="true" rtexprvalue="true"
              description="Name of corresponding property in bean object" %>
<%@ attribute name="label" required="true" rtexprvalue="true"
              description="Label appears in red color if input is considered as invalid after submission" %>
<%@ attribute name="names" required="true" rtexprvalue="true" type="java.util.List"
              description="Names in the list" %>
<%@ attribute name="showPleaseSelect" required="true" rtexprvalue="true"
              description="Show Please Select Option" %>
<%@ attribute name="disabled" required="false" rtexprvalue="true"
              description="Sets greyed out fields" %>
<spring:bind path="${name}">
    <c:set var="cssGroup" value="form-group ${status.error ? 'has-error' : '' }"/>
    <c:set var="valid" value="${not status.error and not empty status.actualValue}"/>    
    <div class="${cssGroup}">    	
            	 <c:if test="${not valid}">
        	<label>${label}</label>
        </c:if>
        <c:if test="${valid}">
        	<label class="green">${label} <span class="glyphicon glyphicon-ok"></span></label>
       	</c:if>
    	<form:select class="form-control" path="${name}" disabled="${disabled}">
    		<c:if test="${showPleaseSelect}">
     		<form:option value="${null}">Please Select</form:option>
		</c:if>
     	<form:options items="${names}" />
	</form:select>    	
    	<c:if test="${valid}">
        	<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>
       	</c:if>
      	<c:if test="${status.error}">
            <span class="red">${status.errorMessage}</span>
       	</c:if>
    </div>

</spring:bind>

