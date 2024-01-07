package cz.cvut.fit.tjv.foto.controller;

import cz.cvut.fit.tjv.foto.domain.Customer;
import cz.cvut.fit.tjv.foto.domain.Order;
import cz.cvut.fit.tjv.foto.domain.OrderDto;
import cz.cvut.fit.tjv.foto.domain.Photographer;
import cz.cvut.fit.tjv.foto.service.CustomerService;
import cz.cvut.fit.tjv.foto.service.OrderService;
import cz.cvut.fit.tjv.foto.service.PhotographerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
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
    @Parameter(name = "author", description = "return orders from the specific customer")
    @Parameter(name = "min", description = "return orders with cost bigger than or equal to parameter")
    @Parameter(name = "max", description = "return orders with cost lower than or equal to parameter")
    @Operation(description = "returns orders from an author. If author is not provided but cost range is, it returns all orders in that cost range, or all orders if no parameter is given")
    @ApiResponses(value = { @ApiResponse( content = {
            @Content( mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Order.class)))})})
    public Iterable<Order> readAll(@RequestParam Optional<Long> author, @RequestParam Optional<Long> min, @RequestParam Optional<Long> max){
        Long minValue= Long.valueOf(Integer.MIN_VALUE), maxValue= Long.valueOf(Integer.MAX_VALUE);
        if(min.isPresent()){ minValue=min.get();}
        if(max.isPresent()){ maxValue=max.get();}
        if (author.isPresent())
            return orderService.readAllByAuthorId(author.get());
        return orderService.readAllByCostBetween(minValue, maxValue);
    }

    @PostMapping
    @Operation(description = "create new order")
    @Parameter(description = "data of an order that is supposed to be created")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "409", description = "duplicate order", content = @Content),
            @ApiResponse(responseCode = "404", description = "order cannot be created, author or photographer do not exist", content=@Content)
    })
    public Order create(@RequestBody OrderDto dto){
        dto.setId(0L);
        Optional<Customer> author = customerService.readById(dto.getAuthor());
        Collection <Photographer> photographers = new ArrayList<>();
        if(author.isEmpty() || dto.getPhotographers().isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        for(Long id : dto.getPhotographers()){
            Optional<Photographer> photographer = photographerService.readById(id);
            if (photographer.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            else photographers.add(photographer.get());
        }
        Order data = new Order(dto.getId(), dto.getCost(), dto.getDate(), dto.getMessage(), author.get(), photographers);
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
    @Parameter(description = "id of order that is supposed to be changed")
    @Parameter(description = "new data of the order that is supposed to be changed")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "order with given id does not exist", content=@Content),
            @ApiResponse(responseCode = "409", description = "incorrect id - should equal id before change", content=@Content)
    })
    public void change(@PathVariable Long id, @RequestBody OrderDto dto){
        Optional<Customer> author = customerService.readById(dto.getAuthor());
        Collection <Photographer> photographers = new ArrayList<>();
        if(author.isEmpty() || dto.getPhotographers().isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        for(Long iden : dto.getPhotographers()){
            Optional<Photographer> photographer = photographerService.readById(iden);
            if (photographer.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            else photographers.add(photographer.get());
        }
        Order data = new Order(dto.getId(), dto.getCost(), dto.getDate(), dto.getMessage(), author.get(), photographers);
        try{
            orderService.update(id, data);
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(description = "delete an order with specific id")
    @Parameter(description = "id of order that is supposed to be deleted")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id ){
        orderService.deleteById(id);
    }

}
