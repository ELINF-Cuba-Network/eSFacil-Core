package cu.vlired.esFacilCore.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import cu.vlired.esFacilCore.model.configFormData.SubmitConfigData;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "submit_config")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Getter
@Setter
public class SubmitConfig extends BaseEntity {

    public static final String TYPE_DSPACE = "DSPACE";
    public static final String TYPE_ABCD = "ABCD";

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private SubmitConfigData data;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "active")
    private boolean active;

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "person")
    private User person;
}
