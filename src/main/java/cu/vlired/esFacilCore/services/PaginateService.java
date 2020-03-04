package cu.vlired.esFacilCore.services;

import cu.vlired.esFacilCore.util.Page;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaginateService {

    @Value("${app.default-page-size}")
    private int limit;

    @Value("${app.max-page-size}")
    private int max;

    /**
     * Handle default params when page and filter data
     *
     * @param page Page
     */
    public void preProcess(Page page) {
        page.setLimit(page.getLimit() == 0 || page.getLimit() > max? limit : page.getLimit());
        page.setQ(page.getQ() == null ? "" : page.getQ());
    }
}
