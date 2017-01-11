package com.iswift.customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    private MockMvc mockMvc;
    private CustomerService mockCustomerService;

    @Before
    public void setup() {
        mockCustomerService = mock(CustomerService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new CustomerController(mockCustomerService)).build();
    }

    @Test
    public void postCustomers_savesToService_andReturns200() throws Exception, ConflictExcpetion {
        Customer customer = new Customer();
        customer.setFirstName("Bob");
        customer.setLastName("Martin");
        customer.setAge(64);
        customer.setAddress("Somewhere in cali probably");

        ObjectMapper objectMapper = new ObjectMapper();
        String customerJson = objectMapper.writeValueAsString(customer);

        mockMvc.perform(post("/customers").contentType(MediaType.APPLICATION_JSON).content(customerJson))
                .andExpect(status().isOk());

        verify(mockCustomerService).addCustomer(customer);
    }

    @Test
    public void postCustomers_returnsConflict_whenServiceThrowsConflict() throws Exception, ConflictExcpetion {
        doThrow(ConflictExcpetion.class)
            .when(mockCustomerService).addCustomer(any());

        Customer customer = new Customer();
        customer.setFirstName("Bob");
        customer.setLastName("Martin");
        customer.setAge(64);
        customer.setAddress("Somewhere in cali probably");

        ObjectMapper objectMapper = new ObjectMapper();
        String customerJson = objectMapper.writeValueAsString(customer);


        mockMvc.perform(post("/customers").content(customerJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }
}