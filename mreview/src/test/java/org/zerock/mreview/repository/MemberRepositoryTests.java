package org.zerock.mreview.repository;

import java.util.stream.IntStream;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview.entity.Member;

@SpringBootTest
public class MemberRepositoryTests {

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	//@Test
	public void insertMember() {
		IntStream.rangeClosed(1, 100).forEach(i -> {
			Member member = Member.builder()
					.email("r" + i + "@zerock.org")
					.pw("1234")
					.nickname("reviewer" + i)
					.build();
			
			memberRepository.save(member);
			
		});
	}
	
	
	@Commit
	@Transactional
	@Test
	public void testDeleteMember() {
		
		Long mid = 2L; // MemberId
		
		Member member = Member.builder().mid(mid).build();
		
		//fk쪽을 먼저 삭제한다.
		reviewRepository.deleteByMember(member);
		memberRepository.deleteById(mid);
		
	}
}
