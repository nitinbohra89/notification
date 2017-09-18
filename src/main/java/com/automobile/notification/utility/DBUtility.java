package com.automobile.notification.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

@Scope("singleton")
public class DBUtility {

	private JdbcTemplate jdbcTemplate=null;
	private Connection connection=null;
	public void initializeConnection(boolean useGoogleConnector, String cloudUrl, String localUrl, String database,
			String instance, String username, String password, String socketFactory) throws Exception {

		try {
			String jdbcUrl = null;
			//	Class.forName("com.mysql.cj.jdbc.Driver");
			if (useGoogleConnector) {
				Class.forName("com.mysql.jdbc.GoogleDriver");
				//jdbcUrl = String.format(cloudUrl,database,instance,socketFactory,username,password);
				connection = DriverManager.getConnection(cloudUrl);
		} else {
			//	Class.forName("com.mysql.cj.jdbc.Driver");
				jdbcUrl = String.format(localUrl, database, instance, socketFactory);
				connection = DriverManager.getConnection(jdbcUrl, username, password);
		}
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

	
	/*public static void main(String[] args) {
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
		
	}*/
	
	
	
}
// gcloud auth application-default login command for proxy
// C:\Users\bohra\AppData\Local\Google\Cloud SDK>