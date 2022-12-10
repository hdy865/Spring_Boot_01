package orz.zerock.guestbook.service;

import orz.zerock.guestbook.dto.GuestbookDTO;
import orz.zerock.guestbook.dto.PageRequestDTO;
import orz.zerock.guestbook.dto.PageResultDTO;
import orz.zerock.guestbook.entity.Guestbook;

public interface GuestbookService {
	
	GuestbookDTO read(Long gno);
	
	Long register(GuestbookDTO dto);
	
	void remove(Long gno);
	
	void modify(GuestbookDTO dto);
	
	default Guestbook dtoToEntity(GuestbookDTO dto) {
		
		Guestbook entity = Guestbook.builder()
				.gno(dto.getGno())
				.title(dto.getTitle())
				.content(dto.getContent())
				.writer(dto.getWriter())
				.build();
	
		return entity;
	}
	
	default GuestbookDTO entityToDTO(Guestbook entity) {
		GuestbookDTO dto = GuestbookDTO.builder()
		.gno(entity.getGno())
		.title(entity.getTitle())
		.content(entity.getContent())
		.writer(entity.getWriter())
		.regDate(entity.getRegDate())
		.modDate(entity.getModDate())
		.build();
		return dto;
	}

	PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO);
}