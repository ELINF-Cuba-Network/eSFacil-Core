package cu.vlired.esFacilCore.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "role")
@Setter @Getter
public class Role extends BaseEntity {

    @Id
    @Column(name="role_id")
    private UUID id = UUID.randomUUID();

    @Column(name="name")
    private String name;

    public Role() {
    }
}

