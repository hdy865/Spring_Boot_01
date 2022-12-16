package org.zerock.mreview.repository;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;

@SpringBootTest
public class ReviewRepositoryTests {

	@Autowired
	private ReviewRepository reviewRepository;
	
	//@Test
	public void insertMovieReview() {
		//200개의 리뷰 등록
		IntStream.rangeClosed(1, 200).forEach(i -> {
			
			//영화번호
			Long mno = (long) ((Math.random() * 100) + 1);
			
			//리뷰어번호
			Long mid = (long) ((Math.random() * 100) + 1);
			Member member = Member.builder().mid(mid).build();
			
			Review movieReview = Review.builder()
					.member(member)
					.movie(Movie.builder().mno(mno).build())
					.grade((int)((Math.random() * 5) + 1))
					.text("이 영화에 대한 느낌..." + i)
					.build(); 
			
			reviewRepository.save(movieReview);
		});
	}
	
	@Test
	public void testGetMovieReview() {
		
		Movie movie = Movie.builder().mno(92L).build();
		
		List<Review> result = reviewRepository.findByMovie(movie);
		
		result.forEach(movieReview -> {
			System.out.print("\t" + movieReview.getReviewwnum()); // r
			System.out.print("\t" + movieReview.getGrade()); // r
			System.out.print("\t" + movieReview.getText()); // r
			System.out.println("\t" + movieReview.getMember().getEmail()); // m.r
			System.out.println("--------------------------------------");
			System.out.println("result : " + result);
			System.out.println("--------------------------------------");
		});
	}
}
