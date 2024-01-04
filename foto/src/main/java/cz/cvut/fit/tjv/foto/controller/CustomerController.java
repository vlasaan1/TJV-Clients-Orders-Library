package cz.cvut.fit.tjv.foto.controller;

import cz.cvut.fit.tjv.foto.domain.Customer;
import cz.cvut.fit.tjv.foto.domain.Photographer;
import cz.cvut.fit.tjv.foto.service.CustomerService;
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
@RequestMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @Operation(description = "get all customers")
    public Iterable<Customer> readAll() {
        return customerService.readAll();
    }

    @PostMapping
    @Operation(description = "register new customer")
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody Customer data) {
        customerService.update(id, data);
        //
    }

    @DeleteMapping("/{id}")
    @Operation(description = "delete a customer with specific id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id ){
        customerService.deleteById(id);
        //
    }


}
