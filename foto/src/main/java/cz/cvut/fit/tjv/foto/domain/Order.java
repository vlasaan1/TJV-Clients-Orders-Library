package cz.cvut.fit.tjv.foto.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Order implements EntityWithId<Long> {
    @Id
    @GeneratedValue
    private Long id;
    private String date;
    private String cost;

    @ManyToMany
    private final Collection<Customer> orderedBy = new ArrayList<>();
    @ManyToOne
    private Customer author;

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

    public Collection<Customer> getOrderedBy() {
        return orderedBy;
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
