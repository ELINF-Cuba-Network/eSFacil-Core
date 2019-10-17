package cu.vlired.submod.model;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class InputForm {

    private String block;
    private String cslfield;
    private String label;
    private String help;
    private boolean repeatable;
    private boolean required;
    private String fieldtype;
    private boolean vocabulary;
    private boolean authority;

    public InputForm(
        String block,
        String cslfield,
        String label,
        String help,
        boolean repeatable,
        boolean required,
        String fieldtype,
        boolean vocabulary,
        boolean authority
    ) {
        this.block = block;
        this.cslfield = cslfield;
        this.label = label;
        this.help = help;
        this.repeatable = repeatable;
        this.required = required;
        this.fieldtype = fieldtype;
        this.vocabulary = vocabulary;
        this.authority = authority;
    }
}
