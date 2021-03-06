/*
 * Created on 4 mar 2017 ( Time 18:53:36 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.demo;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * Jpa configuration.
 * @author Telosys Tools Generator
 *
 */
@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class JpaRepositoryConfig {
	//private final Driver driver = new Driver();
		private final static String databaseURL = "jdbc:mysql://localhost:3306/testjpadb";
		private final static String username = "root";
		private final static String password = "root";
		private final Database databaseVendor = Database.MYSQL;

		/**
		 * DataSource declaration.
		 * 
		 * @return the datasource
		 */
		@Bean
		public DataSource dataSource() {
			DataSource dataSource = getMySQLDataSource();//new SimpleDriverDataSource(this.driver, this.databaseURL, this.username, this.password);
			return dataSource;
		}

		public static DataSource getMySQLDataSource() {
			MysqlDataSource mysqlDS = null;
			mysqlDS = new MysqlDataSource();
			mysqlDS.setURL(databaseURL);
			mysqlDS.setUser(username);
			mysqlDS.setPassword(password);
			return mysqlDS;
		}

    /**
     * EntityManager factory.
     * @return the entitu manager factory.
     */
    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(this.databaseVendor);
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setPackagesToScan(this.getClass().getPackage().getName());
        factoryBean.setDataSource(this.dataSource());

        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }

    /**
     * The jpa dialect declaration.
     * @return  jpaDialect
     */
    @Bean
    public JpaDialect jpaDialect() {
        return new HibernateJpaDialect();
    }

    /**
     * Transaction manager declaration.
     * @return the transaction manager.
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(this.entityManagerFactory());
        return  txManager;
    }
}