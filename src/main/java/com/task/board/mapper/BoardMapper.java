package com.task.board.mapper;

import com.task.board.model.AttachedFile;
import com.task.board.model.Board;
import com.task.board.model.PageDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    public Board selectBoard(int no);
    public AttachedFile selectFile(int no);
    public List<Board> selectBoardList(PageDTO params);
    public void insertBoard(Board board);
    public void insertFile(AttachedFile file);
    public void updateBoard(Board board);
    public int count(PageDTO params);
}
