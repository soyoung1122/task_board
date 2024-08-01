package com.task.board.service;

import com.task.board.model.AttachedFile;
import com.task.board.model.Board;
import com.task.board.model.PageDTO;
import com.task.board.model.PagingResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

public interface BoardService {
    public Board getBoard(int no);
    public AttachedFile getFile(int no);
    public PagingResponse<Board> getBoardList(PageDTO pageDTO);
    public int insertBoard(Board board);
    public void updateBoard(Board board);
    public int getCount(PageDTO pageDTO);
    public void uploadImage(HttpServletRequest request, HttpServletResponse response) throws IOException;
    public void uploadFile(int no, MultipartFile file) throws IOException;
    public void deleteFile(int no);
    public Map<String, Object> downloadFile(int no) throws MalformedURLException;
}
