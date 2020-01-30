package cu.vlired.esFacilCore.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity
@Table(name = "document")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Getter @Setter
public class Document extends BaseEntity {

    @Id
    @Column(name = "id")
    private UUID id = UUID.randomUUID();

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Map<String, List<String>> data;

    @OneToMany(
        mappedBy = "document",
        orphanRemoval = true
    )
    @JsonManagedReference
    private List<Bitstream> bitstreams;

    @Column(name = "condition")
    private String condition;

    @ManyToOne
    @JoinColumn(name = "person")
    private User person;

    public Document() {
        this.bitstreams = new LinkedList<>();
    }

    public void addBitstream(Bitstream bitstream) {
        bitstreams.add(bitstream);
        bitstream.setDocument(this);
    }

    public void removeBitstream(Bitstream bitstream) {
        bitstreams.remove(bitstream);
        bitstream.setDocument(null);
    }
}
