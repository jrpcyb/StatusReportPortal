<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- Header -->
<%
	String pageTitle = "Create/Edit Project";
%>
<jsp:include page="header.jsp" flush="true">
	<jsp:param value="<%=pageTitle%>" name="pageTitle" />
</jsp:include>

<!-- Content -->
<div class="col_10" style="text-align: left;">
	<c:choose>
		<c:when test="${fn:length(param.project_id) > 0}">
			<h5>Edit Project</h5>
		</c:when>

		<c:otherwise>
			<h5>Create Project</h5>
		</c:otherwise>
	</c:choose>

	<hr class="alt1" />
</div>
<div class="col_2"></div>
<c:choose>
	<c:when test="${fn:length(managers) > 0}">
		<!-- Notification -->
		<c:if test="${fn:length(notifymsg) > 0}">
			<div class="col_3"></div>
			<div class="col_6">
				<div class="notice success" id="deleteNotify">
					<i class="icon-ok icon-large"></i> ${notifymsg } <a href="#close"
						class="icon-remove"></a>
				</div>
			</div>
			<div class="col_3"></div>
			<div class="clearfix"></div>
		</c:if>
		<!-- End of Notification -->
		<form:form method="post" modelAttribute="project"
			action="createProject">
			<div class="col_3"></div>
			<div class="col_9" style="text-align: left;margin-top:3%;">
				<div class="col_3">
					<form:label path="project_code">Project Code<span
							class="req">*</span>
					</form:label>
				</div>
				<div class="col_9">
					<c:choose>
						<c:when test="${fn:length(param.project_id) > 0}">
							&nbsp;${param.project_code }
							<input type="hidden" name="project_code"
								value=${param.project_code } />
							<input type="hidden" name="project_id" value=${param.project_id } />
						</c:when>
						<c:otherwise>
							<form:input path="project_code" cssClass="col_7" />
						</c:otherwise>
					</c:choose>
				</div>
				<div class="clear"></div>
				<div class="col_3">
					<form:label path="project_name">Project Name<span
							class="req">*</span>
					</form:label>
				</div>
				<div class="col_9">
					<form:input path="project_name" cssClass="col_7" />
				</div>

				<div class="col_3">
					<form:label path="manager_id">Project Manager<span
							class="req">*</span>
					</form:label>
				</div>
				<div class="col_9">
					<form:select path="manager_id" cssClass="col_7">
						<form:option value="" label="---Select---" />
						<c:forEach items="${managers}" var="user">
							<form:option value="${user.empid }">
								<c:out value="${user.firstname} ${user.lastname}" />
							</form:option>
						</c:forEach>
					</form:select>
				</div>
			</div>
			<div class="col_12" style="margin-top:3%;margin-bottom:3%;">
				<c:choose>
					<c:when test="${fn:length(param.project_id) > 0}">
						<form:button class="blue">
							<i class="icon-upload">&nbsp;Update</i>
						</form:button>
						<input type="hidden" name="hiddenAction" value="update" />
					</c:when>

					<c:otherwise>
						<form:button class="blue">
							<i class="icon-save">&nbsp;Submit</i>
						</form:button>
					</c:otherwise>
				</c:choose>
			</div>

		</form:form>

	</c:when>

	<c:otherwise>
		<div class="col_12">${noManagersMsg }</div>
	</c:otherwise>

</c:choose>
<script type="text/javascript">
	$("#project_code").attr('required', '');
	$("#project_name").attr('required', '');
	$("#manager_id").attr('required', '');
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#deleteNotify').delay(3000).fadeOut();
	});
</script>
<!-- Footer -->
<jsp:include page="footer.jsp" />
