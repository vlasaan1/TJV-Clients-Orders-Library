package cz.cvut.fit.tjv.foto.controller;

import cz.cvut.fit.tjv.foto.domain.Customer;
import cz.cvut.fit.tjv.foto.service.CustomerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(CustomerController.class)
public class CustomerControllerMvcTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    CustomerService customerService;


    @Test
    void create() throws Exception {
        var testCustomer = new Customer();
        testCustomer.setId(1L);

        Mockito.when(customerService.create(testCustomer)).thenReturn(testCustomer);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"id\" : 1 \n" +
                                "}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers
                                .jsonPath("$.id", Matchers.is(1))
                );
        Mockito.verify(customerService, Mockito.atLeastOnce()).create(testCustomer);
    }

    @Test
    void createWithConflictException() throws Exception {
        var testCustomer = new Customer();
        testCustomer.setId(1L);

        Mockito.when(customerService.create(testCustomer))
                .thenThrow(new IllegalArgumentException(""));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"id\" : 1 \n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isConflict())  // Expect HTTP 409 Conflict
                .andExpect(MockMvcResultMatchers.content().string(""))  // No content in response body

                .andExpect(result -> {
                    Mockito.verify(customerService, Mockito.atLeastOnce()).create(testCustomer);
                });
    }

}
