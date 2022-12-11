package org.zerock.board;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;
import org.zerock.board.repository.BoardRepository;

@SpringBootTest
public class BoardRepositoryTests {

	@Autowired
	private BoardRepository boardRepository;
	
	@Test
	public void insertBoard() {
		
		IntStream.rangeClosed(1, 100).forEach(i -> {
			
			//writer(member)로  Member의 email과 같은값 넣어주기
			Member member = Member.builder().email("user" + i + "@aaa.com").build();
			
			Board board = Board.builder()
					.title("Title..." + i)
					.content("Content..." + i)
					.writer(member)
					.build();
			
			boardRepository.save(board);
		});
	}
}
