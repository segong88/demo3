package com.example.demo.repository;

import com.example.demo.entity.Board;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.thymeleaf.standard.expression.Each;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void insertTest(){

        for(int i = 0; i < 200; i++) {
            Board board = new Board();
            board.setTitle("제목입니다 . . " + i);
            board.setContent("내용입니다 . . " + i);
            board.setWriter("작성자입니다 . . " + i);

            boardRepository.save(board);
        }
    }

    @Test
    public void listTest(){

        List<Board> boardList = boardRepository.findAll();

        boardList.forEach(board -> log.info(board));

    }

    @Test
    public void readTest(){

        Optional<Board> optionalBoard = boardRepository.findById(88L);

        try {
            Board board = optionalBoard.orElseThrow(EntityNotFoundException::new);
            log.info(board);

        } catch (EntityNotFoundException e) {
            log.info("찾는 데이터가 없습니다.");
        }

    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void updateTest(){

        Optional<Board> optionalBoard = boardRepository.findById(88L);

        try {

            Board board = optionalBoard.orElseThrow(EntityNotFoundException::new);
            board.setTitle("수정한 제목 . . .");
            board.setContent("수정한 내용 . . .");
            board.setWriter("수정한 작성자 . . .");

        } catch (EntityNotFoundException e) {
            log.info("찾는 데이터가 없습니다.");
        }

    }

    @Test
    public void deleteTest(){

        boardRepository.deleteById(87L);

    }



}