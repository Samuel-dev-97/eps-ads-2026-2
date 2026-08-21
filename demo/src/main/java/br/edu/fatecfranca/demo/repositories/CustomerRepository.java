package br.edu.fatecfranca.demo.repositories;


import org.springframework.data.jpa.repository.JpaRepository;


import br.edu.fatecfranca.demo.entities.Customer;


public interface CustomerRepository
       extends JpaRepository<Customer, Long> {
}
