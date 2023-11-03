package cz.cvut.fit.tjv.foto.service;

import cz.cvut.fit.tjv.foto.domain.Customer;
import cz.cvut.fit.tjv.foto.repository.CustomerRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public class CustomerServiceImpl extends CrudServiceImpl<Customer, Long> implements CustomerService {
    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    protected CrudRepository<Customer, Long> getRepository() {
        return customerRepository;
    }
}