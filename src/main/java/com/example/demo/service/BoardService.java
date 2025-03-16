package com.example.demo.service;

import com.example.demo.dto.BoardDTO;
import com.example.demo.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {

    public Page<BoardDTO> getList(Pageable pageable);

    public BoardDTO boardDetail(Long bno);

    public void boardRegister(BoardDTO boardDTO);

    public void boardUpdate(BoardDTO boardDTO);

    public void boardDelete(Long bno);
}
