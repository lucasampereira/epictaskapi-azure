package br.com.fiap.epictaskapi.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.epictaskapi.model.User;
import br.com.fiap.epictaskapi.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService service;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping
    @Cacheable("user")
    public Page<User> index(@PageableDefault(size = 5) Pageable pageable){
        
        return service.listAll(pageable);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid User user){
        String password = passwordEncoder.encode(user.getPassword());
        user.password(password);
        service.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> show(@PathVariable Long id){
        return ResponseEntity.of(service.getById(id));
    }

    @DeleteMapping("{id}")
    @CacheEvict(value = "user", allEntries = true)
    public ResponseEntity<Object> destroy(@PathVariable Long id){

        Optional<User> optional = service.getById(id);

        if(optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }    

    @PutMapping("{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody @Valid User newUser){
        Optional<User> optional = service.getById(id);

        if(optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        var user = optional.get();
        var password = user.getPassword();

        BeanUtils.copyProperties(newUser, user);

        user.setId(id);
        user.setPassword(passwordEncoder.encode(password));

        service.save(user);

        return ResponseEntity.ok(user);
    }

}
