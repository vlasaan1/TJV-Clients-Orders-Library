package cz.cvut.fit.tjv.foto.repository;

import cz.cvut.fit.tjv.foto.domain.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    Collection<Order> findByAuthorId(Long authorId);

}
