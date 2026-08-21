package br.edu.fatecfranca.demo.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import br.edu.fatecfranca.demo.repositories.CustomerRepository;


@RestController
@RequestMapping("/customers")
public class CustomerController {


 private final CustomerRepository repository;


 public CustomerController(CustomerRepository repository) {
   this.repository = repository;
 }


}
