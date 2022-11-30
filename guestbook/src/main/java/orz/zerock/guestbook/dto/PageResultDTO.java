package orz.zerock.guestbook.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class PageResultDTO<DTO, EN> {
	//엔티티 객체를 DTO객체로 변환해서 자료구조로 담아주어야함
	//화면 출력에 필요한 페이지 정보들을 구성해 주어야 함
	private List<DTO> dtoList;
	
	public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
		dtoList = result.stream().map(fn).collect(Collectors.toList());
	}
}
