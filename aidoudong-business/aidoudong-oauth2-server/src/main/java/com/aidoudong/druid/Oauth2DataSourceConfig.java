package com.aidoudong.druid;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;

@Configuration
@MapperScan(basePackages = "com.aidoudong.dao.oauth2.mapper", sqlSessionTemplateRef = "oauth2SqlSessionTemplate")
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryOauth2",
        transactionManagerRef = "transactionManagerOauth2",
        basePackages = {"com.aidoudong.dao.oauth2.repository"}
)
public class Oauth2DataSourceConfig {
	
	@Autowired
	private PaginationInterceptor paginationInterceptor;
	
	@Primary
	@Bean(name = "oauth2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.oauth2")
    public DataSource oauth2DataSource() {
		return DruidDataSourceBuilder.create().build();
    }
	
	//==================================↓↓↓是jpa↓↓↓==================================
	
	//jpa其他参数配置
    @Autowired
    private JpaProperties jpaProperties;
	
    @Primary
    @Bean(name = "entityManagerOauth2")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryOauth2(builder).getObject().createEntityManager();
    }

    @Primary
    @Bean(name = "entityManagerFactoryOauth2")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryOauth2(EntityManagerFactoryBuilder builder) {
        return builder
                //设置数据源
                .dataSource(oauth2DataSource())
                //设置数据源属性
                .properties(jpaProperties.getHibernateProperties(new HibernateSettings()))
                //设置实体类所在位置.扫描所有带有 @Entity 注解的类
                .packages("com.aidoudong.entity.oauth2")
                // Spring会将EntityManagerFactory注入到Repository之中.有了 EntityManagerFactory之后,
                // Repository就能用它来创建 EntityManager 了,然后Entity就可以针对数据库执行操作
                .persistenceUnit("oauth2PersistenceUnit")
                .build();
    }

    @Primary
    @Bean(name = "transactionManagerOauth2")
    PlatformTransactionManager transactionManagerOauth2(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryOauth2(builder).getObject());
    }
	
	//==================================↓↓↓是mybatis↓↓↓==================================

    @Primary
    @Bean(name = "Oauth2TransactionManager")
    public DataSourceTransactionManager setTransactionManager(@Qualifier("oauth2DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
 
    @Primary
    @Bean(name = "oauth2SqlSessionFactory")
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("oauth2DataSource") DataSource dataSource) throws Exception {
//    	mybatis原生方法
//      SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//  	mybatis-plus方法 ↓↓↓
	  	MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
	  	Interceptor[] plugins = {paginationInterceptor};
	  	bean.setPlugins(plugins);
//  	mybatis-plus方法 ↑↑↑
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/**.xml"));
        return bean.getObject();
    }
 
    @Primary
    @Bean(name = "oauth2SqlSessionTemplate")
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("oauth2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
	
	
	
}
