package br.edu.fatecfranca.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.fatecfranca.demo.entities.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
}
