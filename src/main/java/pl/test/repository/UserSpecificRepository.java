package pl.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.test.model.UserSpecific;

public interface UserSpecificRepository extends JpaRepository<UserSpecific,Long> {
    UserSpecific findByAddress(String address);
}
