$(document).ready(function() {
	$("#alertSuccess").hide();
	$("#alertError").hide();
});

$(document).on("click", "#btnSave", function(event) {
	var status = validateConsumptionForm();
	if (status != true) {
		$("#alertError").show();
		$("#alertError").text(status);
		return;
	}
	// If valid------------------------
	var type = ($("#hidConsumptionIDSave").val() == "") ? "POST" : "PUT";
	console.log(type);
	$.ajax({
		url : "ConsumptionAPI",
		type : type,
		data : $("#frmConsumption").serialize(),
		dataType : "text",
		complete : function(response, status) {
			console.log(response.responseText)
			onConsumptionSaveComplete(response.responseText, status);
		}
	});
});

function onConsumptionSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully Saved");
			$("#alertError").hide();
			$("#alertSuccess").show();
			$("#divConsumptionsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertSuccess").hide();
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error While Saving..");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown Error While Saving..");
		$("#alertError").show();
	}
	$("#hidConsumptionIDSave").val("");
	$("#frmConsumption")[0].reset();
}

$(document).on("click", ".btnUpdate", function(event) {
	$("#hidConsumptionIDSave").val($(this).data("consumptionid")); //change
	$("#currentReading").val($(this).closest("tr").find('td:eq(0)').text()); 
	$("#currentReadDate").val($(this).closest("tr").find('td:eq(1)').text()); 
	$("#previousReading").val($(this).closest("tr").find('td:eq(2)').text());
	$("#previousReadDate").val($(this).closest("tr").find('td:eq(3)').text()); 
	$("#consumedUnits").val($(this).closest("tr").find('td:eq(4)').text()); 
	$("#month").val($(this).closest("tr").find('td:eq(5)').text());
	$("#accountId").val($(this).closest("tr").find('td:eq(6)').text());
});

$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "ConsumptionAPI",
		type : "DELETE",
		data : "consumptionId=" + $(this).data("consumptionid"),  //change
		dataType : "text",
		complete : function(response, status) {
			onConsumptionDeleteComplete(response.responseText, status);
		}
	});
});

function onConsumptionDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully Deleted");
			$("#alertError").hide();
			$("#alertSuccess").show();
			$("#divConsumptionsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertSuccess").hide();
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error While Deleting..");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown Error While Deleting..");
		$("#alertError").show();
	}
}

// CLIENT-MODEL================================================================
function validateConsumptionForm() {
	// Current Reading-------------------------------
	if ($("#currentReading").val().trim() == "") {
		return "Insert Current Reading";
	}
	// Current Read Date-------------------------------
	if ($("#currentReadDate").val().trim() == "") {
		return "Insert Current Read Date";
	}
	// Previous Reading-------------------------------
	if ($("#previousReading").val().trim() == "") {
		return "Insert Previous Reading";
	}
	// Previous Read Date-------------------------------
	if ($("#previousReadDate").val().trim() == "") {
		return "Insert Previous Read Date.";
	}
	// Consumed Units-------------------------------
	if ($("#consumedUnits").val().trim() == "") {
		return "Insert Consumed Units";
	}
	// Month-------------------------------
	if ($("#month").val().trim() == "") {
		return "Insert Month";
	}
	// Account Id-------------------------------
	if ($("#accountId").val().trim() == "") {
		return "Insert Account Id";
	}
	
	return true;
}
