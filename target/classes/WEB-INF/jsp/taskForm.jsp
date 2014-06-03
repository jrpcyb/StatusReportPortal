<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String pageTitle = "Task Submission";
%>
<jsp:include page="header.jsp" flush="true">
	<jsp:param value="<%=pageTitle%>" name="pageTitle" />
</jsp:include>

<!-- Content -->
<div class="col_10" style="text-align: left;">
	<h5>Task you did today</h5>
	<hr class="alt1" />
</div>


<c:choose>
	<c:when test="${fn:length(noConfig) > 0}">
		<div class="col_12">
			<c:out value="${noConfig }"></c:out>
		</div>
	</c:when>
	<c:otherwise>
		<div class="col_3"></div>
		<form:form method="post" modelAttribute="task" action="taskForm">
			<div class="col_9" style="text-align: left;">
				<div class="col_3">Date</div>
				<div class="col_9">${todayStr }&nbsp;&nbsp;(MM/DD/YYYY)</div>

				<c:if test="${allConfig.role == 1 }">
					<div class="col_3">
						<form:label path="roleid">Role<span class="req">*</span>
						</form:label>
					</div>
					<div class="col_9">
						<form:select path="roleid" cssClass="col_4">
							<form:option value="" label="---Select---" />
							<c:forEach items="${roles }" var="role">
								<form:option value="${role.roleid }" label="${role.role }" />
							</c:forEach>
						</form:select>
					</div>
				</c:if>

				<c:if test="${allConfig.ticket_id == 1}">
					<div class="col_3">
						<form:label path="ticketid">Ticket ID<span
								class="req">*</span>
						</form:label>
					</div>
					<div class="col_9">
						<form:input path="ticketid" cssClass="col_4" />
					</div>
				</c:if>

				<c:if test="${allConfig.plan_unplanned == 1 }">
					<div class="col_3">
						<form:label path="planUnplan">Planned/Unplanned<span
								class="req">*</span>
						</form:label>
					</div>
					<div class="col_9">

						<form:select path="planUnplan" cssClass="col_4">
							<form:option value="" label="---Select---" />
							<form:option value="planned" label="Planned" />
							<form:option value="unplanned" label="Unplanned" />
						</form:select>
					</div>
				</c:if>

				<c:if test="${allConfig.work_done_yesterday == 1 }">
					<div class="col_3">
						<form:label path="workDoneYesterday">Work done Yesterday<span
								class="req">*</span>
						</form:label>
					</div>
					<div class="col_9">
						<form:textarea path="workDoneYesterday" cssClass="col_8"
							style="height:70px;" />
					</div>
				</c:if>

				<c:if test="${allConfig.work_done_today == 1}">
					<div class="col_3">
						<form:label path="workDoneToday">Work done Today<span
								class="req">*</span>
						</form:label>
					</div>
					<div class="col_9">
						<form:textarea path="workDoneToday" cssClass="col_8"
							style="height:70px;" />
					</div>
				</c:if>

				<c:if test="${allConfig.impediments == 1 }">
					<div class="col_3">
						<form:label path="impediments">Impediments</form:label>
					</div>
					<div class="col_9">
						<form:textarea path="impediments" cssClass="col_8"
							style="height:70px;" />
					</div>
				</c:if>

				<c:if test="${allConfig.comments == 1 }">
					<div class="col_3">
						<form:label path="comment">Comment</form:label>
					</div>
					<div class="col_9">
						<form:textarea path="comment" cssClass="col_8"
							style="height:70px;" />
					</div>
				</c:if>

				<c:if test="${allConfig.status == 1 }">
					<div class="col_3">
						<form:label path="statusCode">Status<span class="req">*</span>
						</form:label>
					</div>
					<div class="col_9">

						<form:select path="statusCode" cssClass="col_4">
							<form:option value="" label="---Select---" />
							<c:forEach items="${statusList }" var="status">
								<form:option value="${status.status }"
									label="${status.status_description }" />
							</c:forEach>
						</form:select>

					</div>
				</c:if>

				<c:if test="${allConfig.plan_for_next_day == 1 }">
					<div class="col_3">
						<form:label path="planForNextDay">Plan for the next day<span
								class="req">*</span>
						</form:label>
					</div>
					<div class="col_9">
						<form:textarea path="planForNextDay" cssClass="col_8"
							style="height:70px;" />
					</div>
				</c:if>

				<c:if test="${allConfig.other_work == 1 }">
					<div class="col_3">
						<form:label path="otherWork">Other Work</form:label>
					</div>
					<div class="col_9">
						<form:textarea path="otherWork" cssClass="col_8"
							style="height:70px;" />
					</div>
				</c:if>
			</div>
			<div class="col_12">
				<form:button class="blue">
					<i class="icon-save">&nbsp;Submit</i>
				</form:button>
				<form:hidden path="id" value="${id }" />
				<form:hidden path="project_id" value="${allConfig.project_id }" />
			</div>

		</form:form>
	</c:otherwise>
</c:choose>

<script type="text/javascript">
	$(document).ready(function() {

		$("#roleid").attr('required', '');
		$("#ticketid").attr('required', '');
		$("#workDoneYesterday").attr('required', '');
		$("#workDoneToday").attr('required', '');
		$("#planUnplan").attr('required', '');
		$("#statusCode").attr('required', '');
		$("#planForNextDay").attr('required', '');

	});
</script>

