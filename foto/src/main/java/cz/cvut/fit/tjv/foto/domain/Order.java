package cz.cvut.fit.tjv.foto.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class Order implements EntityWithId<Long> {
    @Id
    @GeneratedValue
    private Long id;
    private String date;
    private String cost;


    @Override
    public Long getId() {
        return id;
    }
}
