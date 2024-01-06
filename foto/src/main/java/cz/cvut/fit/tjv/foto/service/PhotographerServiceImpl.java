package cz.cvut.fit.tjv.foto.service;

import cz.cvut.fit.tjv.foto.domain.Order;
import cz.cvut.fit.tjv.foto.domain.Photographer;
import cz.cvut.fit.tjv.foto.repository.OrderRepository;
import cz.cvut.fit.tjv.foto.repository.PhotographerRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class PhotographerServiceImpl extends CrudServiceImpl<Photographer, Long> implements PhotographerService{

    private final PhotographerRepository photographerRepository;
    private final OrderRepository orderRepository;

    public PhotographerServiceImpl(PhotographerRepository photographerRepository, OrderRepository orderRepository) {
        this.photographerRepository = photographerRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    protected CrudRepository<Photographer, Long> getRepository() {
        return photographerRepository;
    }

    @Override
    public void deleteById(Long id) {
        Iterable<Order> orders = orderRepository.findByPhotographers(id);
        for(Order order : orders) {
            throw new IllegalArgumentException();
        }
        photographerRepository.deleteById(id);
    }
}