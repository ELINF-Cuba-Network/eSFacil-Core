/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author luizo
 */
@Component
public class PaginationHelper {

    /**
     * Build pageable object
     * using a params map
     * @param params
     * @return
     */
    public Pageable buildPageableByParams( Map<String, String> params ){
        // Params keys
        // {size, pageNumber, dir, prop, search}
        int pageNumber  = Integer.parseInt(params.get("pageNumber"));
        int size        = Integer.parseInt(params.get("size"));
        String dir      = params.get("dir");
        String prop     = params.get("prop");

        // Build pageable
        Sort sort = new Sort(
                dir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
                prop
        );

        return  PageRequest.of(pageNumber, size, sort);
    }

    public <T> PagedData<T> buildResponseByParams( Map<String, String> params, long count, List<T> filterData){
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
