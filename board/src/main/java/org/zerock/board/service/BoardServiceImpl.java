package org.zerock.board.service;


import java.util.function.Function;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;
import org.zerock.board.repository.BoardRepository;
import org.zerock.board.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{

	private final BoardRepository repository;
	
	private final ReplyRepository replyRepository;
	
	@Override
	public Long register(BoardDTO dto) {
		
		log.info(dto);
		
		Board board = dtoToEntity(dto);
		
		repository.save(board);
		
		return board.getBno();
	}

	@Override
	public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
		
		log.info(">>>pageRequestDTO : " + pageRequestDTO);
		
		//엔티티 객체들을 DTO리스트로 변환
		Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board)en[0],(Member)en[1],(Long)en[2]));
		
		//화면 페이지 전환에 필요한 값 생성
		//Page<Object[]> result = repository.getBoardWithReplyCount(pageRequestDTO.getPageable(Sort.by("bno").descending()));
		
		Page<Object[]> result = repository.searchPage(
				pageRequestDTO.getType(), 
				pageRequestDTO.getKeyword(), 
				pageRequestDTO.getPageable(Sort.by("bno").descending()));
		
		return new PageResultDTO<>(result, fn);
	}

	@Override
	public BoardDTO get(Long bno) {
		Object result = repository.getBoardByBno(bno);
		
		Object[] arr = (Object[])result;
		
		return entityToDTO((Board)arr[0], (Member)arr[1], (Long)arr[2]);
	}

	@Transactional
	@Override
	public void removeWithReplies(Long bno) {
		
		//댓글삭제
		replyRepository.deleteByBno(bno);
		
		//게시글삭제
		repository.deleteById(bno);
	}
	
	@Transactional
	@Override
	public void modify(BoardDTO boardDTO) {
		
		Board board = repository.getOne(boardDTO.getBno());
		
		board.changeTitle(boardDTO.getTitle());
		board.changeContent(boardDTO.getContent());
		
		repository.save(board);
	}

}
