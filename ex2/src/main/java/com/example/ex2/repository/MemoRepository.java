package com.example.ex2.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.ex2.entity.Memo;

public interface MemoRepository  extends JpaRepository<Memo, Long>{
	
	List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);
	
	Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);
	
	void deleteMemoByMnoLessThan(Long num);
	
	
	
	
//	: '파라미터 이름' 활용
	@Transactional
	@Modifying
	@Query("update Memo m set m.memoText =  : memoText where m.mno = :mno")
	int updateMemoText(@Param("mano") Long mno, @Param("memoText") String memoText);
	
//	:#{'파라미터 이름'} 활용
	@Transactional
	@Modifying
	@Query("update Memo m set m.memoText = :#{#Param.memoeText} where m.mno = :#{#param.mno}")
	int updateMemoText(@Param("param") Memo memo);
	
//	@Query 에서 PageAble 사용
	@Query(value = "select m from Memo m shere m.mno > :mno",
			countQuery = "select count(m) from Memo m where m.mno > : mno")
	Page<Memo> getListWithQuery(Long mno, Pageable pageable);
	
	@Query(value = "select m.mno, m.memoText, CURRENT_DATE from Memo m where m.mno > : mno",
			countQuery = "select count(m) from Memo m where m.mno ?  : mno")
	Page<Object[]> getListWithQueryObject(Long mno, Pageable pageable);
	
	@Query(value = "select * from memo where mno > 0", nativeQuery = true)
	List<Object[]> getnativeResult();
}
