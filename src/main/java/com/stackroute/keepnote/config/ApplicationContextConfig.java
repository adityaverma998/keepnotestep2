package com.stackroute.keepnote.config;

import java.util.Properties;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@Configuration
@EnableTransactionManagement
public class ApplicationContextConfig {
	
	@Bean
	public DataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		/*dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/iniyalDB?createDatabaseIfNotExist=true&"
				+ "verifyServerCertificate=false&useSSL=false&requireSSL=false");
		dataSource.setUsername("root");
		dataSource.setPassword("aditya.verma998");*/
		
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://" + System.getenv("MYSQL_HOST") + ":3306/" + System.getenv("MYSQL_DATABASE")
				+"?verifyServerCertificate=false&useSSL=false&requireSSL=false");
		dataSource.setUsername(System.getenv("MYSQL_USER"));
		dataSource.setPassword(System.getenv("MYSQL_PASSWORD"));

		
		return dataSource;
	}
	
	@Bean
	public SessionFactory getSessionFactory(DataSource dataSource) {
		LocalSessionFactoryBuilder sfBuilder = new LocalSessionFactoryBuilder(dataSource);
		sfBuilder.scanPackages("com.stackroute.keepnote.model");
		sfBuilder.addProperties(getHibernateProperties());
		return sfBuilder.buildSessionFactory();
	}
	
	private Properties getHibernateProperties() {
		Properties prop = new Properties();
		prop.put("hibernate.dialect","org.hibernate.dialect.MySQL5InnoDBDialect");
		prop.put("hibernate.show_sql","true");
		prop.put("hibernate.format_sql","true");
		prop.put("hibernate.hbm2ddl.auto","create");
		return prop;
	}
	
	
	@Bean
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager htm = new HibernateTransactionManager(sessionFactory);
		return htm;
	}
	
	
	@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver ivr = new InternalResourceViewResolver();
		ivr.setPrefix("/WEB-INF/views/");
		ivr.setSuffix(".jsp");
		return ivr;
	}
	
	


	
}
