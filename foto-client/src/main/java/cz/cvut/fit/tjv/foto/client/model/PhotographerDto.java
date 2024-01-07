package cz.cvut.fit.tjv.foto.client.model;

import java.util.Collection;

public class PhotographerDto {
    private Long id;
    private String name;
    private String phoneNumber;
    private Collection<String> sessions;


    public PhotographerDto(Long id, String name, String phoneNumber, Collection<String> sessions) {
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

    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public Collection<String> getSessions() {
        return sessions;
    }

    public void setSessions(Collection<String> sessions) {
        this.sessions = sessions;
    }
}
