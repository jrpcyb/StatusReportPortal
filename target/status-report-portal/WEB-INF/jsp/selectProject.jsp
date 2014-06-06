<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:choose>
	<c:when test="${fn:length(projectList) > 0}">
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
		<form:form method="post" modelAttribute="project">
			<div class="col_12" style="margin-top:3%;margin-bottom:3%;">
				<form:label path="project_id">Select Project Name<span
						class="req">*</span>
				</form:label>
				<form:select path="project_id" class="col_4">
					<form:option value="" label="---Select---" />
					<c:forEach items="${projectList}" var="proj">
						<form:option value="${proj.project_id }">
							<c:out value="${proj.project_name }" />
						</form:option>
					</c:forEach>
				</form:select>
				&nbsp; <span style="color: #9B089B;">(*includes all your
					project except Internal)</span>
			</div>
			<div class="col_12">
				<form:button class="blue">Next&nbsp;<i
						class="icon-chevron-right"></i>
				</form:button>
			</div>
		</form:form>
		<div class="col_12" style="text-align:left;">
			<div class="notice warning" style="margin-top: 5%;"><i class="icon-info-sign icon-large"></i>Resources registered by you, are
	assigned to your "Internal" project by default. Hence, your resources who are not assigned to any project by you or your supervisor are tagged to your Internal project implicitly. So, no need to assign explicitly.
		</div>
</div>
	</c:when>

	<c:otherwise>
		<div class="col_12">
			<p>${noProjMsg }</p>
		</div>
	</c:otherwise>
</c:choose>
<script type="text/javascript">
	$(document).ready(function() {

		$("#project_id").attr('required', '');

	});
</script>