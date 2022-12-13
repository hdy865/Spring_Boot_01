package org.zerock.board.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Data;

@Data
public class PageResultDTO<DTO, EN> {
	//엔티티 객체를 DTO객체로 변환해서 자료구조로 담아주어야함
	//화면 출력에 필요한 페이지 정보들을 구성해 주어야 함

	//DTO리스트
	private List<DTO> dtoList;
	
	//총 페이지 번호
	private int totalPage;
	
	//현재 페이지 번호
	private int page;
	//목록 사이즈
	private int size;
	
	//시작 페이지 번호, 끝 페이지 번호
	private int start, end;
	
	//이전, 다음
	private boolean prev, next;
	
	private List<Integer> pageList;
	
	//페이지 번호 목록 
	public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
		dtoList = result.stream().map(fn).collect(Collectors.toList());
		
		totalPage = result.getTotalPages();
		makePageList(result.getPageable());
	}

	private void makePageList(Pageable pageable) {
		this.page = pageable.getPageNumber() + 1;//0부터 시작하니까?
		this.size = pageable.getPageSize();
		
		//끝번호 - 1페이지 Math.ceil(1/10.0) * 10, tempEnd = 10
		//2페이지 Math.ceil(2/10.0) * 10, tempEnd = 10
		//11페이지 Math.ceil(11/10.0) *10, tempEnd = 20
		int tempEnd = (int)(Math.ceil(page/10.0)) * 10;
		
		start = tempEnd - 9;
		prev = start > 1;
		next = totalPage > tempEnd;
		
		//총 페이지가 끝 페이지 보다 크면 end = tempEnd
		//총 페이지가 끝 페이지 보다 작으면 end = totalPage
		end = totalPage > tempEnd ? tempEnd : totalPage;
		
		
		pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
	}
}
