<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- Header -->
<%
	String pageTitle = "Assign project";
%>
<jsp:include page="header.jsp" flush="true">
	<jsp:param value="<%=pageTitle%>" name="pageTitle" />
</jsp:include>

<!-- Content -->
<div class="col_10" style="text-align: left;">
	<h5>Assign Project</h5>
	<hr class="alt1" />
</div>
<div class="col_2"></div>
<jsp:include page="selectProject.jsp" />

<script type="text/javascript">
	$(document).ready(function() {
		$('#deleteNotify').delay(3000).fadeOut();
	});
</script>

<jsp:include page="footer.jsp" />
