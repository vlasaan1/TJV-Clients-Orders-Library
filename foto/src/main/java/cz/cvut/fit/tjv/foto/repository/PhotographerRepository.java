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
    Optional<Collection<Photographer>> findAllById(Collection<Photographer> photographers);

    //Collection<Photographer> findById(Long photographerId);

    @Modifying
    @Transactional
    @Query("UPDATE Photographer p SET p.sessions = :updatedSessions WHERE p = :photographer")
    void updatePhotographerSessions(    @Param("photographer") Photographer photographer,
                                        @Param("updatedSessions") Collection<Order> updatedSessions
    );

}
