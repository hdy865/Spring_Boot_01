package orz.zerock.guestbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import orz.zerock.guestbook.entity.Guestbook;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long>{

}
