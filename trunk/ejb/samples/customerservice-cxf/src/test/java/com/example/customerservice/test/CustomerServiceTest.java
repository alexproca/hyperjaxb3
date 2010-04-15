package com.example.customerservice.test;

import static org.junit.Assert.assertNotNull;

import javax.annotation.Resource;

import org.hisrc.hifaces20.testing.webappenvironment.WebAppEnvironment;
import org.hisrc.hifaces20.testing.webappenvironment.annotations.PropertiesWebAppEnvironmentConfig;
import org.hisrc.hifaces20.testing.webappenvironment.testing.spring.WebAppEnvironmentTestExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.example.customerservice.CustomerService;
import com.example.customerservice.NoSuchCustomerException;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class,
		WebAppEnvironmentTestExecutionListener.class })
@ContextConfiguration("../client/applicationContext.xml")
public class CustomerServiceTest /* extends AbstractJUnit4SpringContextTests */{

	private WebAppEnvironment webAppEnvironment;

	@PropertiesWebAppEnvironmentConfig
	public void setWebAppEnvironment(WebAppEnvironment webAppEnvironment) {
		this.webAppEnvironment = webAppEnvironment;
	}

	private CustomerService customerService;

	@Resource
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	@Test
	public void checkCustomerServiceProvided() throws NoSuchCustomerException {
		// assertNotNull(applicationContext);
		// final Object bean = applicationContext.getBean("customerService");
		assertNotNull(customerService);
		
		final CustomerServiceTester tester = new CustomerServiceTester();
		tester.setCustomerService(customerService);
		tester.testCustomerService();
	}
}
