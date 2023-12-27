package cz.cvut.fit.tjv.foto.service;

import cz.cvut.fit.tjv.foto.domain.Photographer;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface PhotographerService extends CrudService<Photographer, Long> {

}
