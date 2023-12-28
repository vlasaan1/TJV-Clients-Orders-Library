package cz.cvut.fit.tjv.foto.service;

import cz.cvut.fit.tjv.foto.domain.Customer;

public interface CustomerService extends CrudService<Customer, Long> {
    void removeOrder(Long orderId);
    //void deleteFromOrderAuthor(Long productId);

}
