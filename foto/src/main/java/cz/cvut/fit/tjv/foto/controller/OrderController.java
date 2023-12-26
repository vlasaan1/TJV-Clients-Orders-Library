package cz.cvut.fit.tjv.foto.controller;

import cz.cvut.fit.tjv.foto.domain.Order;
import cz.cvut.fit.tjv.foto.service.OrderService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService){ this.orderService = orderService; }
    @GetMapping
    public Iterable<Order> readAllByAuthor(@RequestParam Optional<Long> author){
        if (author.isPresent())
            return orderService.readAllByAuthorId(author.get());
        else
            return orderService.readAll();
    }

}
