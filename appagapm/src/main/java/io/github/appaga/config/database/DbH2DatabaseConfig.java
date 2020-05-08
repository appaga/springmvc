package io.github.appaga.config.database;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * h2database 설정
 *
 */
@Slf4j
@Configuration
@MapperScan(basePackages =  "io.github.appaga.**"
	, annotationClass = DbH2ConnMapper.class
	, sqlSessionFactoryRef = "dbH2SessionFactory")
@EnableTransactionManagement
public class DbH2DatabaseConfig {
	@Autowired
	private Environment env;
	
	@Bean(name = "dbH2DataSource")
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public DataSource dataSource() {
		
		if(log.isDebugEnabled()) {
			log.debug("jdbc-url={}", env.getProperty("spring.datasource.hikari.jdbc-url", ""));
		}
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}
	
	@Bean(name = "dbH2SessionFactory")
	public SqlSessionFactory sessionFactory(@Qualifier("dbH2DataSource") DataSource dataSource,
			ApplicationContext applicationContext) throws Exception {
		final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		sessionFactoryBean.setVfs(SpringBootVFS.class); // Sets the SpringBootVFS class into SqlSessionFactoryBean
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sessionFactoryBean.setMapperLocations(resolver.getResources("classpath:io/github/appaga/**/*H2.xml"));
		
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		// 값이 null이어도 키는 리턴하도록 설정.
		configuration.setCallSettersOnNulls(true);
		sessionFactoryBean.setConfiguration(configuration);
		
		return sessionFactoryBean.getObject();
	}

	@Bean(name = "dbH2SessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("dbH2SessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	@Bean(name="dbH2TransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Autowired @Qualifier(value="dbH2DataSource") DataSource dataSource) {
    	DataSourceTransactionManager dstm = new DataSourceTransactionManager();
    	dstm.setDataSource(dataSource);
    	return dstm;
    }
	
	@Bean(name="dbH2DataSourceInitializer")
    public DataSourceInitializer dataSourceInitializer(@Qualifier("dbH2DataSource") DataSource datasource) {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("config/database/h2-ddl.sql"));
        resourceDatabasePopulator.addScript(new ClassPathResource("config/database/h2-init.sql"));

        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(datasource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }
}