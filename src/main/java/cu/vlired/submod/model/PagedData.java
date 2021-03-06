package cu.vlired.submod.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter @Getter
@ToString
public class PagedData<T> {
    
    private List<T> data;
    private Page    page;
}

