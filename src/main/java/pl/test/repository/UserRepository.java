package pl.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.test.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
