package com.kies.sgsc.comm.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(basePackages = "com.kies.sgsc.dao.base")
public class DataAccessConfig {

	
	@Primary
	@Bean(name="masterDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.hikari.main")
	public DataSource masterDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Primary
	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("masterDataSource")DataSource masterDataSource,ApplicationContext applicationContext) throws Exception {
		
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(masterDataSource);
		sessionFactory.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
		sessionFactory.setMapperLocations(applicationContext.getResources("classpath:com/kies/sgsc/dao/base/*.xml"));
		return sessionFactory.getObject();
	}
	
	@Primary 
	@Bean(name="sqlSessionFactory")
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
