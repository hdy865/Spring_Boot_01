package orz.zerock.guestbook.service;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import orz.zerock.guestbook.dto.GuestbookDTO;

@SpringBootTest
public class GuestbookServiceTests {

	@Autowired
	private GuestbookService service;
	//long gno = 301;
	@Test
	public void testRegister() {
		GuestbookDTO guestbookDTO = GuestbookDTO.builder()
				/*.gno(gno)*/
				.title("Sample Title")
				.content("Sample Content")
				.writer("user0")
				.build();
		
		System.out.println(service.register(guestbookDTO));
	}
}

