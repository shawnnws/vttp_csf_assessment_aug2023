package vttp2023.batch3.csf.assessment.cnserver.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import vttp2023.batch3.csf.assessment.cnserver.models.News;

@Repository
public class NewsRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	// TODO: Task 1 
	// Write the native Mongo query in the comment above the method
	public News save(News news) {
	/**
		 * Saves the news object into the database
		 * 
		 * {
		 * 	"_id": ObjectId("..."),
		 * 	"postDate": <long>,
		 * 	"title": <string>,
		 * 	"description": <string>,
		 * 	"image": <string>,
		 * 	"tags": <array of strings>
		 * }
		 */
		return mongoTemplate.insert(news, "news");
	}

	// TODO: Task 2 
	// Write the native Mongo query in the comment above the method


	// TODO: Task 3
	// Write the native Mongo query in the comment above the method


}
