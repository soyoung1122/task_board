package com.task.board.service;

import com.task.board.model.Board;

import java.util.List;

public interface BoardService {
    public Board getBoard(int no);
    public List<Board> getBoardList();
    public void insertBoard(Board board);
    public void updateBoard(Board board);
}
