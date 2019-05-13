package pl.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.test.model.User;
import pl.test.model.UserRole;
import pl.test.model.UserSpecific;
import pl.test.repository.UserRepository;
import pl.test.repository.UserRoleRepository;
import pl.test.repository.UserSpecificRepository;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private UserSpecificRepository userSpecificRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserSpecificRepository(UserSpecificRepository userSpecificRepository) {
        this.userSpecificRepository = userSpecificRepository;
    }

    @Autowired
    public void setRoleRepository(UserRoleRepository roleRepository) {
        this.userRoleRepository = roleRepository;
    }

    public boolean addWithDefaultRole(User user) {
        boolean check = false;
        try{
            String s = userRepository.findByEmail(user.getEmail()).getEmail();
        }catch (NullPointerException e){
            check = true;
        }
        if(check) {
            UserRole defaultRole = userRoleRepository.findByRole("ROLE_USER");
            user.getRoles().add(defaultRole);
            String passwordHash = passwordEncoder.encode(user.getPassword());
            user.setPassword(passwordHash);
            userRepository.save(user);
        }
        return check;
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public void updateUser(long id, String nameOfRole){
        User user = userRepository.getOne(id);
        UserRole role = userRoleRepository.findByRole(nameOfRole);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    public void updateUserSpecific(long id, UserSpecific userSpecific){
        User user = userRepository.getOne(id);
        Long specificId = null;
        try {
            specificId = user.getUserSpecific().getId();
        }catch (NullPointerException e){
            System.out.println("null");
        }
        user.setUserSpecific(userSpecific);
        if(specificId != null)
            userSpecificRepository.deleteById(specificId);
        userRepository.save(user);
    }

    public void deleteUser(long id){
        User user = userRepository.getOne(id);
        userRepository.delete(user);
    }
}

