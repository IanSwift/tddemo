package com.iswift.customer;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    private CustomerRepository mockCustomerRepository;
    private CustomerService customerService;

    @Before
    public void setup() {
        mockCustomerRepository = mock(CustomerRepository.class);
        customerService = new CustomerService(mockCustomerRepository);
    }

    @Test
    public void addCustomer_savesCustomerToRepository() throws ConflictExcpetion {
        Customer customer = new Customer();

        customerService.addCustomer(customer);

        verify(mockCustomerRepository).save(customer);
    }

    @Test(expected = ConflictExcpetion.class)
    public void addCustomer_throwsConflictException_whenFirstNameAndLastNameAlreadyExist() throws ConflictExcpetion {
        when(mockCustomerRepository.findByFirstNameAndLastName("Bob", "Martin"))
                .thenReturn(Collections.singletonList(new Customer()));

        Customer customer = new Customer();
        customer.setFirstName("Bob");
        customer.setLastName("Martin");

        customerService.addCustomer(customer);
    }
}