package com.example.demo.controller;

import com.example.demo.dto.BoardDTO;
import com.example.demo.entity.Board;
import com.example.demo.service.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class BoardController {

    //public final을 붙이는 이유가 있나요?? 이거 안붙이면 뷰페이지 오류가 나네요..
    public final BoardService boardService;

    @GetMapping("/boardlist")
    public String boardList(@PageableDefault(size = 10, sort = "bno", direction = Sort.Direction.DESC)  //페이지 기본 설정
                                Pageable pageable, Model model){

        Page<BoardDTO> boardDTOS = boardService.getList(pageable);

        //뷰에 보여줘야지. 뷰(html)에서 boardDTOS 라는 이름을 쓸거야
        model.addAttribute("boardDTOS", boardDTOS);

        return "boardlist";
    }

    @GetMapping("/board/{bno}")
    public String boardDetail(@PathVariable("bno") Long bno, Model model){

        BoardDTO boardDTO = boardService.boardDetail(bno);
        model.addAttribute("boardDTO", boardDTO);

        return "boarddetail";
    }


    @GetMapping("/register")
    public String register() {

        return "register";
    }

    @PostMapping("/register")
    public String registerPost(BoardDTO boardDTO){

        boardService.boardRegister(boardDTO);

        return "redirect:/boardlist";
    }

    @GetMapping("/boardedit/{bno}")
    public String edit(@PathVariable("bno") Long bno, Model model){

        BoardDTO boardDTO = boardService.boardDetail(bno);
        model.addAttribute("boardDTO", boardDTO);

        return "boardedit";
    }

    @PostMapping("/boardedit")
    public String edit(BoardDTO boardDTO){

        boardService.boardUpdate(boardDTO);
        Long bno = boardDTO.getBno();

        return "redirect:/board/" + bno;
    }

    @GetMapping("/boarddelete/{bno}")
    public String delete(@PathVariable("bno") Long bno) {

        if(bno != null) {   //bno가 null이 아니라면, 값이 있다면
            boardService.boardDelete(bno);  //삭제
        }

        return  "redirect:/boardlist";
    }


}
