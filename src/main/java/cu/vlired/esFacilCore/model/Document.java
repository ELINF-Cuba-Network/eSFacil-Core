package cu.vlired.esFacilCore.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;

import cu.vlired.esFacilCore.constants.Condition;
import cu.vlired.esFacilCore.model.documentData.DocumentData;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity
@Table(name = "document")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Getter @Setter
public class Document extends BaseEntity {

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private DocumentData data;

    @OneToMany(
        mappedBy = "document",
        orphanRemoval = true,
        cascade = CascadeType.REMOVE
    )
    @JsonManagedReference
    private List<Bitstream> bitstreams;

    @Column(name = "condition")
    private String condition = Condition.WORKSPACE;

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
