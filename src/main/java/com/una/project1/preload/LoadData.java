package com.una.project1.preload;


import com.una.project1.model.Payment;
import com.una.project1.model.User;
import com.una.project1.model.Role;
import com.una.project1.repository.PaymentRepository;
import com.una.project1.repository.RoleRepository;
import com.una.project1.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Set;

@Configuration
class LoadData {
    private static final Logger log = LoggerFactory.getLogger(LoadData.class);

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, RoleRepository roleRepository, PaymentRepository paymentRepository) {
        return args -> {
            log.info("Preloading Roles...");
            Set<Role> userRoleSet = new java.util.HashSet<>(Collections.emptySet());
            Set<Role> adminRoleSet = new java.util.HashSet<>(Collections.emptySet());
            Role standardClient = roleRepository.save(new Role("StandardClient"));
            Role adminClient = roleRepository.save(new Role("AdministratorClient"));
            userRoleSet.add(standardClient);
            adminRoleSet.add(adminClient);
            log.info("Preloading Users...");
            User user1 = new User("jdiaz", "Jose",passwordEncoder.encode("12345"), adminRoleSet);
            User user2 = new User("elopez", "Eliecer", passwordEncoder.encode("testpassword"), userRoleSet);
            userRepository.save(user1);
            userRepository.save(user2);
            log.info("Preloading Payments...");
            Payment payment1 = new Payment("1234123456785678","Jose Diaz", "04/24", "123", "Heredia, Costa Rica", user1);
            paymentRepository.save(payment1);
        };
    }
}