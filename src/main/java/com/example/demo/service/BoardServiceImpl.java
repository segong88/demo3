package com.example.demo.service;

import com.example.demo.dto.BoardDTO;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper = new ModelMapper();


    @Override
    public Page<BoardDTO> getList(Pageable pageable) {
        log.info("들어온 값 : " + pageable);

        Page<Board> boards = boardRepository.findAll(pageable);

        //boards는 Page<List>로 감싸져있어서 modelMapper가 인식을 못함 -> 내용물만 board에 담아서 DTO로 변환
        return boards.map(board -> modelMapper.map(board, BoardDTO.class));
    }

    @Override
    public BoardDTO boardDetail(Long bno) {
        log.info("들어온 값 : " + bno);

        Board board = boardRepository.findById(bno).orElseThrow();

        return modelMapper.map(board, BoardDTO.class);
    }

    @Override
    public void boardRegister(BoardDTO boardDTO) {
        log.info("들어온 값 : " + boardDTO);

        //DTO를 받아서 entity로 변환
        Board board = modelMapper.map(boardDTO, Board.class);
        boardRepository.save(board);
    }

    @Override
    public void boardUpdate(BoardDTO boardDTO) {
        log.info("들어온 값 : " + boardDTO);

        //기존 데이터 가져오기 (pk로)
        Board board = boardRepository.findById(boardDTO.getBno())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        //IllegalArgumentException : 잘못된 값이나 형식의 인수가 메서드에 전달될 때 발생


        //기존 데이터에 새로운 값 적용
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        board.setModifiedDate(LocalDateTime.now());     //수정 시간 업데이트

        //저장
        boardRepository.save(board);
    }

    @Override
    public void boardDelete(Long bno) {
        log.info("들어온 값 : " + bno);

        boardRepository.deleteById(bno);

    }   //삭제는 굳이 entity로 변환을 안해도 되는군요,,~


}
