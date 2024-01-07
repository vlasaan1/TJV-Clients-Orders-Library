package cz.cvut.fit.tjv.foto.client.service;

import cz.cvut.fit.tjv.foto.client.api_client.CustomerClient;
import cz.cvut.fit.tjv.foto.client.model.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerClient customerClient;
    private Long currentCustomer;

    public CustomerService(CustomerClient customerClient) {
        this.customerClient = customerClient;
    }

    public boolean isCurrentCustomer() {
        return currentCustomer != null;
    }

    public void setCurrentCustomer(Long id) {
        this.currentCustomer = id;
        customerClient.setCurrentCustomer(id);
    }

    public Optional<CustomerDto> readOne() {
        return customerClient.readOne();
    }

    public Collection<CustomerDto> readAll() {
        return customerClient.readAll();
    }

    public void create(CustomerDto data) {
        customerClient.create(data);
    }

    public void update(CustomerDto formData) { customerClient.update(formData);}

    public void delete() { customerClient.delete();}



}
