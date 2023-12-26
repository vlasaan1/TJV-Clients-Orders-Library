package cz.cvut.fit.tjv.foto.service;

import cz.cvut.fit.tjv.foto.domain.Photographer;
import cz.cvut.fit.tjv.foto.repository.PhotographerRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public abstract class PhotographerServiceImpl extends CrudServiceImpl<Photographer, Long> implements PhotographerService{

    private PhotographerRepository photographerRepository;

    public void updatePhotographerSessions(Collection<Photographer> photographers){

    }


    @Override
    protected CrudRepository<Photographer, Long> getRepository() {
        return photographerRepository;
    }


}