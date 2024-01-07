package cz.cvut.fit.tjv.foto.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order implements EntityWithId<Long> {
    @Id
    @GeneratedValue
    private Long id;
    private String date;
    private Long cost;
    @ManyToOne
    @JsonIgnoreProperties(value = "myOrders")
    private Customer author;
    @ManyToMany
    @JsonIgnoreProperties(value = "sessions")
    private Collection<Photographer> photographers;
    private String message;


    public Collection<Photographer> getPhotographers() {
        return photographers;
    }

    public void setPhotographers(Collection<Photographer> photographers) {
        this.photographers = photographers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public Customer getAuthor() {
        return author;
    }

    public void setAuthor(Customer author) {
        this.author = author;
    }

    public Order() {
    }
    public Order(Customer author) {
        this.author = Objects.requireNonNull(author);
    }

    public Order(Long id, Long cost, String date, String message, Customer customer, Collection<Photographer> photographers) {
        this.id=id;
        this.author=customer;
        this.cost=cost;
        this.date=date;
        this.message=message;
        this.photographers=photographers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(date, order.date) && Objects.equals(cost, order.cost) && Objects.equals(author, order.author) && Objects.equals(photographers, order.photographers) && Objects.equals(message, order.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, cost, author, photographers, message);
    }

    @Override
    public Long getId() {
        return id;
    }
}
