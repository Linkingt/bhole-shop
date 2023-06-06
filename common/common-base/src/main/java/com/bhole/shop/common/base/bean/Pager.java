package com.bhole.shop.common.base.bean;

/**
 * @program: bhole-shop-common-base
 * @description:
 * @author: joke
 * @date: 2023/5/30 13:42
 * @version: 1.0
 */
public class Pager {

    /**
     * 总行数
     */
    private int totalRows;

    /**
     * 每页显示
     */
    private int pageSize;

    /**
     * 当前页
     */
    private int currentPage;

    /**
     * 总页数
     */
    private int totalPages;

    /**
     * 起始行
     */
    private int startRow;

    /**
     * 结束行
     */
    private int endRow;

    /**
     * 翻页操作
     */
    private String pageMethod;

    /* 默认最小数量分页 */
    private static final Pager min;
    /* 默认最大数量分页 */
    private static final Pager max;
    /* 默认数量分页 */
    private static final Pager defaultPager;

    static {
        min = new Pager();
        min.setCurrentPage(1);
        min.setPageSize(10);

        max = new Pager();
        max.setCurrentPage(1);
        max.setPageSize(65535);

        defaultPager = new Pager();
        defaultPager.setCurrentPage(1);
        defaultPager.setPageSize(100);
    }

    public Pager() {
    }

    /**
     * 总行数初始化
     *
     * @param totalRows
     */
    public Pager(int totalRows) {
        this.totalRows = totalRows;
        if(pageSize != 0) {
            totalPages = totalRows / pageSize;

            int mod = totalRows % pageSize;
            if (mod > 0) {
                totalPages++;
            }
        }else{
            totalPages = 0;
        }

        currentPage = 1;
        startRow = 0;
        resetEndRow();
    }

    public Pager(int totalRows, int pageSize) {
        this.totalRows = totalRows;
        this.pageSize = pageSize;

        if(pageSize != 0) {
            totalPages = totalRows / pageSize;
            int mod = totalRows % pageSize;
            if (mod > 0) {
                totalPages++;
            }
        }else{
            totalPages = 0;
        }

        currentPage = 1;
        startRow = 0;
        resetEndRow();
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage < 1 ? 1 : currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        if(pageSize > 0 && totalRows > 0){
            totalPages = totalRows / pageSize;
            if (totalRows % pageSize > 0) {
                totalPages++;
            }
        }
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getStartRow() {
        if(pageSize > 0){
            if (currentPage >= 1) {
                startRow = currentPage * pageSize - pageSize;
            } else {
                startRow = 0;
            }
        }
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public String getPageMethod() {
        return pageMethod;
    }

    public void setPageMethod(String pageMethod) {
        this.pageMethod = pageMethod;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public void resetEndRow() {
        if (startRow + pageSize <= totalRows) {
            endRow = startRow + pageSize;
        } else {
            endRow = totalRows;
        }
    }

    public void first() {
        currentPage = 1;
        startRow = 0;
        resetEndRow();
    }

    public void previous() {
        if (currentPage == 1) {
            return;
        }
        currentPage--;
        startRow = (currentPage - 1) * pageSize;
        resetEndRow();
    }

    public void next() {
        if (currentPage < totalPages) {
            currentPage++;
        }
        startRow = (currentPage - 1) * pageSize;
        resetEndRow();
    }

    public void last() {
        if (totalPages <= 0) {
            return;
        } else if (totalPages - currentPage <= 0) {
            currentPage = totalPages;
            return;
        } else {
            currentPage = totalPages;
        }
        startRow = (currentPage - 1) * pageSize;
        resetEndRow();
    }

    public void refresh(int currentPageRef) {
        currentPage = currentPageRef;
        if (currentPage > totalPages) {
            last();
        }
        if (currentPage < 1) {
            first();
        }
        resetEndRow();
    }

    public void go() {
        if (currentPage > totalPages) {
            last();
        } else {
            startRow = (currentPage - 1) * pageSize;
        }
    }

    public boolean isFirst() {
        return currentPage == 1;
    }

    public boolean isLast() {
        return currentPage == totalPages;
    }

    public boolean hasNext() {
        return currentPage < totalPages;
    }

    public boolean hasPrevious() {
        return currentPage > 1;
    }

    /**
     * 获取默认最小分页数量
     * @return
     */
    public static Pager defaultMinPager(){
        return min;
    }

    /**
     * 默认最大分页数量
     * @return
     */
    public static Pager defaultMaxPager(){
        return max;
    }

    /**
     * 默认分页数量
     * @return
     */
    public static Pager defaultPager(){
        return defaultPager;
    }

    @Override
    public String toString() {
        return "Pager{" +
                "totalRows=" + totalRows +
                ", pageSize=" + pageSize +
                ", currentPage=" + currentPage +
                ", totalPages=" + totalPages +
                ", startRow=" + startRow +
                ", endRow=" + endRow +
                ", pageMethod='" + pageMethod + '\'' +
                '}';
    }
}
