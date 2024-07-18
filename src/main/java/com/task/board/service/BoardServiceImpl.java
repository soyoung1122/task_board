package com.task.board.service;

import com.task.board.mapper.BoardMapper;
import com.task.board.model.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardMapper boardMapper;
    @Override
    public Board getBoard(int no) {
        return boardMapper.selectBoard(no);
    }

    @Override
    public List<Board> getBoardList() {
        return boardMapper.selectBoardList();
    }

    @Override
    public void insertBoard(Board board) {
        boardMapper.insertBoard(board);
    }

    @Override
    public void updateBoard(Board board) {
        boardMapper.updateBoard(board);
    }
}
