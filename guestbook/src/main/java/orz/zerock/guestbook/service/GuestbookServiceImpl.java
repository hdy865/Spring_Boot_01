package orz.zerock.guestbook.service;

import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import orz.zerock.guestbook.dto.GuestbookDTO;
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

}
