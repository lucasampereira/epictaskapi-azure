package br.com.fiap.epictaskapi.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.fiap.epictaskapi.model.Task;
import br.com.fiap.epictaskapi.model.User;
import br.com.fiap.epictaskapi.repository.TaskRepository;
import br.com.fiap.epictaskapi.repository.UserRepository;

@Configuration
@Profile("dev")
public class DatabaseSeed implements CommandLineRunner {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        userRepository.save(
            new User()
                .name("João")
                .email("joao@fiap.com.br")
                .password(passwordEncoder.encode("123")) 
        );

        userRepository.saveAll(List.of(
            new User("Chris", "chrissytirocerto@gmail.com", "password123"),
            new User("Julius", "juliusrock@gmail.com", "pass123456w"),
            new User("Greg", "chrisegreg@gmail.com", "taonasua23"),
            new User("Isabela", "isabela@fiap.com.br", passwordEncoder.encode("123"))
        ));

        taskRepository.saveAll(List.of(
            new Task("Modelar BD", "modelar tabelas do banco", 150),
            new Task("Prototipo", "prototipar as telas", 20),
            new Task("Login", "prototipar as telas", 10),
            new Task("Deploy", "prototipar as telas", 50),
            new Task("Cadastro de usuário", "prototipar as telas", 30),
            new Task("Testes", "prototipar as telas", 25),
            new Task("Logout", "prototipar as telas", 12),
            new Task("Internacionalização", "prototipar as telas", 70),
            new Task("Bug", "corrigir erro da API", 50)
        ));
    }
    
}
