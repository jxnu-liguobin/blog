package cn.edu.jxnu.blog.druid;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;

/**
 * 
 * @author： liguobin
 * @Description： druid数据源 sql监控 Srping监控配置
 * @时间： 2017-12-26 下午12:58:41
 * @version： V1.0
 * 
 */

@Configuration
public class DruidConfig {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DruidConfig.class);

	@Value("${spring.datasource.driverClassName}")
	private String driverClassName;
	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private String password;
	@Value("${spring.datasource.initialSize}")
	private int initialSize;
	@Value("${spring.datasource.minIdle}")
	private int minIdle;
	@Value("${spring.datasource.maxActive}")
	private int maxActive;
	@Value("${spring.datasource.maxWait}")
	private int maxWait;
	@Value("${spring.datasource.validationQuery}")
	private String validationQuery;
	@Value("${spring.datasource.testOnBorrow}")
	private boolean testOnBorrow;
	@Value("${spring.datasource.testOnReturn}")
	private boolean testOnReturn;
	@Value("${spring.datasource.testWhileIdle}")
	private boolean testWhileIdle;
	@Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;
	@Value("${spring.datasource.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;
	@Value("${spring.datasource.removeAbandoned}")
	private boolean removeAbandoned;
	@Value("${spring.datasource.removeAbandonedTimeout}")
	private int removeAbandonedTimeout;
	@Value("${spring.datasource.logAbandoned}")
	private boolean logAbandoned;
	@Value("${spring.datasource.filters}")
	private String filters;
	@Value("${spring.datasource.poolPreparedStatements}")
	private boolean poolPreparedStatements;
	@Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;
	@Value("${spring.datasource.connectionProperties}")
	private Properties connectionProperties;
	@Value("${spring.datasource.timeBetweenLogStatsMillis}")
	private Long timeBetweenLogStatsMillis;

	@Bean
	@Primary
	public DataSource dataSource() {
		DruidDataSource datasource = new DruidDataSource();
		datasource.setDriverClassName(driverClassName);
		datasource.setUrl(url);
		datasource.setUsername(username);
		datasource.setPassword(password);
		// 其它配置
		datasource.setInitialSize(initialSize);
		datasource.setTimeBetweenLogStatsMillis(timeBetweenLogStatsMillis);
		datasource.setMinIdle(minIdle);
		datasource.setMaxActive(maxActive);
		datasource.setMaxWait(maxWait);
		datasource
				.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		datasource.setValidationQuery(validationQuery);
		datasource.setTestWhileIdle(testWhileIdle);
		datasource.setTestOnBorrow(testOnBorrow);
		datasource.setTestOnReturn(testOnReturn);
		datasource.setPoolPreparedStatements(poolPreparedStatements);
		datasource.setConnectProperties(connectionProperties);
		datasource
				.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		try {
			datasource.setFilters(filters);
		} catch (SQLException e) {
			LOGGER.error("druid configuration initialization filter", e);
		}
		return datasource;
	}

	@Bean(name = "druid-stat-interceptor")
	public DruidStatInterceptor getDruidStatInterceptor() {
		DruidStatInterceptor druidStatInterceptor = new DruidStatInterceptor();
		return druidStatInterceptor;

	}

	@Bean
	/**
	 * 必须使用@Qualifier 否则注入失败
	 * @return
	 */
	@Qualifier("druid-stat-interceptor")
	public BeanNameAutoProxyCreator getBeanNameAutoProxyCreator() {
		BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
		beanNameAutoProxyCreator.setInterceptorNames("druid-stat-interceptor"); // 关联
		beanNameAutoProxyCreator.setBeanNames("*Service","*Dao");
		beanNameAutoProxyCreator.setProxyTargetClass(true);
		return beanNameAutoProxyCreator;

	}

}