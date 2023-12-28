package cz.cvut.fit.tjv.foto.controller;

import cz.cvut.fit.tjv.foto.domain.Order;
import cz.cvut.fit.tjv.foto.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
/*
    @PostMapping
    @Operation(description = "create new order")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "409", description = "duplicate username", content = @Content)
    })
    public Order create(@RequestBody Order data){
        try {
            return orderService.create(data);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
    @PutMapping
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Order with given id does not exist", content=@Content),
            @ApiResponse(responseCode = "409", description = "id of given order does not match...ordercontroler", content=@Content)
    })
    public void change(@PathVariable Long id, @RequestBody Order data){
        try{
            orderService.update(id,data);
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

    }

    //@DeleteMapping
*/
}
