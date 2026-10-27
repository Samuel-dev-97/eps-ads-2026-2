
package br.edu.fatecfranca.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.fatecfranca.api.dtos.CarRequest;
import br.edu.fatecfranca.api.entities.Car;
import br.edu.fatecfranca.api.repositories.CarRepository;

@Service
public class CarService {

   private final CarRepository repository;

   public CarService(CarRepository repository) {
      this.repository = repository;
   }

   private void copyToEntity(CarRequest request, Car car) {
      car.setBrand(request.brand().trim());
      car.setModel(request.model().trim());
      car.setColor(request.color().trim());
      car.setYearManufacture(Math.toIntExact(request.yearManufacture()));
      car.setImported(request.imported());
      car.setPlates(request.plates().trim());
      car.setSellingDate(request.sellingDate());
      car.setSellingPrice(request.sellingPrice());
      car.setCustomerId(request.customerId());
   }

   public Car create(CarRequest request) {
      Car car = new Car();
      copyToEntity(request, car);
      return repository.save(car);
   }

   public List<Car> findAll() {
      return repository.findAll();
   }

   public Optional<Car> findById(Long id) {
      return repository.findById(id);
   }

   public Car update(Long id, CarRequest request) {
      Car car = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));

      copyToEntity(request, car);
      return repository.save(car);
   }

   public boolean existsById(Long id) {
      return repository.existsById(id);
   }

   public void deleteById(Long id) {
      repository.deleteById(id);
   }
}

