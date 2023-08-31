package vttp2023.batch3.csf.assessment.cnserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import vttp2023.batch3.csf.assessment.cnserver.services.NewsService;

@RestController
@CrossOrigin(origins="*")
public class NewsController {

	@Autowired
	private NewsService newsService;

	// TODO: Task 1
	@PostMapping(path="/news", consumes = "multipart/form-data")
	public ResponseEntity<String> createNews(
		@RequestParam("title") String title,
		@RequestParam("description") String description,
		@RequestParam("image") MultipartFile image,
		@RequestParam("tags") String tags
	) {
		System.out.println("Received title: " + title);
	  	System.out.println("Received description: " + description);
	  	System.out.println("Received image filename: " + image.getOriginalFilename());
	  	System.out.println("Received tags: " + tags);
	  	return ResponseEntity.ok("OK");
	}

	// TODO: Task 2


	// TODO: Task 3

}
