package legalfasystem.controller;

import legalfasystem.enums.UsuarioPerfil;
import legalfasystem.model.Usuario;
import legalfasystem.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        usuarioRepository.deleteAll();

        Usuario usuario = new Usuario(
                "admin_teste",
                passwordEncoder.encode("Ta@1234"),
                UsuarioPerfil.ADMIN
        );
        usuarioRepository.save(usuario);
    }

    @Test
    @DisplayName("Deve registrar um usuário e retornar 201")
    void registerSuccess() throws Exception {
        String json = "{\"login\":\"admin_teste2\", \"senha\":\"Ta@123456\", \"role\":\"ADMIN\"}";

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve fazer login e receber um token JWT")
    void loginSuccess() throws Exception {

        String loginJson = "{\"login\":\"admin_teste\", \"senha\":\"Ta@1234\"}";

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }
}