package com.example.demo.service;

import com.example.demo.dto.BoardDTO;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper = new ModelMapper();


    @Override
    public Page<BoardDTO> getList(Pageable pageable) {

        Page<Board> boards = boardRepository.findAll(pageable);

        //boards는 Page<List>로 감싸져있어서 modelMapper가 인식을 못함 -> 내용물만 board에 담아서 DTO로 변환
        return boards.map(board -> modelMapper.map(board, BoardDTO.class));
    }

    @Override
    public BoardDTO boardDetail(Long bno) {

        Board board = boardRepository.findById(bno).orElseThrow();

        return modelMapper.map(board, BoardDTO.class);
    }

    @Override
    public void boardRegister(BoardDTO boardDTO) {

        //등록하면 자동으로 현재 시간 값 들어감
        boardDTO.setLocalDateTime(LocalDateTime.now());

        //DTO를 받아서 entity로 변환
        Board board = modelMapper.map(boardDTO, Board.class);
        boardRepository.save(board);
    }

    @Override
    public void boardUpdate(BoardDTO boardDTO) {

        Board board = modelMapper.map(boardDTO, Board.class);
        boardRepository.save(board);

    }   //등록과 수정은 똑같군요,, ~

    @Override
    public void boardDelete(Long bno) {

        boardRepository.deleteById(bno);

    }   //삭제는 굳이 entity로 변환을 안해도 되는군요,,~


}
