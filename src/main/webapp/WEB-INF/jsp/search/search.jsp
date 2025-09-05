<!DOCTYPE html>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tatari" tagdir="/WEB-INF/tags" %>
<html lang="en">
<jsp:include page="../fragments/htmlHeader.jsp"/>
<body>
<jsp:include page="../fragments/menubar.jsp"/>
	<div class="container">
		<div class="st_formframe">
			<div class="title">Genie Search Form</div>
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
			<form:form role="form" modelAttribute="geneCodonSearchTerm" >
				<tatari:sheetselectstring name="type" names="${variantTypes}" showPleaseSelect="true"/>
				<tatari:inputField label="Gene *" type="text" name="gene" placeholder="Enter gene name, e.g. KRAS"/>
				<tatari:inputField label="Codon *" type="text" name="codon" placeholder="Enter the codon number affected, e.g. 12"/>
				<tatari:inputField label="Incidence *" type="text" name="incidence" placeholder="Enter the minimum incidence, default 0"/>
 				<div class="buttondiv"><button type="submit" class="btn btn-default">Submit</button></div>
    		</form:form>
    		<p class="message">${msg}</p>
    		</div>
    	</div>
	<jsp:include page="../fragments/footer.jsp"/>
</body>
</html>
