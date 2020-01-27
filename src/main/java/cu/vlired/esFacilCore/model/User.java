package cu.vlired.esFacilCore.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(name = "person")
@Getter @Setter
public class User extends BaseEntity {

    @Id
    @Column(name = "id")
    private UUID id = UUID.randomUUID();

    @Column(unique = true, name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(unique = true, name = "email")
    private String email;

    @Column(name = "active")
    private boolean active;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    public User() {
    }
}
