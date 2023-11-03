package cz.cvut.fit.tjv.foto.service;

import cz.cvut.fit.tjv.foto.domain.Order;

import java.util.Collection;

public interface OrderService extends CrudService<Order, Long> {
        Collection<Order> readAllByAuthor(String userId);
        void createOrder(long orderId, String who) throws AuthorCannotCreateNewOrder;

    }
