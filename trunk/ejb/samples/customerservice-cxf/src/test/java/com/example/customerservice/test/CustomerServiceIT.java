package com.example.customerservice.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.tools.common.DataTypeAdapter;
import org.hisrc.hifaces20.testing.webappenvironment.WebAppEnvironment;
import org.hisrc.hifaces20.testing.webappenvironment.annotations.PropertiesWebAppEnvironmentConfig;
import org.hisrc.hifaces20.testing.webappenvironment.testing.junit4.WebAppEnvironmentRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.GenericXmlContextLoader;

import com.example.customerservice.model.Customer;
import com.example.customerservice.model.CustomerType;
import com.example.customerservice.service.CustomerService;

public class CustomerServiceIT {

	@Rule
	public MethodRule webAppEnvironmentRule = WebAppEnvironmentRule.INSTANCE;

	@PropertiesWebAppEnvironmentConfig
	public WebAppEnvironment webAppEnvironment;

	@Test
	public void checkCustomerService() throws Exception {

		final JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
		jaxWsProxyFactoryBean.setServiceClass(CustomerService.class);
		jaxWsProxyFactoryBean.setAddress(webAppEnvironment.getBaseUrl()
				+ "/CustomerServicePort");

		final CustomerService customerService = (CustomerService) jaxWsProxyFactoryBean
				.create();

		final Customer originalCustomer = new Customer();

		originalCustomer.setCustomerId(1);
		originalCustomer.setName("Scott Tiger");
		originalCustomer.getAddress().add("Hauptstr. 6");
		originalCustomer.getAddress().add("76133 Karlsruhe");
		originalCustomer.getAddress().add("Germany");
		originalCustomer.setNumOrders(15);
		originalCustomer.setRevenue(1234.56);
		originalCustomer.setTest(BigDecimal.valueOf(7890));
		originalCustomer.setBirthDate(DataTypeAdapter.parseDate("1970-01-01"));
		originalCustomer.setType(CustomerType.BUSINESS);

		customerService.updateCustomer(originalCustomer);

		final List<Customer> customers = customerService
				.getCustomersByName("Scott Tiger");

		assertEquals(1, customers.size());

		final Customer retrievedCustomer = customers.get(0);

		assertEquals(originalCustomer, retrievedCustomer);

		customerService.deleteCustomerById(retrievedCustomer.getCustomerId());

	}
}
