package cu.vlired.esFacilCore.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@Setter @Getter
public class BaseEntity {

    @Id
    @Column(name = "id")
    private UUID id = UUID.randomUUID();

    @Override
    public String toString(){
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}

