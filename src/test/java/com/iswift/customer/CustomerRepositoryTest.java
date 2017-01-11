package com.iswift.customer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    @Test
    public void findByFirstNameAndLastName_returnsEntry_afterSave() {
        Customer customer = new Customer();
        customer.setLastName("Martin");
        customer.setFirstName("Bob");
        customer.setId(1);
        customerRepository.save(customer);

        List<Customer> customers = customerRepository.findByFirstNameAndLastName("Bob", "Martin");

        assertThat(customers).contains(customer);
    }

    @Test
    public void findByFirstNameAndLastName_returnsEmptyList_whenNoEntrySaved() {
        List<Customer> customersInDb = customerRepository.findByFirstNameAndLastName("Not", "Here");

        assertThat(customersInDb).isEmpty();
    }
}