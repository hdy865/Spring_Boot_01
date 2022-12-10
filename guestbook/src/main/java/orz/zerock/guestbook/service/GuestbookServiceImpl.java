package orz.zerock.guestbook.service;


import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import orz.zerock.guestbook.dto.GuestbookDTO;
import orz.zerock.guestbook.dto.PageRequestDTO;
import orz.zerock.guestbook.dto.PageResultDTO;
import orz.zerock.guestbook.entity.Guestbook;
import orz.zerock.guestbook.entity.QGuestbook;
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
		//getList(PageRequestDTO pageRequestDTO)메소드
		Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
		
		BooleanBuilder booleanBuilder = getSearch(requestDTO);
		
		Page<Guestbook> result = repository.findAll(booleanBuilder, pageable);
		
		//entity를 
		Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDTO(entity));
		
		return new PageResultDTO<>(result, fn);
	}

	@Override
	public GuestbookDTO read(Long gno) {
		Optional<Guestbook> result = repository.findById(gno);
		return result.isPresent() ? entityToDTO(result.get()) : null;
	}

	@Override
	public void remove(Long gno) {
		repository.deleteById(gno);
		
	}

	@Override
	public void modify(GuestbookDTO dto) {
		
		Optional<Guestbook> result = repository.findById(dto.getGno());
		
		if(result.isPresent()) {
			Guestbook entity = result.get();
			entity.changeTitle(dto.getTitle());
			entity.changeContent(dto.getContent());
			repository.save(entity);
		}
	}
	
	private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
		String type = requestDTO.getType();
		
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		
		QGuestbook qGuestbook = QGuestbook.guestbook;
		
		String keyword = requestDTO.getKeyword();
		
		BooleanExpression expression = qGuestbook.gno.gt(0L); // gno > 0 조건만 생성
		
		booleanBuilder.and(expression);
		
		if(type == null || type.trim().length() == 0) { // 검색조건이 없는경우
			return booleanBuilder;
		}
		
		BooleanBuilder conditionBuilder = new BooleanBuilder();
		
		if(type.contains("t")) {
			conditionBuilder.or(qGuestbook.title.contains(keyword));
		}
		if(type.contains("c")) {
			conditionBuilder.or(qGuestbook.content.contains(keyword));
		}
		if(type.contains("w")) {
			conditionBuilder.or(qGuestbook.writer.contains(keyword));
		}
		//모든 조건 포함
		booleanBuilder.and(conditionBuilder);
		return booleanBuilder;
	}

}
