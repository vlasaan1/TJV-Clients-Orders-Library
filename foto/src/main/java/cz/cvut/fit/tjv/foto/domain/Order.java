package cz.cvut.fit.tjv.foto.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.util.ArrayList;
import java.util.Collection;

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

    @Override
    public Long getId() {
        return id;
    }
}
