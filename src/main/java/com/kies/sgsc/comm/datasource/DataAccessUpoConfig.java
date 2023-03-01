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
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(basePackages = "com.kies.sgsc.dao.upo", sqlSessionFactoryRef="SqlSessionFactoryUpo")
@EnableTransactionManagement
public class DataAccessUpoConfig {
	
	@Bean(name = "DataSourceUPO")
	@ConfigurationProperties(prefix = "spring.datasource.hikari.upo")
	public DataSource dataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}
	
	
	@Bean(name="SqlSessionFactoryUpo")
	public SqlSessionFactory SqlSessionFactoryUpo(@Qualifier("DataSourceUPO") DataSource DataSourceUPO, ApplicationContext applicationContext) throws Exception {
		
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(DataSourceUPO);
		sessionFactory.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
		sessionFactory.setMapperLocations(applicationContext.getResources("classpath:com/kies/sgsc/dao/upo/*.xml"));
		return sessionFactory.getObject();
	}
	
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
