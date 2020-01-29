package cu.vlired.esFacilCore.components;

import cu.vlired.esFacilCore.model.Page;
import cu.vlired.esFacilCore.model.PagedData;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PaginationHelper {

    /**
     * Build pageable object
     * using a params map
     * @param params
     * @return
     */
    public Pageable buildPageableByParams(Map<String, String> params ){

        // {size, pageNumber, dir, prop, search}
        int pageNumber  = Integer.parseInt(params.get("pageNumber"));
        int size        = Integer.parseInt(params.get("size"));
        String dir      = params.get("dir");
        String prop     = params.get("prop");

        // Build pageable
        Sort sort = Sort.by(prop);
        if (dir.equals("asc")) {
            sort.ascending();
        } else {
            sort.descending();
        }

        return  PageRequest.of(pageNumber, size, sort);
    }

    public <T> PagedData<T> buildResponseByParams(Map<String, String> params, long count, List<T> filterData){
        // Build Respose for FrontEnd
        Page page = new Page();

        // Params keys
        // {size, pageNumber, dir, prop, search}
        int pageNumber  = Integer.parseInt(params.get("pageNumber"));
        int size        = Integer.parseInt(params.get("size"));
        String dir      = params.get("dir");
        String prop     = params.get("prop");
        String search   = params.get("search");

        page.setTotalElements(count);
        page.setPageNumber(pageNumber);
        page.setSearch(search);
        page.setSize(size);

        Map<String, String> sortInfo = new HashMap<>();
        sortInfo.put("dir",     dir);
        sortInfo.put("prop",    prop);
        page.setSortInfo(sortInfo);
        page.setTotalPages(count / size);

        PagedData<T> pagedData = new PagedData<>();
        pagedData.setData(filterData);
        pagedData.setPage(page);

        return pagedData;
    }

}
