package com.example.customerservice.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.apache.cxf.tools.common.DataTypeAdapter;
import org.hisrc.hifaces20.testing.webappenvironment.WebAppEnvironment;
import org.hisrc.hifaces20.testing.webappenvironment.annotations.PropertiesWebAppEnvironmentConfig;
import org.hisrc.hifaces20.testing.webappenvironment.testing.spring.WebAppEnvironmentTestExecutionListener;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.example.customerservice.model.Customer;
import com.example.customerservice.model.CustomerType;
import com.example.customerservice.service.CustomerService;
import com.example.customerservice.service.NoSuchCustomerException;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class,
		WebAppEnvironmentTestExecutionListener.class })
@ContextConfiguration("../client/applicationContext.xml")
public class CustomerServiceTest {

	private WebAppEnvironment webAppEnvironment;

	@PropertiesWebAppEnvironmentConfig
	public void setWebAppEnvironment(WebAppEnvironment webAppEnvironment) {
		this.webAppEnvironment = webAppEnvironment;
	}

	private CustomerService customerService;

	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	@Test
	public void checkCustomerServiceProvided() throws NoSuchCustomerException, InterruptedException {

		// try {
		// getCustomerService().getCustomersByName("Scott Tiger");
		// fail();
		// } catch (NoSuchCustomerException nscex) {
		// Assert.assertTrue(true);
		// }

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

		getCustomerService().updateCustomer(originalCustomer);

		final List<Customer> customers = getCustomerService()
				.getCustomersByName("Scott Tiger");

		assertEquals(1, customers.size());

		final Customer retrievedCustomer = customers.get(0);

		assertEquals(originalCustomer, retrievedCustomer);

		getCustomerService().deleteCustomerById(
				retrievedCustomer.getCustomerId());
		
		Thread.sleep(500);

		try {
			final List<Customer> retrievedCustomers = getCustomerService().getCustomersByName("Scott Tiger");
			Assert.fail();
		} catch (NoSuchCustomerException nscex) {
			Assert.assertTrue(true);
		}

	}
}
