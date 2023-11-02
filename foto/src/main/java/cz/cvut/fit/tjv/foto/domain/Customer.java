package cz.cvut.fit.tjv.foto.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Customer implements EntityWithId<Long> {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    //unsure about this attribute
    private String phoneNumber;



    @Override
    public Long getId() {
        return id;
    }

}
