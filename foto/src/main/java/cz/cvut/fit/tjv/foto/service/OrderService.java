package cz.cvut.fit.tjv.foto.service;

import cz.cvut.fit.tjv.foto.domain.Order;

import java.util.Collection;

public interface OrderService extends CrudService<Order, Long> {
    Collection<Order> readAllByAuthorId(Long userId);
    Collection<Order> readAllByPhotographerId(Long photographerId);

    Order createOrder(Order o) throws AuthorCannotCreateExistingOrder;

    //delete orders 
}
