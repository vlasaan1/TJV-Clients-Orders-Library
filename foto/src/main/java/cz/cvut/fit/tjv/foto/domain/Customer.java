package cz.cvut.fit.tjv.foto.domain;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.HashSet;

@Entity
//@Table(name = "customers")
public class Customer implements EntityWithId<Long> {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    //unsure about this attribute
    private String phoneNumber;
    @OneToMany(mappedBy = "author")
    private Collection<Order> myOrders = new HashSet<>();
    @ManyToMany(mappedBy = "orderedBy")
    private Collection<Order> orderedByMe= new HashSet<>();


    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof Customer u)
            return id == null ? id == u.id : id.equals(u.id);
        return false;
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
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

    public Collection<Order> getOrderedByMe() {
        return orderedByMe;
    }

    public void setOrderedByMe(Collection<Order> orderedByMe) {
        this.orderedByMe = orderedByMe;
    }

}
