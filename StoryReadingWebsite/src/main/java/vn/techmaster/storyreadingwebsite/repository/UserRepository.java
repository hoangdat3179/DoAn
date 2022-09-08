package vn.techmaster.storyreadingwebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.techmaster.storyreadingwebsite.entity.User;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Tìm User theo email
    User findUserByEmail(String username);
}