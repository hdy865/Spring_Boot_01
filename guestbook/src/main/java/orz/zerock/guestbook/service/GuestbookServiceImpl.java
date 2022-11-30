package orz.zerock.guestbook.service;


import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import orz.zerock.guestbook.dto.GuestbookDTO;
import orz.zerock.guestbook.dto.PageRequestDTO;
import orz.zerock.guestbook.dto.PageResultDTO;
import orz.zerock.guestbook.entity.Guestbook;
import orz.zerock.guestbook.repository.GuestbookRepository;

@Service
@Log4j2
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService {

	
	private final GuestbookRepository repository;
	@Override
	public Long register(GuestbookDTO dto) {
		log.info("DTO>>>>>>>>>>>>>>>>");
		log.info(dto);
		
		Guestbook entity = dtoToEntity(dto);
		log.info(entity);
		
		repository.save(entity);
		
		return entity.getGno();
	}
	
	@Override
	public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO){
		Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
		Page<Guestbook> result = repository.findAll(pageable);
		
		Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDTO(entity));
		return new PageResultDTO<>(result, fn);
		}

}
