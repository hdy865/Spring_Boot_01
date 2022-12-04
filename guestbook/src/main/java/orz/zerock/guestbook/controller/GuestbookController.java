package orz.zerock.guestbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import orz.zerock.guestbook.dto.PageRequestDTO;
import orz.zerock.guestbook.service.GuestbookService;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestbookController {
	
	private final GuestbookService service;
	
	@GetMapping({"/"})
	public String list() {
		log.info("list...........");
		
		return "guestbook/list";
	}
	
	@GetMapping({"/list"})
	public void list(PageRequestDTO pageRequestDTO, Model model) {
		log.info("list......." + pageRequestDTO);
		model.addAttribute("result", service.getList(pageRequestDTO));
	}
}
