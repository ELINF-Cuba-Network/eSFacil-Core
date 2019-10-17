/*
 * @copyleft VLIRED
 * @author jose
 * 10/13/19
 */
package cu.vlired.submod.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class MetadataValue implements Serializable {

    private String key;

    private List<String> values;

    public MetadataValue() {
        this.values = new LinkedList<>();
    }

    public MetadataValue(String key, List<String> values) {
        this.key = key;
        this.values = values;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public void addValue(String v) {
        values.add(v);
    }

    public void removeValue(String v) {
        this.values.remove(v);
    }

    @Override
    public String toString() {
        return "<" + key + ": " + values + ">";
    }
}
