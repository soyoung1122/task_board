package com.task.board.controller;

import com.task.board.model.Board;
import com.task.board.model.PageDTO;
import com.task.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.util.Map;

@Controller
@Log4j2
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private PageDTO page;

    @GetMapping("")
    public String getBoardList(HttpServletRequest request, @ModelAttribute("params")PageDTO params, Model model) {
        params.setCountList(page.getCountList());
        params.setCountPage(page.getCountPage());

        model.addAttribute("response", boardService.getBoardList(params));
        return "index";
    }

    @GetMapping("/{no}")
    public String getBoardDetail(HttpServletRequest request, @PathVariable("no") int no) {
        request.setAttribute("board", boardService.getBoard(no));
        return "board";
    }

    @GetMapping("/write")
    public String writeBoard(HttpServletRequest request) {
        return "write";
    }

    @PostMapping("/write")
    public @ResponseBody int writeBoard(HttpServletRequest request, @RequestBody Board board) {
        return boardService.insertBoard(board);
    }

    @GetMapping("/update/{no}")
    public String updateBoard(HttpServletRequest request, @PathVariable("no") int no) {
        request.setAttribute("board", boardService.getBoard(no));
        return "update";
    }

    @PutMapping("/update")
    public ResponseEntity updateBoard(HttpServletRequest request, @RequestBody Board board) {
        boardService.updateBoard(board);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/download/{no}")
    public ResponseEntity<Resource> downloadFile(HttpServletRequest request, @PathVariable int no) throws MalformedURLException {
        Map<String, Object> map = boardService.downloadFile(no);

        Resource resource = (Resource)map.get("file");
        String fileType = (String)map.get("fileType");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    @PostMapping("/upload/{no}")
    public ResponseEntity uploadFile(HttpServletRequest request, @PathVariable("no") int no, @RequestParam("uploadFile") MultipartFile file) throws IOException {
        boardService.uploadFile(no, file);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/upload/img")
    public void uploadImg(HttpServletRequest request, HttpServletResponse response) throws IOException {
       boardService.uploadImage(request, response);
}


}