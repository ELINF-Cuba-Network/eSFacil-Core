package cu.vlired.submod.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity
@Table(name = "document")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Document extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "document_id")
    private Long id;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Map<String, List<String>> data;

    @OneToMany(
        mappedBy = "document",
        cascade = CascadeType.ALL,
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

    public Long getId() {
        return id;
    }

    public Map<String, List<String>> getData() {
        return data;
    }

    public void setData(Map<String, List<String>> data) {
        this.data = data;
    }

    public List<Bitstream> getBitstreams() {
        return bitstreams;
    }

    public void setBitstreams(List<Bitstream> bitstreams) {
        this.bitstreams = bitstreams;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public User getPerson() {
        return person;
    }

    public void setPerson(User person) {
        this.person = person;
    }
}
