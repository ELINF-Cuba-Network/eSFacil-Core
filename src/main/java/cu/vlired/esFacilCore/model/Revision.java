package cu.vlired.esFacilCore.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "revision")
@Getter @Setter
public class Revision extends BaseEntity {

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "person")
    private User person;

    @ManyToOne
    @JoinColumn(name = "document")
    private Document document;

}
