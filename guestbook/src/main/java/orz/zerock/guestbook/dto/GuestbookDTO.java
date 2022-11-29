package orz.zerock.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GuestbookDTO {

	//private Long gno;
	private String title;
	private String content;
	private String writer;
	//private LocalDateTime regDate, modDate;
	//gno와 date는 자동 입력되니까 dto에 담지 않아도 됨
}
