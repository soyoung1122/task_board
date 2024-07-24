package com.task.board.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
@Alias("PageDTO")
public class PageDTO {
    @Value("${paging.count-list}")
    private int countList;       // 한페이지에 보여줄 글 갯수
    @Value("${paging.count-page}")
    private int countPage;         // 페이지 갯수

    private int page;             // 현재 페이지 번호
    private Pagination pagination;

    public PageDTO() {
        this.page = 1;
    }
    public int getOffset() {
        return (page - 1) * countList;
    }
}
