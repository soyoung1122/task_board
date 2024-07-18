package com.task.board.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;
import java.util.List;

@Data
@Alias("Board")
public class Board {
    private int no;
    private String user_name;
    private String title;
    private String content;
    private Date reg_date;
    private Date update_date;
    private List<File> files;
}
