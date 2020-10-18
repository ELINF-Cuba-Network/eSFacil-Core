package cu.vlired.esFacilCore.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "submit_task")
@Getter
@Setter
public class SubmitTask extends BaseEntity {

    public static final short STATUS_PENDING = 0;
    public static final short STATUS_OK = 1;
    public static final short STATUS_KO= 2;

    @ManyToOne
    @JoinColumn(name = "submit_config")
    private SubmitConfig config;

    @ManyToOne
    @JoinColumn(name = "document")
    private Document document;

    @Column(name = "status")
    private short status;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;
}
