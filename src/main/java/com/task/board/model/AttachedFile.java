package com.task.board.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Data
@AllArgsConstructor
@Alias("AttachedFile")
public class AttachedFile {
    private int no;
    private int board_no;
    private String file_name;
    private String file_path;
    private String file_type;
    private long file_size;
    private Date file_date;
}
