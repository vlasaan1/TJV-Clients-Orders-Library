package cz.cvut.fit.tjv.foto.domain;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
//@Table(name = "customers")
public class Customer implements EntityWithId<Long> {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    //unsure about this attribute
    private String phoneNumber;
    @OneToMany(mappedBy = "author", orphanRemoval = true)
    private Collection<Order> myOrders = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Collection<Order> getMyOrders() {
        return myOrders;
    }

    public void setMyOrders(Collection<Order> myOrders) {
        this.myOrders = myOrders;
    }


}
