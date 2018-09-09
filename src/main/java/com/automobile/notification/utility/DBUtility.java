package com.automobile.notification.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Scope("singleton")
public class DBUtility {

	private JdbcTemplate jdbcTemplate=null;
	private Connection connection=null;

	public static void main(String[] args)  throws Exception{
		BCryptPasswordEncoder utility=new BCryptPasswordEncoder();
		/*utility.initializeConnection(true,null,null,null,
				null,null,null,
		null);*/
		System.out.println(utility.encode("tester123"));
	}
	public void initializeConnection(boolean useGoogleConnector, String cloudUrl, String localUrl, String database,
			String instance, String username, String password, String socketFactory) throws Exception {
		/*
		try {
			String jdbcUrl = null;
			//	Class.forName("com.mysql.cj.jdbc.Driver");
			if (useGoogleConnector) {
			//	Class.forName("com.mysql.jdbc.Driver");
				//jdbcUrl = String.format(cloudUrl,database,instance,socketFactory,username,password);
				connection = DriverManager.getConnection(cloudUrl);
		} else {
				Class.forName("com.mysql.cj.jdbc.Driver");
				jdbcUrl = String.format(localUrl, database, instance, socketFactory);
				connection = DriverManager.getConnection(jdbcUrl, username, password);
		}
		*/
		 String dbName = "notification";
		  String userName = "was_prod";
		  String password2 = "was_prod";
		  String hostname = "notification-prod.cjsfifl0htcj.us-east-1.rds.amazonaws.com";
		  String port = "3306";
		  String jdbcUrl = "jdbc:mysql://" + hostname + ":" +
		    port + "/" + dbName + "?user=" + userName + "&password=" + password2;
		 try {
			    System.out.println("Loading driver...");
			    Class.forName("com.mysql.jdbc.Driver");
			    System.out.println("Driver loaded!");
			  } catch (ClassNotFoundException e) {
			    throw new RuntimeException("Cannot find the driver in the classpath!", e);
			  }

			  try {
			    // Create connection to RDS DB instance
				  connection = DriverManager.getConnection(jdbcUrl);
			System.out.println("Class Loaded");
			System.out.println("Got the connection--" + connection);
			jdbcTemplate = new JdbcTemplate(new SingleConnectionDataSource(connection, false), false);
			System.out.println("Template created successfully");
			
		
		} catch (SQLException e) {
			throw e;
		}
	}

	public JdbcTemplate geJdbcTemplate() {
		return jdbcTemplate;
	}

	public Connection getConnection() {
		return connection;
	}

	/*
	public static void main(String[] args) {
		boolean useGoogleConnector=true;
		String cloudUrl="jdbc:mysql://google/%s?cloudSqlInstance=%s&socketFactory=%s";
		String localUrl="jdbc:mysql://130.211.246.172:3306/%s";
		String database="sms";
		String instance="reference-bee-171703:asia-east1:automobile";
		String username="was";
		String password="was"; 
		String socketFactory="com.google.cloud.sql.mysql.SocketFactory";
		DBUtility dbu=new DBUtility();
		dbu.initializeConnection(useGoogleConnector, cloudUrl, localUrl, database, instance, username, password, socketFactory);
		
	}
	*/
	/*public static void main(String[] args) {
		
		 String dbName = "notification";
		  String userName = "nitinbohra89";
		  String password = "nitinbohra89";
		  String hostname = "notification-db-instance.csduj5ehhpyy.ap-south-1.rds.amazonaws.com";
		  String port = "3306";
		  String jdbcUrl = "jdbc:mysql://" + hostname + ":" +
		    port + "/" + dbName + "?user=" + userName + "&password=" + password;
		 try {
			    System.out.println("Loading driver...");
			    Class.forName("com.mysql.jdbc.Driver");
			    System.out.println("Driver loaded!");
			  } catch (ClassNotFoundException e) {
			    throw new RuntimeException("Cannot find the driver in the classpath!", e);
			  }

			  Connection conn = null;
			  Statement setupStatement = null;
			  Statement readStatement = null;
			  ResultSet resultSet = null;
			  String results = "";
			  int numresults = 0;
			  String statement = null;

			  try {
			    // Create connection to RDS DB instance
			    conn = DriverManager.getConnection(jdbcUrl);
			    
			  }catch (SQLException ex) {
	    // Handle any errors
	    System.out.println("SQLException: " + ex.getMessage());
	    System.out.println("SQLState: " + ex.getSQLState());
	    System.out.println("VendorError: " + ex.getErrorCode());
	  } finally {
	       System.out.println("Closing the connection.");
	      if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
	  }
	}*/
	
}
// gcloud auth application-default login command for proxy
// C:\Users\bohra\AppData\Local\Google\Cloud SDK>