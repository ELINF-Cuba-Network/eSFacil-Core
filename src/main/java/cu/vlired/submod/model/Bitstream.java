package cu.vlired.submod.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

@Entity
@Table(name = "bitstream")
public class Bitstream  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bitstream_id")
    private Long id;

    @Column(name = "bitstream_name")
    private String name;

    @Column(name = "bitstream_extension")
    private String extension;
   
    @JsonIgnore
    @Column(name = "bitstream_code")
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    @JsonBackReference
    private Document document;
    
    public Bitstream() {
    }

    public Bitstream(String name, String extension, String code) {
        this.name = name;
        this.extension = extension;
        this.code = code;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
