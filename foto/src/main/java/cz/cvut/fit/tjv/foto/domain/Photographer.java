package cz.cvut.fit.tjv.foto.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class Photographer {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String phoneNumber;

}
