package pl.test.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 2, message = "{pl.test.model.User.firstName.Size}")
    private String firstName;
    @Size(min = 2, message = "{pl.test.model.User.lastName.Size}")
    private String lastName;
    @NotEmpty(message = "{pl.test.model.User.email.NotNull}")
    @Email(message = "{pl.test.model.User.email.Email}")
    private String email;
    @Size(min = 6, message = "{pl.test.model.User.password.Size}")
    private String password;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<UserRole> roles = new HashSet<>();
    @OneToOne(cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    private UserSpecific userSpecific;

    public User(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public UserSpecific getUserSpecific() {
        return userSpecific;
    }

    public void setUserSpecific(UserSpecific userSpecific) {
        this.userSpecific = userSpecific;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", userSpecific=" + userSpecific +
                '}';
    }
}
