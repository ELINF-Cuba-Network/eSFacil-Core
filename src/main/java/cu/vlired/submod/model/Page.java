package cu.vlired.submod.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Setter @Getter
@ToString
public class Page {
    
    //The number of elements in the page
    private long size;

    //The total number of elements
    private long totalElements;

    //The total number of pages
    private long totalPages;

    //The current page number
    private int pageNumber;

    //Sort info
    private Map<String, String> sortInfo;

    //search patter
    private String search;

    public Page() {
    }
}
