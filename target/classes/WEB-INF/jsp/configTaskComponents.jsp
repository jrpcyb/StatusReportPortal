<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- Header -->
<%
	String pageTitle = "Step 2 - Task Configuration";
%>
<jsp:include page="header.jsp" flush="true">
	<jsp:param value="<%=pageTitle%>" name="pageTitle" />
</jsp:include>

<!-- Content -->
<div class="col_10" style="text-align: left;">
	<h5>Task Configuration - Step 2</h5>
	<hr class="alt1" />
</div>
<div class="col_2"></div>
<div class="col_12" style="padding-top: 2%;">Select the task
	components from below list for the project&nbsp;${project.project_name
	}&nbsp;:</div>
<form:form method="post" modelAttribute="taskConfiguration"
	action="configTaskComponents">
	<div class="col_3"></div>
	<div class="col_6">
		<fieldset>
			<legend>Components</legend>
			<div style="text-align: left; margin-left: 5%">
				<c:choose>
					<c:when test="${allCols != ''}">
						<c:choose>
							<c:when test="${allCols.role != 1 }">
								<form:checkbox path="role" value="1" id="role" />
							</c:when>
							<c:otherwise>
								<form:checkbox path="role" value="${allCols.role}" id="role"
									checked="checked" />
							</c:otherwise>
						</c:choose>
						<label for="role" class="inline">Role</label>
						<br />

						<c:choose>
							<c:when test="${allCols.ticket_id != 1}">
								<form:checkbox path="ticket_id" value="1" id="ticket_id" />
							</c:when>
							<c:otherwise>
								<form:checkbox path="ticket_id" value="${allCols.ticket_id}"
									id="ticket_id" checked="checked" />
							</c:otherwise>
						</c:choose>
						<label for="ticket_id" class="inline">Ticket ID</label>
						<br />

						<c:choose>
							<c:when test="${allCols.plan_unplanned != 1}">
								<form:checkbox path="plan_unplanned" value="1"
									id="plan_unplanned" />
							</c:when>
							<c:otherwise>
								<form:checkbox path="plan_unplanned"
									value="${allCols.plan_unplanned}" id="plan_unplanned"
									checked="checked" />
							</c:otherwise>
						</c:choose>
						<label for="plan_unplanned" class="inline">Plan Unplanned</label>
						<br />
						<c:choose>
							<c:when test="${allCols.work_done_today != 1}">
								<form:checkbox path="work_done_today" value="1"
									id="work_done_today" />

							</c:when>
							<c:otherwise>
								<form:checkbox path="work_done_today"
									value="${allCols.work_done_today}" id="work_done_today"
									checked="checked" />
							</c:otherwise>
						</c:choose>

						<label for="work_done_today" class="inline">Work done
							today</label>
						<br />
						<c:choose>
							<c:when test="${allCols.impediments != 1}">
								<form:checkbox path="impediments" value="1" id="impediments" />
							</c:when>
							<c:otherwise>
								<form:checkbox path="impediments" value="${allCols.impediments}"
									id="impediments" checked="checked" />
							</c:otherwise>
						</c:choose>

						<label for="impediments" class="inline">Impediments</label>
						<br />

						<c:choose>
							<c:when test="${allCols.comments != 1}">
								<form:checkbox path="comments" value="1" id="comments" />
							</c:when>
							<c:otherwise>
								<form:checkbox path="comments" value="${allCols.comments}"
									id="comments" checked="checked" />
							</c:otherwise>

						</c:choose>
						<label for="comments" class="inline">Comments</label>
						<br />
						<c:choose>
							<c:when test="${allCols.status != 1}">
								<form:checkbox path="status" value="1" id="status" />

							</c:when>
							<c:otherwise>
								<form:checkbox path="status" value="${allCols.status}"
									id="status" checked="checked" />
							</c:otherwise>

						</c:choose>
						<label for="status" class="inline">Status</label>
						<br />

						<c:choose>
							<c:when test="${allCols.plan_for_next_day != 1}">
								<form:checkbox path="plan_for_next_day" value="1"
									id="plan_for_next_day" />
							</c:when>
							<c:otherwise>
								<form:checkbox path="plan_for_next_day"
									value="${allCols.plan_for_next_day}" id="plan_for_next_day"
									checked="checked" />

							</c:otherwise>
						</c:choose>

						<label for="plan_for_next_day" class="inline">Plan for
							next day</label>
						<br />

						<c:choose>
							<c:when test="${allCols.work_done_yesterday != 1}">
								<form:checkbox path="work_done_yesterday" value="1"
									id="work_done_yesterday" />

							</c:when>
							<c:otherwise>
								<form:checkbox path="work_done_yesterday"
									value="${allCols.work_done_yesterday}" id="work_done_yesterday"
									checked="checked" />

							</c:otherwise>
						</c:choose>

						<label for="work_done_yesterday" class="inline">Work done
							yesterday</label>
						<br />

						<c:choose>
							<c:when test="${allCols.other_work != 1}">
								<form:checkbox path="other_work" value="1" id="other_work" />
							</c:when>
							<c:otherwise>
								<form:checkbox path="other_work" value="${allCols.other_work}"
									id="other_work" checked="checked" />
							</c:otherwise>
						</c:choose>

						<label for="other_work" class="inline">Other work</label>
					</c:when>
					<c:otherwise>
						<form:checkbox path="role" value="1" id="role" />
						<label for="role" class="inline">Role</label>
						<br />
						<form:checkbox path="ticket_id" value="1" id="ticket_id" />
						<label for="ticket_id" class="inline">Ticket ID</label>
						<br />
						<form:checkbox path="plan_unplanned" value="1" id="plan_unplanned" />
						<label for="plan_unplanned" class="inline">Plan Unplanned</label>
						<br />
						<form:checkbox path="work_done_today" value="1"
							id="work_done_today" />
						<label for="work_done_today" class="inline">Work done
							today</label>
						<br />
						<form:checkbox path="impediments" value="1" id="impediments" />
						<label for="impediments" class="inline">Impediments</label>
						<br />
						<form:checkbox path="comments" value="1" id="comments" />
						<label for="comments" class="inline">Comments</label>
						<br />
						<form:checkbox path="status" value="1" id="status" />
						<label for="status" class="inline">Status</label>
						<br />
						<form:checkbox path="plan_for_next_day" value="1"
							id="plan_for_next_day" />
						<label for="plan_for_next_day" class="inline">Plan for
							next day</label>
						<br />
						<form:checkbox path="work_done_yesterday" value="1"
							id="work_done_yesterday" />
						<label for="work_done_yesterday" class="inline">Work done
							yesterday</label>
						<br />
						<form:checkbox path="other_work" value="1" id="other_work" />
						<label for="other_work" class="inline">Other work</label>
						<br />
					</c:otherwise>
				</c:choose>
			</div>
		</fieldset>
	</div>
	<div class="col_3"></div>
	<div class="col_12" style="margin-bottom:3%;">
		<form:button class="blue">
			<i class="icon-save">&nbsp;Submit</i>
		</form:button>
		<form:hidden path="project_id"
			value="${taskConfiguration.project_id }" />
		<form:hidden path="manager_id"
			value="${taskConfiguration.manager_id }" />
	</div>
</form:form>