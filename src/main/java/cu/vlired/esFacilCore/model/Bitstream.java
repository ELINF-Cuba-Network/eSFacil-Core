package cu.vlired.esFacilCore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "bitstream")
@Getter @Setter
public class Bitstream  extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "extension")
    private String extension;
   
    @JsonIgnore
    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description = "";

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
}
