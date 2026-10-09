package br.edu.fatecfranca.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import br.edu.fatecfranca.api.dtos.UserRequest;
import br.edu.fatecfranca.api.entities.User;

class UserTest {

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
}
