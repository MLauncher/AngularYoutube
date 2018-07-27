package main.java.com.brian.Java_Files.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan("main.java.com.brian.Java_Files.config.PersistenceConfig")
public class PersistenceConfig {

	@Autowired
	private Environment env;
	
	
	@Bean
	public LocalSessionFactoryBean sessionFactory()
	{
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(aDataSource());
		sessionFactory.setPackagesToScan(new String[] {"main.java.com.brian.Java_Files.hibernateFiles3"});
		sessionFactory.setHibernateProperties(hibernateProperties());
		
		return sessionFactory;
	}
	
	public DataSource aDataSource(){
		
		BasicDataSource datasource = new BasicDataSource();
		datasource.setDriverClassName("com.mysql.jdbc.Driver");
		datasource.setUrl("jdbc:mysql://localhost:3306/utube_schema");
		datasource.setUsername("root");
		datasource.setPassword("donkey64");
		return datasource;
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager transactionmanager(SessionFactory sessionFactory){
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		
		return txManager;
	}
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exception(){
		
		return new PersistenceExceptionTranslationPostProcessor();
		
	}
	
	
	Properties hibernateProperties(){
		return new Properties(){
			{
				setProperty("hibernate.hbm2ddl.auto","update");
				setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
				setProperty("hibernate.globally_quoted_identifiers", "true");
			}
		};
			
			
		}
}
	
