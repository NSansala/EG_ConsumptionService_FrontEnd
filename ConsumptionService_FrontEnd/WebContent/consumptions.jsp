<%@page import="com.ConsumptionService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Power Consumption Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/consumptions.js"></script> 
<link rel="stylesheet" href="//cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">
</head>
<body>
	<!-- As a heading -->
	<nav class="navbar navbar-dark" style="background: #990000;">
		<h3 style="color: white">Power Consumption Management</h3>
	</nav>


	<div class="container">

		<form id="frmConsumption" name="frmConsumption"
			style="margin-top: 20px; margin-left: 25%; width: 500px;">

			<div class="form-group">
				<label>Current Reading</label> <input type="text" name="currentReading" id="currentReading"
					style="margin-bottom:15px;" class="form-control" placeholder="Current Reading" size="30px" required>
			</div>

			<div class="form-group" id="datePicker">
				<label>Current Read Date</label> <input type="text" name="currentReadDate"
					id="currentReadDate" style="margin-bottom:15px;" class="form-control" placeholder="Current Read Date"
					size="30px" required>
			</div>

			<div class="form-group">
				<label>Previous Reading</label> <input type="text" name="previousReading"
					id="previousReading" style="margin-bottom:15px;" class="form-control" placeholder="Previous Reading"
					size="30px" required>
			</div>

			<div class="form-group">
				<label>Previous Read Date</label> <input type="text" name="previousReadDate" id="previousReadDate"
					style="margin-bottom:15px;" class="form-control" placeholder="Previous Read Date" size="30px" required>
			</div>

			<div class="form-group">
				<label>Consumed Units</label> <input type="text" name="consumedUnits" id="consumedUnits"
					style="margin-bottom:15px;" class="form-control" placeholder="Consumed Units" size="30px" required>
			</div>

			<div class="form-group">
				<label>Month</label> <input type="text" name="month" id="month"
					style="margin-bottom:15px;" class="form-control" placeholder="Month" size="30px" required>
			</div>
			<div class="form-group">
				<label>Account Id</label> <input type="text" name="accountId" id="accountId"
					style="margin-bottom:15px;" class="form-control" placeholder="Account Id" size="30px" required>
			</div>
 
			<div class="form-group" align="right">
				<input id="btnSave" name="btnSave" type="button" value="Save" style="margin-bottom:15px;" class="btn btn-primary">
				<input type="hidden" id="hidConsumptionIDSave" name="hidConsumptionIDSave" value="">
			</div>

		</form>
		<div id="alertSuccess" class="alert alert-success"></div>
		<div id="alertError" class="alert alert-danger"></div>
		<br>
		<div id="divConsumptionsGrid">
		<%
			ConsumptionService consumptionObj = new ConsumptionService();
			out.print(consumptionObj.readConsumptions());
		%>
		<br><br>
		</div>
	</div>

</body>
</html>