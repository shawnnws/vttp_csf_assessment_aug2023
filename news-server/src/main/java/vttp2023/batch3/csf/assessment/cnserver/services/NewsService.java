package vttp2023.batch3.csf.assessment.cnserver.services;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vttp2023.batch3.csf.assessment.cnserver.models.News;
import vttp2023.batch3.csf.assessment.cnserver.models.TagCount;
import vttp2023.batch3.csf.assessment.cnserver.repositories.ImageRepository;
import vttp2023.batch3.csf.assessment.cnserver.repositories.NewsRepository;

@Service
public class NewsService {

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private NewsRepository newsRepository;
	
	// TODO: Task 1
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns the news id
	public String postNews(String title, String description, String tags, MultipartFile image) throws IOException {
		String url = imageRepository.upload(title, description, image);
		List<String> tagList = deserializeTags(tags);
		News news = new News();
		news.setId(ObjectId.get().toString());
		news.setPostDate(System.currentTimeMillis());
		news.setTitle(title);
		news.setDescription(description);
		news.setImage(url);
		news.setTags(tagList);
		return news.getId();
	}

	public List<String> deserializeTags(String tags) {
		List<String> tagList = new LinkedList<>();
		tags = tags.substring(1, tags.length() - 1);
		String[] tagsArray = tags.split(",");
		for (String tag : tagsArray) {
			tagList.add(tag.trim());
		}
		return tagList;
	}
	 
	// TODO: Task 2
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns a list of tags and their associated count
	public List<TagCount> getTags(String time) {

		Long millisecondsBeforeCurrentTime = System.currentTimeMillis() - serializeTimeToMilliseconds(time);
		return newsRepository.getTags(millisecondsBeforeCurrentTime);
	}

	// TODO: Task 3
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns a list of news
	public List<News> getNewsByTag(String selectedTime, String tag) {
		Long millisecondsBeforeCurrentTime = System.currentTimeMillis() - serializeTimeToMilliseconds(selectedTime);
		System.out.println(String.format("Milliseconds before current time: %d", millisecondsBeforeCurrentTime));
		return newsRepository.getTagNewsWithinTime(millisecondsBeforeCurrentTime, tag);
	}
	
	
	public Long serializeTimeToMilliseconds(String time) {
		/**
		 * Given a possible String
		 * "5" -> Long 5 * 60 * 1000 Milliseconds
		 * "15" -> Long 15 * 60 * 1000 Milliseconds
		 * "30" -> Long 30 * 60 * 1000 Milliseconds
		 * "45" -> Long 45 * 60 * 1000 Milliseconds
		 * "60" -> Long 60 * 60 * 1000 Milliseconds
		 * 
		 * Parse the number of minutes from the string with regex, then multiply by 60 * 1000
		 */
		Long minutes = Long.parseLong(time.trim());
		return minutes * 60 * 1000;
	}
}
