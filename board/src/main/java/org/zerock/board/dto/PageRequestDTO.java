package org.zerock.board.dto;

import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {
	//화면에서 전달되는 page, size 파라미터 수집
	//Pageable 타입의 객체 생성
	//JPA의 경우 페이지 번호가 0부터 시작하니까 1페이지인 경우 0이 될 수 있도록 page - 1
	//정렬은 나중에 다양한 상황에서 쓰기 위해 별도의 파라미터로 받도록 설계
	private int page;
	private int size;
	private String type;
	private String keyword;
	
	public PageRequestDTO() {
		this.page = 1;
		this.size = 10;
	}
	
	
	
	public Pageable getPageable(Sort sort) {
		return PageRequest.of(page - 1, size, sort);
	}
}
