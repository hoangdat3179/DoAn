package vn.techmaster.storyreadingwebsite;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import vn.techmaster.storyreadingwebsite.entity.Role;
import vn.techmaster.storyreadingwebsite.entity.User;
import vn.techmaster.storyreadingwebsite.repository.UserRepository;

import java.util.List;

@Configuration
public class DatabaseLoader {

    private UserRepository repo;

    public DatabaseLoader(UserRepository repo) {
        this.repo = repo;
    }

//    @Bean
//    public CommandLineRunner initializeDatabase() {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        return args -> {
//            User user1 = new User("admin@gmail.com", encoder.encode( "admin"), Role.ADMIN);
//
//            repo.saveAll(List.of(user1));
//
//            System.out.println("Database initialized");
//        };
//    }

}
