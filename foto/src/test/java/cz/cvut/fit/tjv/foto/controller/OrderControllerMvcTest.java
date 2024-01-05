package cz.cvut.fit.tjv.foto.controller;

import cz.cvut.fit.tjv.foto.domain.Customer;
import cz.cvut.fit.tjv.foto.domain.Order;
import cz.cvut.fit.tjv.foto.domain.Photographer;
import cz.cvut.fit.tjv.foto.service.CustomerService;
import cz.cvut.fit.tjv.foto.service.OrderService;
import cz.cvut.fit.tjv.foto.service.PhotographerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Optional;

@WebMvcTest(OrderController.class)
public class OrderControllerMvcTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    OrderService orderService;
    @MockBean
    CustomerService customerService;
    @MockBean
    PhotographerService photographerService;

    Photographer photographer;
    Customer customer;
    Order order;


    @BeforeEach
    void setup(){
        customer= new Customer();
        customer.setId(2L);

        photographer = new Photographer();
        photographer.setId(3L);

        order = new Order();
        order.setId(1L);
        order.setAuthor(customer);
        order.setPhotographers(Collections.singleton(photographer));
    }



    @Test
    void create() throws Exception {
        var testOrder = new Order(order.getId(), order.getCost(), order.getDate(), order.getMessage(), customer, Collections.singleton(photographer));
        Mockito.when(customerService.readById(customer.getId())).thenReturn(Optional.of(customer));
        Mockito.when(photographerService.readById(photographer.getId())).thenReturn(Optional.of(photographer));
        Mockito.when(orderService.create(Mockito.any())).thenReturn(testOrder);

        Mockito.when(orderService.create(testOrder)).thenReturn(testOrder);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"id\" : 1, \n" +
                                "\"author\" : 2,\n" +
                                "\"photographer\" : 3\n" +

                                "}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers
                                .jsonPath("$.id", Matchers.is("1"))
                );
        Mockito.verify(orderService, Mockito.atLeastOnce()).create(testOrder);
    }


}
