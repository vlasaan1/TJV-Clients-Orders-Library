package cz.cvut.fit.tjv.foto.client.model;

import java.util.Collection;

public class Photographer {
    private Long id;
    private String name;
    private String phoneNumber;
    private Collection<Order> sessions;

    public Photographer(Long id, String name, String phoneNumber, Collection<Order> sessions) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.sessions = sessions;
    }

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

    public Collection<Order> getSessions() {
        return sessions;
    }

    public void setSessions(Collection<Order> sessions) {
        this.sessions = sessions;
    }
}
