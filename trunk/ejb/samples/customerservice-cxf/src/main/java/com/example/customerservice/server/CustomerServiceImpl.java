package com.example.customerservice.server;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.customerservice.model.Customer;
import com.example.customerservice.service.CustomerService;
import com.example.customerservice.service.NoSuchCustomer;
import com.example.customerservice.service.NoSuchCustomerException;

@Transactional
public class CustomerServiceImpl extends JpaDaoSupport implements
		CustomerService {

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteCustomerById(final Integer customerId) {

		@SuppressWarnings("unchecked")
		final List<Customer> customers = getJpaTemplate().executeFind(
				new JpaCallback<List<Customer>>() {

					@Override
					public List<Customer> doInJpa(EntityManager em)
							throws PersistenceException {
						final Query query = em
								.createQuery("SELECT c FROM Customer c WHERE c.customerId = :id");
						query.setParameter("id", customerId);
						return (List<Customer>) query.getResultList();
					}
				});

		for (Customer customer : customers) {
			getJpaTemplate().remove(customer);
		}

	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Customer> getCustomersByName(final String name)
			throws NoSuchCustomerException {
		@SuppressWarnings("unchecked")
		final List<Customer> customers = getJpaTemplate().executeFind(
				new JpaCallback<List<Customer>>() {

					@Override
					public List<Customer> doInJpa(EntityManager em)
							throws PersistenceException {

						final Query query = em
								.createQuery("SELECT c FROM Customer c WHERE c.name = :name ORDER BY c.name DESC");

						query.setParameter("name", name);
						final List resultList = query.getResultList();
						return resultList;
					}
				});

		if (customers.isEmpty()) {
			NoSuchCustomer noSuchCustomer = new NoSuchCustomer();
			noSuchCustomer.setCustomerName(name);
			throw new NoSuchCustomerException(
					"Did not find any matching customer for name=" + name,
					noSuchCustomer);

		}
		return customers;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateCustomer(Customer customer) {
		getJpaTemplate().merge(customer);
	}

}
