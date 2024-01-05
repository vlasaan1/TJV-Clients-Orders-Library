package cz.cvut.fit.tjv.foto.client.service;

import cz.cvut.fit.tjv.foto.client.api_client.PhotographerClient;
import cz.cvut.fit.tjv.foto.client.model.Photographer;
import cz.cvut.fit.tjv.foto.client.model.PhotographerDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class PhotographerService {
    private final PhotographerClient photographerClient;
    private Long currentPhotographer;

    public PhotographerService(PhotographerClient photographerClient) {
        this.photographerClient = photographerClient;
    }

    public boolean isCurrentPhotographer() {
        return currentPhotographer != null;
    }

    public void setCurrentPhotographer(Long id) {
        this.currentPhotographer = id;
        photographerClient.setCurrentPhotographer(id);;
    }

    public Optional<Photographer> readOne() {
        return photographerClient.readOne();
    }

    public Collection<Photographer> readAll() {
        return photographerClient.readAll();
    }

    public void create(PhotographerDto data) {
        photographerClient.create(data);
    }

    public void update(PhotographerDto formData) { photographerClient.update(formData);}

    public void delete() { photographerClient.delete();}

}
