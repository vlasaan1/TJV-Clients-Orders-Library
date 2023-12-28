package cz.cvut.fit.tjv.foto.controller;

import cz.cvut.fit.tjv.foto.domain.Customer;
import cz.cvut.fit.tjv.foto.domain.Order;
import cz.cvut.fit.tjv.foto.service.CustomerService;
import cz.cvut.fit.tjv.foto.service.OrderService;
import cz.cvut.fit.tjv.foto.service.PhotographerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    private final CustomerService customerService;
    private final PhotographerService photographerService;
    public OrderController(OrderService orderService, CustomerService customerService, PhotographerService photographerService){
        this.orderService = orderService;
        this.customerService = customerService;
        this.photographerService = photographerService;
    }

    @GetMapping
    @Operation(description = "get orders from an author or all orders if no parametr given")
    public Iterable<Order> readAllByAuthor(@RequestParam Optional<Long> author){
        if (author.isPresent())
            return orderService.readAllByAuthorId(author.get());
        else
            return orderService.readAll();
    }

    @PostMapping
    @Operation(description = "create new order")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "409", description = "duplicate order", content = @Content)
    })
    public Order create(@RequestBody Order data){
        try {
            return orderService.create(data);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}")
    @Operation(description = "get order with specific id")
    @Parameter(description = "id of order that is supposed to be returned")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "order with given id does not exist", content=@Content)
    })
    public Order readById(@PathVariable Long id) {
        Optional<Order> found = orderService.readById(id);
        if(found.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return found.get();
    }

    @PutMapping("/{id}")
    @Operation(description = "change info about order with specific id")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Order with given id does not exist", content=@Content),
            @ApiResponse(responseCode = "409", description = "incorrect id - should equal id before change  ...ordercontroler", content=@Content)
    })
    public void change(@PathVariable Long id, @RequestBody Order data){
        try{
            orderService.update(id, data);
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }


    //
    @DeleteMapping("/{id}")
    @Operation(description = "delete an order with specific id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id ){
        customerService.removeOrder(id);
        photographerService.removeOrderFromSessions(id);
        orderService.deleteById(id);
    }

}
