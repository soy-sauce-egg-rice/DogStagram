package com.example.dogstar.controller;

import com.example.dogstar.domain.Board;
import com.example.dogstar.dto.BoardDTO;
import com.example.dogstar.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<?> addBoard(@RequestBody BoardDTO boardDto){
        Board savedBoard = boardService.save(boardDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedBoard);
    }
}
