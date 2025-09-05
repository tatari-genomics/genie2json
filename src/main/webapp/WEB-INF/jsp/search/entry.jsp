<!DOCTYPE html>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tatari" tagdir="/WEB-INF/tags" %>
<html lang="en">
<jsp:include page="../fragments/htmlHeader.jsp"/>
<body>
<jsp:include page="../fragments/menubar.jsp"/>
	<div class="container">
		<div class="st_formframe">
			<div class="title">Results</div>
			<div class="panel-group">
    				<div class="panel panel-default">
      				<div class="panel-heading">
       					<h4 class="panel-title">
          					<a data-toggle="collapse" href="#uploadinfo"><span class="green">&#9432;</span></a>
        					</h4>
      				</div>
      				<div id="uploadinfo" class="panel-collapse collapse">
        					<div class="panel-body">
       	 					<div class="info">
								<ul>
									<li>Required fields indicated by *.</li>
								</ul>
							</div>				
        					</div>
      				</div>
    				</div>
  			</div> 
<table class="aliquot">
										<c:forEach var="genieEntry" items="${genieEntries}">
										<tr><td class="data">${genieEntry.displayText}</td></tr>
 		    				</c:forEach>
									</table>
    		</div>
    	</div>
	<jsp:include page="../fragments/footer.jsp"/>
</body>
</html>
