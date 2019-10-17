/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.vlired.submod.model;

/**
 *
 * @author luizo
 */
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

    public InputForm(String block, String cslfield, String label, String help, boolean repeatable, boolean required, String fieldtype, boolean vocabulary, boolean authority) {
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

    public String getBlock() {
        return block;
    }

    public String getCslfield() {
        return cslfield;
    }

    public String getFieldtype() {
        return fieldtype;
    }

    public String getHelp() {
        return help;
    }

    public String getLabel() {
        return label;
    }

    public boolean isAuthority() {
        return authority;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isVocabulary() {
        return vocabulary;
    }

    public void setAuthority(boolean authority) {
        this.authority = authority;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public void setCslfield(String cslfield) {
        this.cslfield = cslfield;
    }

    public void setFieldtype(String fieldtype) {
        this.fieldtype = fieldtype;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setRepeatable(boolean repeatable) {
        this.repeatable = repeatable;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setVocabulary(boolean vocabulary) {
        this.vocabulary = vocabulary;
    }
    
    
}
