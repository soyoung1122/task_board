package com.task.board.service;

import com.task.board.mapper.BoardMapper;
import com.task.board.model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardMapper boardMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;
    @Override
    public Board getBoard(int no) {
        Board board = boardMapper.selectBoard(no);
        board.setFile(boardMapper.selectFile(no));
        return board;
    }

    @Override
    public AttachedFile getFile(int no) {
        return boardMapper.selectFile(no);
    }

    @Override
    public PagingResponse<Board> getBoardList(PageDTO pageDTO) {
        int count = boardMapper.count(pageDTO);
        if(count < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
        }

        Pagination pagination = new Pagination(count, pageDTO);
        pageDTO.setPagination(pagination);

        List<Board> boardList = boardMapper.selectBoardList(pageDTO);

        return new PagingResponse<>(boardList, pagination);
    }

    @Override
    public int insertBoard(Board board) {
        boardMapper.insertBoard(board);
        return board.getNo();
    }

    @Override
    public void updateBoard(Board board) {
        boardMapper.updateBoard(board);
    }

    @Override
    public int getCount(PageDTO pageDTO) {
        return boardMapper.count(pageDTO);
    }

    @Override
    public void uploadImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sFileInfo = "";
        String sFileName = request.getHeader("file-name");
        String sFileNameExt = sFileName.substring(sFileName.lastIndexOf(".") + 1);
        sFileNameExt = sFileNameExt.toLowerCase();
        String[] allowFileArr = {"jpg","png","bmp","gif"};

        int nCnt = 0;
        for(int i=0; i<allowFileArr.length; i++) {
            if(sFileNameExt.equals(allowFileArr[i])){
                nCnt++;
            }
        }

        if(nCnt == 0) {
            PrintWriter print = response.getWriter();
            print.print("NOTALLOW_"+sFileName);
            print.flush();
            print.close();
        } else {

            File file = new File(uploadDir);

            if(!file.exists()) {
                file.mkdirs();
            }

            String sRealFileNm = "";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String today= formatter.format(new java.util.Date());
            sRealFileNm = today+ UUID.randomUUID().toString() + sFileName.substring(sFileName.lastIndexOf("."));
            String rlFileNm = uploadDir + sRealFileNm;

            InputStream inputStream = request.getInputStream();
            OutputStream outputStream=new FileOutputStream(rlFileNm);
            int numRead;
            byte bytes[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
            while((numRead = inputStream.read(bytes,0,bytes.length)) != -1){
                outputStream.write(bytes,0,numRead);
            }
            if(inputStream != null) {
                inputStream.close();
            }
            outputStream.flush();
            outputStream.close();

            sFileInfo += "&bNewLine=true";
            sFileInfo += "&sFileName="+ sFileName;
            sFileInfo += "&sFileURL="+uploadDir+sRealFileNm;
            PrintWriter printWriter = response.getWriter();
            printWriter.print(sFileInfo);
            printWriter.flush();
            printWriter.close();
        }
    }

    @Override
    public void uploadFile(int no, MultipartFile file) throws IOException {
        String savedFileName = "";
        String fileName = file.getOriginalFilename();
        UUID uuid = UUID.randomUUID();

        savedFileName = uuid.toString() + "_" + fileName;
        File uploadFile = new File(uploadDir, savedFileName);

        AttachedFile attachedFile = new AttachedFile(0,
                                                    no,
                                                    fileName,
                                                    uploadFile.getCanonicalPath(),
                                                    file.getContentType(),
                                                    file.getSize(),
                                                    new Date());

        boardMapper.insertFile(attachedFile);

        try {
            file.transferTo(uploadFile);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public Map<String, Object> downloadFile(int no) throws MalformedURLException {
        AttachedFile file = boardMapper.selectFile(no);
        Path path = Paths.get(file.getFile_path()).normalize();

        Resource resource = new UrlResource(path.toUri());

        if(resource.exists() || resource.isReadable()) {
            Map<String, Object> result = new HashMap<>();
            result.put("file", resource);
            result.put("fileType", file.getFile_type());
            return result;
        } else {
            throw new MalformedURLException();
        }
    }
}
