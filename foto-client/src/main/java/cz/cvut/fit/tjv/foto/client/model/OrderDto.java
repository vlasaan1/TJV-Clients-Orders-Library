package cz.cvut.fit.tjv.foto.client.model;

import java.util.Collection;

public class OrderDto {

    private Long id;
    private String date;
    private Long cost;
    private Long author;
    private String message;
    private Collection<Long> photographers;

    public OrderDto(Long id, String date, Long cost, Long author, String message, Collection<Long> photographers) {
        this.id=id;
        this.author=author;
        this.cost=cost;
        this.date=date;
        this.message=message;
        this.photographers=photographers;
    }
    public OrderDto() {
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

    public String getMessage() {
        return message;
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Collection<Long> getPhotographers() {
        return photographers;
    }

    public void setPhotographers(Collection<Long> photographers) {
        this.photographers = photographers;
    }
}
