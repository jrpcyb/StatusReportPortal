<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- Header -->
<%
	String pageTitle = "Admin : Download Report";
%>
<jsp:include page="header.jsp" flush="true">
	<jsp:param value="<%=pageTitle%>" name="pageTitle" />
</jsp:include>

<!-- Content -->
<div class="col_10" style="text-align: left;">
	<h5>Download Report</h5>
	<hr class="alt1" />
</div>
<div class="col_2"></div>

		<form:form method="post" modelAttribute="project">
			<div class="col_3"></div>
			<div class="col_9" style="text-align: left; margin-top:3%;">
				<div class="col_3">
					<form:label path="project_id">Select Project Name<span
							class="req">*</span>
					</form:label>
				</div>
				<div class="col_9">
					<form:select path="project_id" class="col_6">
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

				<div class="col_3">
					<form:label path="format">Choose format<span
							class="req">*</span>
					</form:label>
				</div>
				<div class="col_9">
					<form:select path="format" class="col_6">
						<form:option value="" label="---Select---" />
						<form:option value="excel" label="Excel" />
						<form:option value="word" label="Word" />
					</form:select>
				</div>

				<div class="col_3">
					<form:label path="selected_date">Select Date<span
							class="req">*</span>
					</form:label>
				</div>
				<div class="col_9">
					<input type="text" id="datepicker" name="selected_date" />
				</div>


				<div class="col_8">
					<div class="excel">
						<fieldset>
							<legend>Which data columns do you want to see in report?</legend>
							<%-- <c:forEach items="${usersList}" var="user">
								<form:checkbox path="empid" value="${user.empid}"
									id="${user.empid}" />
								<label for="${user.empid}" class="inline">${user.firstname}&nbsp;${user.lastname}</label>
								<br />
							</c:forEach> --%>
							<form:checkbox path="col" id="Date" value="Date"
								checked="checked" />
							<label for="Date" class="inline">Date</label> <br />
							<form:checkbox path="col" id="Resource Name"
								value="Resource Name" checked="checked" />
							<label for="Resource Name" class="inline">Resource Name</label> <br />
							<form:checkbox path="col" id="Dev/QA/UI" value="Dev/QA/UI"
								checked="checked" />
							<label for="Dev/QA/UI" class="inline">Role (Dev/QA/UI)</label> <br />
							<form:checkbox path="col" id="Assignment/Story/Ticket ID"
								value="Assignment/Story/Ticket ID" checked="checked" />
							<label for="Assignment/Story/Ticket ID" class="inline">Assignment/Story/Ticket
								ID</label> <br />
							<form:checkbox path="col" id="Planned/Unplanned"
								value="Planned/Unplanned" checked="checked" />
							<label for="Planned/Unplanned" class="inline">Planned/Unplanned</label>
							<br />
							<form:checkbox path="col" id="Work Done Today"
								value="Work Done Today" checked="checked" />
							<label for="Work Done Today" class="inline">Work Done
								Today</label> <br />
							<form:checkbox path="col" id="Impediment" value="Impediment"
								checked="checked" />
							<label for="Impediment" class="inline">Impediment</label> <br />
							<form:checkbox path="col" id="Comment" value="Comment"
								checked="checked" />
							<label for="Comment" class="inline">Comment</label> <br />
							<form:checkbox path="col" id="Status" value="Status"
								checked="checked" />
							<label for="Status" class="inline">Status</label> <br />
							<form:checkbox path="col" id="Work Done Yesterday"
								value="Work Done Yesterday" checked="checked" />
							<label for="Work Done Yesterday" class="inline">Work Done
								Yesterday</label> <br />
							<form:checkbox path="col" id="Plan For Next Day"
								value="Plan For Next Day" checked="checked" />
							<label for="Plan For Next Day" class="inline">Plan For
								Next Day</label> <br />
							<form:checkbox path="col" id="Other work" value="Other work"
								checked="checked" />
							<label for="Other work" class="inline">Other work</label>

						</fieldset>

					</div>
				</div>
			</div>
			<div class="col_12" style="height:3em;">
				<div class="col_3"></div>
				<div class="col_6" style="text-align:left;">
					<span class="req" style="font-weight:normal;">(*Mandatory)</span>
				</div>
				<div class="col_3"></div>
			</div>
			<div class="col_12" style="margin-top:2%;">
				<form:button class="blue">Download&nbsp;<i
						class="icon-chevron-right"></i>
				</form:button>
			</div>
		</form:form>
<div class="col_12" style="text-align:left;">
	<div class="notice warning" style="margin-top: 5%;"><i class="icon-info-sign icon-large"></i>Resources registered by you, are
	assigned to your "Internal" project by default. So your resources who are not assigned to any project by you or your supervisor are tagged to your Internal project implicitly. You can download the report for the same.
	</div>
</div>
	
<script>
	/*  jQuery ready function. Specify a function to execute when the DOM is fully loaded.  
	 *  http://basicuse.net/articles/pl/scripting_languages/javascript/jquery_ui_widgets_datepicker_calendar
	 */
	$(document).ready(

	/* This is the function that will get executed after the DOM is fully loaded */
	function() {
		$("#datepicker").datepicker({
			changeMonth : true,//this option for allowing user to select month
			changeYear : true, //this option for allowing user to select from year range
			maxDate : '+0d'
		});
		$("#datepicker").attr('required', '');
		$("#project_id").attr('required', '');
		$("#format").attr('required', '');

		$('.excel').hide();
		$('#format').change(function() {
			if (this.value == 'excel') {
				$('.excel').show();
			} else {
				$('.excel').hide();
			}
		});
	}

	);
</script>
<!-- Footer -->
<jsp:include page="footer.jsp" />