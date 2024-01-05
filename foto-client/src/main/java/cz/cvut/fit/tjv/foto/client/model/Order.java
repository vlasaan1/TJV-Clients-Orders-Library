package cz.cvut.fit.tjv.foto.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Collection;
import java.util.Objects;

public class Order {

    private Long id;
    private String date;
    private Long cost;
    private CustomerDto author;
    private Collection<PhotographerDto> photographers;
    private String message;
    //

    public Collection<PhotographerDto> getPhotographers() {
        return photographers;
    }

    public void setPhotographers(Collection<PhotographerDto> photographers) {
        this.photographers = photographers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public CustomerDto getAuthor() {
        return author;
    }

    public void setAuthor(CustomerDto author) {
        this.author = author;
    }


    public Order() {
    }
    public Order(CustomerDto author) {
        this.author = Objects.requireNonNull(author);
    }

    public Order(Long id, Long cost, String date, String message, CustomerDto customer, Collection<PhotographerDto> photographers) {
        this.id=id;
        this.author=customer;
        this.cost=cost;
        this.message=message;
        this.date=date;
        this.photographers=photographers;
    }

}
