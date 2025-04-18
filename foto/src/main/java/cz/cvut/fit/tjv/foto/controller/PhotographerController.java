package cz.cvut.fit.tjv.foto.controller;

import cz.cvut.fit.tjv.foto.domain.Customer;
import cz.cvut.fit.tjv.foto.domain.Order;
import cz.cvut.fit.tjv.foto.domain.Photographer;
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

import java.util.Optional;

@RestController
@RequestMapping(value = "/photographer", produces = MediaType.APPLICATION_JSON_VALUE)
public class PhotographerController {
    private final PhotographerService photographerService;

    public PhotographerController(PhotographerService photographerService){this.photographerService=photographerService;}

    @GetMapping
    @Operation(description = "return all photographers")
    @ApiResponses(value = { @ApiResponse( content = {
            @Content( mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Order.class)))})})
    public Iterable<Photographer> readAll() {
            return photographerService.readAll();
    }

    @PostMapping
    @Operation(description = "register new photographer")
    @Parameter(description = "data of a photographer that is supposed to be created")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "409", description = "duplicate customer cannot be created", content = @Content),
    })
    public Photographer create(@RequestBody Photographer data) {
        try {
            return photographerService.create(data);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }


    @GetMapping("/{id}")
    @Operation(description = "get photographer with specific id")
    @Parameter(description = "id of photographer that is supposed to be returned")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "photographer with given id does not exist", content=@Content)
    })
    public Photographer readById(@PathVariable Long id) {
        Optional<Photographer> found = photographerService.readById(id);
        if(found.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return found.get();
    }

    @PutMapping("/{id}")
    @Operation(description = "change info about photographer with specific id")
    @Parameter(description = "id of photographer that should be changed")
    @Parameter(description = "new data of the photographer that is supposed to be changed")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "photographer with given id does not exist", content=@Content),
            @ApiResponse(responseCode = "409", description = "incorrect id - should equal id before change", content=@Content)
    })
    public void update(@PathVariable Long id, @RequestBody Photographer data) {
        try{
            photographerService.update(id, data);
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

    }

    @DeleteMapping("/{id}")
    @Operation(description = "delete a photographer with specific id")
    @Parameter(description = "id of product that should be deleted")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "cannot delete photographer with orders", content=@Content),
    })
    public void delete(@PathVariable Long id ){
        try {
            photographerService.deleteById(id);
        }
        catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
