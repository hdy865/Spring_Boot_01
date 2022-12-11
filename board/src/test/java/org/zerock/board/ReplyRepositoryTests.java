package org.zerock.board;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;
import org.zerock.board.repository.ReplyRepository;

@SpringBootTest
public class ReplyRepositoryTests {

	@Autowired
	private ReplyRepository replyRepository;
	
	@Test
	public void insertReply() {
		
		IntStream.rangeClosed(1, 300).forEach(i -> {
			//0.0~0.09 : 0~9까지
			//0.1~0.19 : 10~19까지
			long bno = (long)(Math.random() * 100) + 1;
			
			Board board = Board.builder().bno(bno).build();
			
			Reply reply = Reply.builder()
					.text("Reply..." + i)//1부터 300까지의 댓글
					.board(board)// bno가 1~100까지의 중복있는 랜덤값 (board_bno)
					.replyer("guest")
					.build();
			replyRepository.save(reply);
		});
	}
}
