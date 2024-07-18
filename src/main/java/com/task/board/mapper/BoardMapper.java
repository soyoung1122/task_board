package com.task.board.mapper;

import com.task.board.model.Board;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface BoardMapper {
    public Board selectBoard(int no);
    public List<Board> selectBoardList();
    public void insertBoard(Board board);
    public void updateBoard(Board board);
}
