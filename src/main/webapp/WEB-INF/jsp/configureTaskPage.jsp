<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- Header -->
<%
	String pageTitle = "Step 1 - Task Configuration";
%>
<jsp:include page="header.jsp" flush="true">
	<jsp:param value="<%=pageTitle%>" name="pageTitle" />
</jsp:include>

<!-- Content -->
<div class="col_10" style="text-align: left;">
	<h5>Task Configuration - Step 1</h5>
	<hr class="alt1" />
</div>
<div class="col_2"></div>
<!-- Notification -->
<c:if test="${fn:length(notifymsg) > 0}">
	<div class="col_3"></div>
	<div class="col_6">
		<div class="notice success" id="deleteNotify">
			<i class="icon-ok icon-large"></i>
			<c:out value="${notifymsg }"></c:out>
			<a href="#close" class="icon-remove"></a>
		</div>
	</div>
	<div class="col_3"></div>
	<div class="clearfix"></div>
</c:if>
<!-- End of Notification -->
<form:form method="post" modelAttribute="taskConfiguration">
	<div class="col_12" style="margin-top:3%;">
		<form:label path="project_id">Select Project Name<span
				class="req">*</span>
		</form:label>
		<form:select path="project_id" class="col_3">
			<form:option value="" label="---Select---" />
			<form:option value="-1">
					Your Internal Project
				</form:option>
			<c:if test="${fn:length(projectList) > 0}">
				<c:forEach items="${projectList}" var="proj">
					<form:option value="${proj.project_id }">
						<c:out value="${proj.project_name }" />
					</form:option>
				</c:forEach>
			</c:if>

		</form:select>
		&nbsp; <span style="color: #9B089B;">(*includes your project
			only)</span>
	</div>
	<div class="col_12" style="margin-top:3%;">
		<form:button class="blue">Next&nbsp;<i
				class="icon-chevron-right"></i>
		</form:button>
		<form:hidden path="manager_id" value="${currEmpid }" />
	</div>
</form:form>
<div class="col_12" style="text-align:left;">
<div class="notice warning" style="margin-top: 5%;"><i class="icon-info-sign icon-large"></i>Resources registered by you, but not assigned to any project yet, are
	assigned to project "Internal" by default. You can configure their task
	page.</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		$('#deleteNotify').delay(3000).fadeOut();
	});
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#project_id").attr('required', '');
	});
</script>
<jsp:include page="footer.jsp" />