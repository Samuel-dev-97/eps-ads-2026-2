package br.edu.fatecfranca.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.fatecfranca.api.dtos.LoginRequest;
import br.edu.fatecfranca.api.dtos.UserRequest;
import br.edu.fatecfranca.api.entities.User;
import br.edu.fatecfranca.api.repositories.UserRepository;

@Service
public class UserService {

   private final UserRepository repository;
   private final PasswordEncoder passwordEncoder;

   public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
      this.repository = repository;
      this.passwordEncoder = passwordEncoder;
   }

   private void copyToEntity(UserRequest request, User user) {
      user.setFullname(request.fullname().trim());
      user.setUsername(request.username().trim());
      user.setEmail(request.email().trim());
      user.setPassword(passwordEncoder.encode(request.password()));
      user.setIsAdmin(Boolean.TRUE.equals(request.isAdmin()));
   }

   public User create(UserRequest request) {
      User user = new User();
      copyToEntity(request, user);
      return repository.save(user);
   }

   public List<User> findAll() {
      return repository.findAll();
   }

   public Optional<User> findById(Long id) {
      return repository.findById(id);
   }

   public User update(Long id, UserRequest request) {
      User user = repository.findById(id)
          .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

      copyToEntity(request, user);
      return repository.save(user);
   }

   public boolean existsById(Long id) {
      return repository.existsById(id);
   }

   public void deleteById(Long id) {
      repository.deleteById(id);
   }

   public Optional<User> authenticate(LoginRequest request) {
      String usernameOrEmail = request.usernameOrEmail().strip();
      Optional<User> result = repository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

      if (result.isEmpty()) {
         return Optional.empty();
      }

      User user = result.get();
      boolean validPassword = passwordEncoder.matches(request.password(), user.getPassword());

      if (!validPassword) {
         return Optional.empty();
      }

      return Optional.of(user);
   }
}
