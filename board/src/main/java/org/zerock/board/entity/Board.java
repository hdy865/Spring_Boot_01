package org.zerock.board.entity;

import javax.persistence.*;

import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer") //@ToString은 항상 exclude
public class Board extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bno;
	
	private String title;
	
	private String content;
	
	@ManyToOne (fetch = FetchType.LAZY)
	private Member writer; //연관관계 지정(FK 부분에 지정)
	
	
	//게시글 수정을 위한 메소드
	public void changeTitle(String title) {
		this.title = title;
	}
	public void changeContent(String content) {
		this.content = content;
	}
	
}
