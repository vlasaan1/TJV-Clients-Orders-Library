package cz.cvut.fit.tjv.foto.controller;

import cz.cvut.fit.tjv.foto.domain.Customer;
import cz.cvut.fit.tjv.foto.domain.Order;
import cz.cvut.fit.tjv.foto.domain.Photographer;
import cz.cvut.fit.tjv.foto.service.CustomerService;
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

import java.util.Optional;

@RestController
@RequestMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @Operation(description = "return all customers")
    @ApiResponses(value = { @ApiResponse( content = {
            @Content( mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Order.class)))})})
    public Iterable<Customer> readAll() {
        return customerService.readAll();
    }

    @PostMapping
    @Operation(description = "register new customer")
    @Parameter(description = "data of customer that is supposed to be created")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "409", description = "duplicate customer", content = @Content),
    })
    public Customer create(@RequestBody Customer data) {
        try {
            return customerService.create(data);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}")
    @Operation(description = "get customer with specific id")
    @Parameter(description = "id of customer that is supposed to be returned")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "customer with given id does not exist", content=@Content)
    })
    public Customer readById(@PathVariable Long id) {
        Optional<Customer> found = customerService.readById(id);
        if(found.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return found.get();
    }




    @PutMapping("/{id}")
    @Operation(description = "change info about customer with specific id")
    @Parameter(description = "id of customer that is supposed to be changed")
    @Parameter(description = "new data of the customer that is supposed to be changed")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "photographer with given id does not exist", content=@Content),
            @ApiResponse(responseCode = "409", description = "incorrect id - should equal id before change", content=@Content)
    })
    public void update(@PathVariable Long id, @RequestBody Customer data) {
        try{
            customerService.update(id, data);
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

    }

    @DeleteMapping("/{id}")
    @Operation(description = "delete a customer with specific id")
    @Parameter(description = "id of customer that is supposed to be deleted")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id ){
        customerService.deleteById(id);
    }


}
