package com.example.dogstar.controller;

import com.example.dogstar.domain.Board;
import com.example.dogstar.dto.BoardDTO;
import com.example.dogstar.dto.ResponseDTO;
import com.example.dogstar.service.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<?> addBoard(@RequestBody BoardDTO boardDto) {
        try {
            if (boardDto == null || boardDto.getMemberId() == null) throw new RuntimeException("check your request");
            Board savedBoard = boardService.save(boardDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(savedBoard);
        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping // db board 에 있는 모든 게시글 조회
    public ResponseEntity<?> findAllBoards() {
        List<Board> boaorList = boardService.findAll();
        ResponseDTO<Board> response = ResponseDTO.<Board>builder().data(boaorList).build();
        return ResponseEntity.ok().body(response);
    }
}
