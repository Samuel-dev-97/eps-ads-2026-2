package br.edu.fatecfranca.api.services;


import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;
import br.edu.fatecfranca.api.dtos.CustomerRequest;
import br.edu.fatecfranca.api.dtos.CustomerRequest;
import br.edu.fatecfranca.api.entities.Customer;
import br.edu.fatecfranca.api.repositories.CustomerRepository;


@Service
public class CustomerService {


   private final CustomerRepository repository;


   private void copyToEntity(
       CustomerRequest request,
       Customer customer) {


       customer.setName(request.name().trim());
       customer.setIdentDocument(request.identDocument().trim());
       customer.setBirthDate(request.birthDate());
       customer.setStreetName(request.streetName().trim());
       customer.setHouseNumber(request.houseNumber().trim());
       customer.setComplements(request.complements() != null ? request.complements().trim() : null);
       customer.setDistrict(request.district().trim());
       customer.setMunicipality(request.municipality().trim());
       customer.setState(request.state().trim().toUpperCase());
       customer.setPhone(request.phone().trim());
       customer.setEmail(request.email().trim());
   }  

   public CustomerService(CustomerRepository repository) {
       this.repository = repository;
   }


   public Customer create(CustomerRequest request) {
         Customer customer = new Customer();
            copyToEntity(request, customer);
       return repository.save(customer);
   }


   public List<Customer> findAll() {
       return repository.findAll();
   }


   public Optional<Customer> findById(Long id) {
       return repository.findById(id);
   }


   public Customer update(Long id, CustomerRequest request) {
       Customer customer = repository.findById(id)
           .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

       copyToEntity(request, customer);
       return repository.save(customer);
   }


   public boolean existsById(Long id) {
       return repository.existsById(id);
   }


   public void deleteById(Long id) {
       repository.deleteById(id);
   }

}