package orz.zerock.guestbook.repository;

import java.util.Optional;


import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

import orz.zerock.guestbook.entity.Guestbook;
import orz.zerock.guestbook.entity.QGuestbook;


@SpringBootTest
public class GuestbookRepositoryTests {

	@Autowired
	private GuestbookRepository guestbookRepository;
	
	//@Test
	public void insertDummies() {
		
		IntStream.rangeClosed(1, 300).forEach(i -> {
			
			Guestbook guestbook = Guestbook.builder()
					.title("Title..." + i)
					.content("Content..." + i)
					.writer("user" + (i % 10))
					.build();
			System.out.println(guestbookRepository.save(guestbook));
		});
	}
	
	//@Test
	public void updateTest() {
		
		Optional<Guestbook> result = guestbookRepository.findById(300L);
		
		if(result.isPresent()) {
			Guestbook guestbook = result.get();
			guestbook.changeTitle("Changed Titile...");
			guestbook.changeContent("Changed Content...");
			guestbookRepository.save(guestbook);
		}
	}
	
	//@Test
	public void testQuery1() {
		Pageable pageable = PageRequest.of(0, 10,Sort.by("gno").descending());
		
		QGuestbook qGuestbook = QGuestbook.guestbook;
		
		String keyword = "1";
		
		BooleanBuilder builder = new BooleanBuilder();
		
		BooleanExpression expression = qGuestbook.title.contains(keyword);
		
		builder.and(expression);
		
		Page<Guestbook> result = guestbookRepository.findAll(builder,pageable);
		
		result.stream().forEach(guestbook -> {System.out.println(guestbook);});
	}
	
	@Test
	public void testQuery2() {
		Pageable pageable =PageRequest.of(0,10,Sort.by("gno").descending());
		QGuestbook qGuestbook = QGuestbook.guestbook;
		String keyword="1";
		String keyword2="7";
		BooleanBuilder builder = new BooleanBuilder();
		BooleanExpression exTitle = qGuestbook.title.contains(keyword);
		BooleanExpression exContent = qGuestbook.content.contains(keyword2);
		BooleanExpression exAll = exTitle.or(exContent); //1------
		builder.and(exAll);
		builder.and(qGuestbook.gno.gt(240L)); //3-------
		Page<Guestbook> result = guestbookRepository.findAll(builder,pageable);
		
		result.stream().forEach(guestbook-> {
			System.out.println(guestbook);
		});
	}
}
