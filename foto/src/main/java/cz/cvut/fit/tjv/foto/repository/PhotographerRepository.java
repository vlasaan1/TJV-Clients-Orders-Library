package cz.cvut.fit.tjv.foto.repository;

import cz.cvut.fit.tjv.foto.domain.Photographer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PhotographerRepository extends CrudRepository<Photographer, Long> {

    //Collection<Photographer> findById(Long photographerId);

}
