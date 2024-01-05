package cz.cvut.fit.tjv.foto.repository;

import cz.cvut.fit.tjv.foto.domain.Customer;
import cz.cvut.fit.tjv.foto.domain.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
//    @Query("SELECT c FROM Customer c WHERE size(c.myOrders) > :customers ") //JPQL: Java Persistence Query Language
//    Collection<Customer> findByAuthor_MyOrdersGreaterThan(int customers);


}
