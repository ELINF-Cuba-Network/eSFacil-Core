package cu.vlired.submod.model;

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

    @Id
    @Column(name = "bitstream_id")
    private UUID id = UUID.randomUUID();

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
}
