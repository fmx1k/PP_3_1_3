package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Set;

@Configuration
public class DatabaseInitializer {

    private UserRepository userRepository;


    private RoleRepository roleRepository;

    @Autowired
    public DatabaseInitializer(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostConstruct
    public void init() {
        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");

        roleRepository.save(adminRole);
        roleRepository.save(userRole);

        User adminUser = new User("admin", encoder.encode("adminpassword"));
        adminUser.setRoles(Set.of(adminRole, userRole));
        userRepository.save(adminUser);

        User regularUser = new User("user", encoder.encode("userpassword"));
        regularUser.setRoles(Set.of(userRole));
        userRepository.save(regularUser);
    }

}
