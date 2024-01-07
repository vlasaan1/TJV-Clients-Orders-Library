package cz.cvut.fit.tjv.foto.client.service;

import cz.cvut.fit.tjv.foto.client.api_client.OrderClient;
import cz.cvut.fit.tjv.foto.client.model.Order;
import cz.cvut.fit.tjv.foto.client.model.OrderDto;
import cz.cvut.fit.tjv.foto.client.model.Range;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderClient orderClient;
    private Long currentOrder;

    public OrderService(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    public boolean isCurrentOrder() {
        return currentOrder != null;
    }

    public void setCurrentOrder(Long id) {
        this.currentOrder = id;
        orderClient.setCurrentOrder(id);
    }

    public Optional<Order> readOne() {
        return orderClient.readOne();
    }
    public Collection<Order> readAll(Range range) {
        return orderClient.readAll(range);
    }

    public void create(OrderDto data) {
        orderClient.create(data);
    }

    public void update(OrderDto formData) { orderClient.update(formData);}

    public void delete() { orderClient.delete();}


}
