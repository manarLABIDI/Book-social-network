package com.mlsoftwares.book;

import com.mlsoftwares.book.role.Role;
import com.mlsoftwares.book.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAware")
@EnableAsync
@ComponentScan(basePackages = {
        "com.mlsoftwares.book.config",
        "com.mlsoftwares.book.security",
        "com.mlsoftwares.book" // Assuming JwtService is here
})
public class BookNetworksApplication {

    public static void main(String[] args) {

        SpringApplication.run(BookNetworksApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName("USER").isEmpty()) {
                roleRepository.save(
                        Role.builder().name("USER").build()
                );
            }
        };
    }

}
