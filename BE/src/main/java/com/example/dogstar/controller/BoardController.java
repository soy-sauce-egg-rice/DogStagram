package com.example.dogstar.controller;

import com.example.dogstar.domain.Board;
import com.example.dogstar.dto.BoardDTO;
import com.example.dogstar.dto.ResponseDTO;
import com.example.dogstar.service.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Request;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> findBoard(@PathVariable long id) {
        try {
            Board board = boardService.findById(id);
            ResponseDTO<Board> response = ResponseDTO.<Board>builder().board(board).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDTO response = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable long id) {
        boardService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateBoard(@PathVariable long id,
                                         @RequestBody BoardDTO boardDTO){
        Board updatedBoard = boardService.update(id, boardDTO);
        ResponseDTO<Board> response = ResponseDTO.<Board>builder().board(updatedBoard).build();
        return ResponseEntity.ok().body(response);
    }
}