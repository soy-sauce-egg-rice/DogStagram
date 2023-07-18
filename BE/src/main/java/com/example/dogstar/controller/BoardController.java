package com.example.dogstar.controller;

import com.example.dogstar.domain.Board;
import com.example.dogstar.dto.BoardDto;
import com.example.dogstar.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("board")
public class BoardController {
//    @GetMapping
//    public List<>;
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<Board> addBoard(@RequestBody BoardDto boardDto){
        Board savedBoard = boardService.save(boardDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedBoard);
    }
}
