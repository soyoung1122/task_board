package com.task.board.model;

import lombok.Getter;

@Getter
public class Pagination {
    private int totalRecordCount;
    private int totalPageCount;
    private int startPage;
    private int endPage;
    private int limitStart;
    private boolean existPrevPage;
    private boolean existNextPage;

    public Pagination(int totalRecordCount, PageDTO params) {
        if (totalRecordCount > 0) {
            this.totalRecordCount = totalRecordCount;
            calculation(params);
        }
    }

    private void calculation(PageDTO params) {

        totalPageCount = ((totalRecordCount - 1) / params.getCountList()) + 1;

        if (params.getPage() > totalPageCount) {
            params.setPage(totalPageCount);
        }

        startPage = ((params.getPage() - 1) / params.getCountPage()) * params.getCountPage() + 1;

        endPage = startPage + params.getCountPage() - 1;

        if (endPage > totalPageCount) {
            endPage = totalPageCount;
        }

        limitStart = (params.getPage() - 1) * params.getCountList();

        existPrevPage = startPage != 1;

        existNextPage = (endPage * params.getCountList()) < totalRecordCount;
    }

}
