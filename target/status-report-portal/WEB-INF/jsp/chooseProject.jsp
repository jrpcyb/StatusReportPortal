<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<form:form method="post" modelAttribute="taskConfiguration"
	action="projectTask">
	<div class="col_12" style="margin-top:3%;">
		<form:label path="project_id">Select Project Name<span
				class="req">*</span>
		</form:label>
		<form:select path="project_id" class="col_3">
			<form:option value="" label="---Select---" />
			<form:option value="-1">
					Internal Project of Lead/Manager
				</form:option>
			<c:if test="${fn:length(projectList) > 0}">
				<c:forEach items="${projectList}" var="proj">
					<form:option value="${proj.project_id }">
						<c:out value="${proj.project_name }" />
					</form:option>
				</c:forEach>
			</c:if>

		</form:select>
		&nbsp; <span style="color: #9B089B;">(*includes all your
			project)</span>
	</div>
	<div class="col_12" style="height:3em;">
		<div class="col_3"></div>
		<div class="col_6" style="text-align:left;">
			<span class="req" style="font-weight:normal;">(*Mandatory)</span>
		</div>
		<div class="col_3"></div>
	</div>
	<div class="col_12" style="margin-top:3%;text-align:center;margin-bottom:5%;">
		<form:button class="blue">Next&nbsp;<i
				class="icon-chevron-right"></i>
		</form:button>
		<form:hidden path="manager_id" value="${currEmpid }" />
	</div>
</form:form>






<script type="text/javascript">
	$(document).ready(function() {
		$("#project_id").attr('required', '');
	});
</script>