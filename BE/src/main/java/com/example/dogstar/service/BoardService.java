package com.example.dogstar.service;

import com.example.dogstar.domain.Board;
import com.example.dogstar.dto.BoardDTO;
import com.example.dogstar.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor // final OR @NotNull 인 필드 생성자 추가
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public Board save(BoardDTO boardDto) {
        return boardRepository.save(boardDto.toEntity());
    }
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Board findById(Long id){ // "게시글 하나 상세 조회"
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found" + id));
    }
}
