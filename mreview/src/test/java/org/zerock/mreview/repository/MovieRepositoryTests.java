package org.zerock.mreview.repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;

@SpringBootTest
public class MovieRepositoryTests {

	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private MovieImageRepository movieImageRepository;
	
	@Commit
	@Transactional
	//@Test
	public void insertMovies() {
		IntStream.rangeClosed(1, 100).forEach(i -> {
			Movie movie = Movie.builder()
					.title("Movie..." + i)
					.build();
			
			System.out.println("-----------------------");// movie pk 1부터 100까지 생상
			
			movieRepository.save(movie);
			
			// 0.0 ~ 1.0 결과는 1,2,3,4
			int count  = (int) ((Math.random() * 5) + 1);// movie[0]에 이미지 갯수 랜덤한값 부여 movie[99]까지 반복
			
			for(int j = 0; j < count; j++) {
				MovieImage movieImage = MovieImage.builder()
						.uuid(UUID.randomUUID().toString())
						.movie(movie)
						.imgName("test" + j + ".jpg")
						.build();
				
				movieImageRepository.save(movieImage);
				System.out.println("===================");
			}
			
			
		});
	}
	
	//@Test
	public void testListPage() {
		
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.DESC, "mno");
		
		Page<Object[]> result = movieRepository.getListPage(pageRequest);
		
		for(Object[] objects : result.getContent()) {
			System.out.println(Arrays.toString(objects));
		}
	}
	
	@Test
	public void testGetMovieWhithAll() {
		
		List<Object[]> result = movieRepository.getMovieWithAll(97L);
		
		System.out.println("result : " + result);
		
		for(Object[] arr : result) {
			System.out.println("arr : " + Arrays.toString(arr));
		}
	}
}
