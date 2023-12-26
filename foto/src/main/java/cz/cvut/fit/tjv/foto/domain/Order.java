package cz.cvut.fit.tjv.foto.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Order implements EntityWithId<Long> {
    @Id
    @GeneratedValue
    private Long id;
    private String date;
    private String cost;
    @ManyToOne
    private Customer author;
    @ManyToMany
    private Collection<Photographer> photographer;

    private String message;

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

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public Customer getAuthor() {
        return author;
    }

    public void setAuthor(Customer author) {
        this.author = author;
    }






    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return Objects.equals(id, order.id);
    }

    public Order() {
    }
    public Order(Customer author) {
        this.author = Objects.requireNonNull(author);
    }


    @Override
    public Long getId() {
        return id;
    }
}
