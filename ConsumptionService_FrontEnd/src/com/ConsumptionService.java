package com;

import java.sql.*;

public class ConsumptionService {
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/consumptionservicedb","root","0919");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public String readConsumptions() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Current Reading</th><th>Current Read Date</th><th>Previous Reading</th><th>Previous Read Date</th>"
					+ "<th>Consumed Units</th><th>Month</th><th>Account Id</th><th>Update</th><th>Remove</th></tr>";
			String query = "select * from consumption";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String consumptionId = Integer.toString(rs.getInt("consumptionId"));
				String currentReading = Integer.toString(rs.getInt("currentReading"));
				String currentReadDate = rs.getString("currentReadDate");
				String previousReading = Integer.toString(rs.getInt("previousReading"));
				String previousReadDate = rs.getString("previousReadDate");
				String consumedUnits = Integer.toString(rs.getInt("consumedUnits"));
				String month = rs.getString("month");
				String accountId = Integer.toString(rs.getInt("accountId"));
				// Add into the html table
				output += "<tr><td><input id='hidConsumptionIDUpdate' name='hidConsumptionIDUpdate' type='hidden' value='" + consumptionId
						+ "'>" + currentReading + "</td>";
				output += "<td>" + currentReadDate + "</td>";
				output += "<td>" + previousReading + "</td>";
				output += "<td>" + previousReadDate + "</td>";
				output += "<td>" + consumedUnits + "</td>";
				output += "<td>" + month + "</td>";
				output += "<td>" + accountId + "</td>";
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-consumptionid='"
						+ consumptionId + "'>" + "</td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the consumptions.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String insertConsumption(String currentreading, String currentreaddate, String previousreading, String previousreaddate, String consumedunits, String mon, String accountid) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into consumption(`consumptionId`,`currentReading`,`currentReadDate`,`previousReading`,`previousReadDate`,`consumedUnits`,`month`,`accountId`)"

					+ " values (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, currentreading);
			preparedStmt.setString(3, currentreaddate);
			preparedStmt.setString(4, previousreading);
			preparedStmt.setString(5, previousreaddate);
			preparedStmt.setString(6, consumedunits);
			preparedStmt.setString(7, mon);
			preparedStmt.setString(8, accountid);
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newConsumptions = readConsumptions();
			output = "{\"status\":\"success\", \"data\": \"" + newConsumptions + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the consumptions.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String updateConsumption(String ID, String currentreading, String currentreaddate, String previousreading, String previousreaddate, String consumedunits, String mon, String accountid) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE consumption SET currentReading=?,currentReadDate=?,previousReading=?,previousReadDate=?,consumedUnits=?,month=?,accountId=? WHERE consumptionId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, currentreading);
			preparedStmt.setString(2, currentreaddate);
			preparedStmt.setString(3, previousreading);
			preparedStmt.setString(4, previousreaddate);
			preparedStmt.setString(5, consumedunits);
			preparedStmt.setString(6, mon);
			preparedStmt.setString(7, accountid);
			preparedStmt.setInt(8, Integer.parseInt(ID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newConsumptions = readConsumptions();
			output = "{\"status\":\"success\", \"data\": \"" + newConsumptions + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while updating the consumption.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String deleteConsumption(String consumptionId) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from consumption where consumptionId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(consumptionId));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newConsumptions = readConsumptions();
			output = "{\"status\":\"success\", \"data\": \"" + newConsumptions + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the consumption.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

}