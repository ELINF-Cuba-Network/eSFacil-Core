/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.vlired.submod.model;

import java.util.Map;

/**
 *
 * @author luizo
 */
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

    public long getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public Map<String, String> getSortInfo() {
        return sortInfo;
    }

    public String getSearch() {
        return search;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setSortInfo(Map<String, String> sortInfo) {
        this.sortInfo = sortInfo;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @Override
    public String toString() {
        return "Page{" + "size=" + size + ", totalElements=" + totalElements + ", totalPages=" + totalPages + ", pageNumber=" + pageNumber + ", sortInfo=" + sortInfo + ", search=" + search + '}';
    }

    
}
