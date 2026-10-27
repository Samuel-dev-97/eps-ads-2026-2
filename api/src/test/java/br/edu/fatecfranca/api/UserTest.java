package br.edu.fatecfranca.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.edu.fatecfranca.api.dtos.CarRequest;
import br.edu.fatecfranca.api.dtos.CustomerRequest;
import br.edu.fatecfranca.api.dtos.LoginRequest;
import br.edu.fatecfranca.api.dtos.UserRequest;
import br.edu.fatecfranca.api.entities.User;
import br.edu.fatecfranca.api.repositories.UserRepository;
import br.edu.fatecfranca.api.services.UserService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class UserTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void shouldCreateUserFromRequest() {
        UserRequest request = new UserRequest(
            "Maria Silva",
            "maria",
            "maria@email.com",
            "senha123",
            false
        );

        User user = new User();
        user.setFullname(request.fullname());
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setIsAdmin(request.isAdmin());

        assertEquals("Maria Silva", user.getFullname());
        assertEquals("maria", user.getUsername());
        assertEquals("maria@email.com", user.getEmail());
        assertFalse(user.getIsAdmin());
    }

    @Test
    void shouldRejectBlankValuesInCustomerRequest() {
        CustomerRequest request = new CustomerRequest(
            "   ",
            "12345678900",
            LocalDate.of(1990, 2, 15),
            "Avenida Brasil",
            "150",
            "Casa",
            "Centro",
            "Franca",
            "SP",
            "16999999999",
            "maria@email.com"
        );

        Set<ConstraintViolation<CustomerRequest>> violations = validator.validate(request);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void shouldRejectBlankValuesInCarRequest() {
        CarRequest request = new CarRequest(
            "   ",
            "Civic",
            "Preto",
            2022L,
            true,
            "ABC1234",
            LocalDate.now(),
            new BigDecimal("50000.00"),
            1L
        );

        Set<ConstraintViolation<CarRequest>> violations = validator.validate(request);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("brand")));
    }

    @Test
    void shouldRejectBlankValuesInUserRequest() {
        UserRequest request = new UserRequest(
            "   ",
            "maria",
            "maria@email.com",
            "senha123",
            false
        );

        Set<ConstraintViolation<UserRequest>> violations = validator.validate(request);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("fullname")));
    }

    @Test
    void shouldAuthenticateUserByUsernameOrEmail() {
        UserRepository repository = mock(UserRepository.class);
        PasswordEncoder passwordEncoder = new Argon2PasswordEncoder(16, 32, 4, 65 * 1024, 3);
        UserService service = new UserService(repository, passwordEncoder);

        User user = new User();
        user.setFullname("Maria das Couves");
        user.setUsername("mcouves");
        user.setEmail("mcouves@verdura.com.br");
        user.setPassword(passwordEncoder.encode("Verdura@456"));
        user.setIsAdmin(true);

        when(repository.findByUsernameOrEmail("mcouves", "mcouves")).thenReturn(Optional.of(user));

        Optional<User> authenticated = service.authenticate(new LoginRequest("mcouves", "Verdura@456"));

        assertTrue(authenticated.isPresent());
        assertEquals("mcouves", authenticated.get().getUsername());
    }
}
