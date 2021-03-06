package pl.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.test.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByRole(String role);
}