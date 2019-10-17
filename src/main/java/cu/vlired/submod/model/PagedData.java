/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.vlired.submod.model;

import java.util.List;

/**
 *
 * @author luizo
 */
public class PagedData<T> {
    
    private List<T> data;
    private Page    page;

    public List<T> getData() {
        return data;
    }

    public Page getPage() {
        return page;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "PagedData{" + "data=" + data + ", page=" + page + '}';
    }
    
   

}

