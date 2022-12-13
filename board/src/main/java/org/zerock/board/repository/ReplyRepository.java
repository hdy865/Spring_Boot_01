package org.zerock.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.board.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long>{

	
	//@Param 추가해야함
	@Modifying
	@Query("delete from Reply r where r.board.bno= :bno")
	void deleteByBno(@Param("bno") Long bno);
}
