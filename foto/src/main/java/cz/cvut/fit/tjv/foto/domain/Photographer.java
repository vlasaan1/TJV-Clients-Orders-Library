package cz.cvut.fit.tjv.foto.domain;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
public class Photographer {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String phoneNumber;

//set objednavek - dny
    @ManyToMany(mappedBy = "photographer")
    private Collection<Order> sessions;

    public Long getId() { return id; }
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
}
