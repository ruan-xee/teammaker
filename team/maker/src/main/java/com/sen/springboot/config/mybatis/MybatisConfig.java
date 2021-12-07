package com.sen.springboot.config.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
//必须满足两个条件的存在
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
public class MybatisConfig {

    @Configuration
    @MapperScan(basePackages = "com.sen.springboot.mapper",
            sqlSessionFactoryRef = "productSqlSessionFactory")
    public static class ProductMybatisConfig {
        @Bean
        @Primary
        @ConfigurationProperties(prefix = "spring.datasource.product")
        public DataSource ProductDataSource() {
            return DataSourceBuilder.create().build();
        }

        @Bean(name = "productSqlSessionFactory")
        @Primary
        public SqlSessionFactory productSqlSessionFactory(@Qualifier("ProductDataSource") DataSource datasource)
                throws Exception {
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
            bean.setDataSource(datasource);
            //这一步必须加上，不然会找不到mapper.xml文件
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().
                    getResources("classpath:/mapper/**/*.xml"));
            return bean.getObject();
        }

        @Bean(name = "ProductSqlSessionTemplate")
        // 表示这个数据源是默认数据源
        @Primary
        public SqlSessionTemplate productSqlSessionTemplate(
                @Qualifier("productSqlSessionFactory") SqlSessionFactory sessionfactory) {
            return new SqlSessionTemplate(sessionfactory);
        }

        @Bean(name = "productTransactionManager")
        public DataSourceTransactionManager productTransactionManager(@Qualifier("ProductDataSource") DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }
    /**
     * 测试数据库
     * **/
//    @Configuration
//    @MapperScan(basePackages = "com.mason.spring.wj.mapper.test",
//            sqlSessionFactoryRef = "testSqlSessionFactory")
//    public static class TestMybatisConfig {
//        @Bean
//        @ConfigurationProperties(prefix = "spring.datasource.test")
//        public DataSource TestDataSource() {
//            return DataSourceBuilder.create().build();
//        }
//
//        @Bean(name = "testSqlSessionFactory")
//        public SqlSessionFactory testSqlSessionFactory(@Qualifier("TestDataSource") DataSource datasource)
//                throws Exception {
//            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//            bean.setDataSource(datasource);
//            //这一步必须加上，不然会找不到mapper.xml文件
//            bean.setMapperLocations(new PathMatchingResourcePatternResolver().
//                    getResources("classpath:/mapper/test/**/*.xml"));
//            return bean.getObject();
//        }
//
//        @Bean(name = "testSqlSessionTemplate")
//        // 表示这个数据源是默认数据源
//        public SqlSessionTemplate testSqlSessionTemplate(
//                @Qualifier("testSqlSessionFactory") SqlSessionFactory sessionfactory) {
//            return new SqlSessionTemplate(sessionfactory);
//        }
//
//        @Bean(name = "testTransactionManager")
//        public DataSourceTransactionManager testTransactionManager(@Qualifier("TestDataSource") DataSource dataSource) {
//            return new DataSourceTransactionManager(dataSource);
//        }
//    }
}
