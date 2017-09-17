package com.automobile.notification.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.automobile.notification.utility.DBUtility;

@Configuration
@PropertySource("classpath:database.properties")
public class DataBaseConfig extends WebMvcConfigurerAdapter {
	@Value("${mysql.use.google.connector}")
	private boolean useGoogleConnector;

	@Value("${google.mysql.cloud.connection.url}")
	private String mysqlCloudUrl;

	@Value("${google.mysql.local.connection.url}")
	private String mysqlLocalUrl;
	
	@Value("${google.mysql.socketfactory}")
	private String mysqlSocketFactory;

	@Value("${google.mysql.database}")
	private String database;

	@Value("${google.mysql.instance}")
	private String instance;

	@Value("${google.mysql.username}")
	private String username;

	@Value("${google.mysql.password}")
	private String password;

	@Bean
	public DBUtility initilalize() throws Exception {

		DBUtility dbUtility = new DBUtility();
		dbUtility.initializeConnection(useGoogleConnector,mysqlCloudUrl,mysqlLocalUrl, database, instance, username, password, mysqlSocketFactory);
		return dbUtility;
	}
    
}
