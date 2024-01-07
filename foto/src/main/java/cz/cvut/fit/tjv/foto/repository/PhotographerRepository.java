package cz.cvut.fit.tjv.foto.repository;

import cz.cvut.fit.tjv.foto.domain.Order;
import cz.cvut.fit.tjv.foto.domain.Photographer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PhotographerRepository extends CrudRepository<Photographer, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Photographer p WHERE :order MEMBER OF p.sessions")
    void deleteSessionsByOrder(Order order);

}
