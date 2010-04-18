/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.example.customerservice.client;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.example.customerservice.model.Customer;
import com.example.customerservice.service.CustomerService;
import com.example.customerservice.service.NoSuchCustomerException;

public final class CustomerServiceSpringClient {

	private CustomerServiceSpringClient() {
	}

	public static void main(String args[]) throws Exception {
		// Initialize the spring context and fetch our test client
		final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:com/example/customerservice/client/applicationContext.xml");

		CustomerService bean = context.getBean(CustomerService.class);

		for (String arg : args)

		{
			try {
				List<Customer> customersByName = bean.getCustomersByName(arg);

				for (Customer customer : customersByName) {
					System.out.println("Found customer with id ["
							+ customer.getCustomerId() + "].");
				}
			} catch (NoSuchCustomerException nscex) {
				System.err.println("Could not find any customers named [" + arg
						+ "].");

			}
		}

		System.exit(0);
	}
}
