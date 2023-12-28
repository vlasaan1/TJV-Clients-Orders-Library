package cz.cvut.fit.tjv.foto.service;

import cz.cvut.fit.tjv.foto.domain.Photographer;
import cz.cvut.fit.tjv.foto.repository.PhotographerRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PhotographerServiceImpl extends CrudServiceImpl<Photographer, Long> implements PhotographerService{

    private PhotographerRepository photographerRepository;

    @Override
    protected CrudRepository<Photographer, Long> getRepository() {
        return photographerRepository;
    }

    @Override
    public void removeOrderFromSessions(Long orderId) {
        Iterable<Photographer> photographers = photographerRepository.findAll();
        for (Photographer photographer: photographers) {
            photographer.getSessions().removeIf(order -> order.getId().equals(orderId));
            photographerRepository.save(photographer);
        }
    }
}