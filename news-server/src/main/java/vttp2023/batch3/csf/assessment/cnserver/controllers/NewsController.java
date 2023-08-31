package vttp2023.batch3.csf.assessment.cnserver.controllers;

import java.util.List;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import vttp2023.batch3.csf.assessment.cnserver.models.News;
import vttp2023.batch3.csf.assessment.cnserver.models.TagCount;
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

		try {
			String newsId = newsService.postNews(title, description, tags, image);
			String jsonResponse = String.format("{\"newsId\": \"%s\"}", newsId);
			return ResponseEntity.status(HttpStatus.SC_CREATED).body(jsonResponse);
		} catch (Exception e) {
			e.printStackTrace();
			String jsonResponse = String.format("{\"error\": \"%s\"}", e.getMessage());
			return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(jsonResponse);
		}
	}
	

	// TODO: Task 2
	@GetMapping(path = "/top-tags/{time}", produces = "application/json")
    public ResponseEntity<List<TagCount>> getTopTags(@PathVariable String time) {

        System.out.println("Received request for top tags with time: " + time);

        List<TagCount> topTags = newsService.getTags(time);
		System.out.println(String.format("Returning %d top tags", topTags.size()));

		for (TagCount tagCount: topTags) {
			System.out.println("Received tag: " + tagCount.tag() + " with count: " + tagCount.count());
		}

        return ResponseEntity.ok(topTags);
    }

	// TODO: Task 3
	@GetMapping("/tag-news")
    public ResponseEntity<List<News>> getTagNewsWithinTime(
        @RequestParam(name = "selectedTime") String selectedTime,
        @RequestParam(name = "tag") String tag
    ) {
		System.out.println("Received request for tag news with time: " + selectedTime + " and tag: " + tag);
        List<News> tagNews = newsService.getNewsByTag(selectedTime, tag);
        return ResponseEntity.ok(tagNews);
    }
}
