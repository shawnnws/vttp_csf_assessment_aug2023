package vttp2023.batch3.csf.assessment.cnserver.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp2023.batch3.csf.assessment.cnserver.models.News;
import vttp2023.batch3.csf.assessment.cnserver.models.TagCount;

@Repository
public class NewsRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	// TODO: Task 1 
	// Write the native Mongo query in the comment above the method

		/**
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

	public News save(News news) {

		return mongoTemplate.insert(news, "news");
	}

	// TODO: Task 2 
	// Write the native Mongo query in the comment above the method

		/**
		*	db.news.aggregate([ 
				{ $match: { postDate: { $gte: 1693460185532 } } }, 
				{ $unwind: "$tags" }, 
				{ $match: { tags: { $exists: true, $ne: null, $ne: "" } } }, 
				{ $group: { _id: "$tags", count: { $sum: 1 } } }, 
				{ $sort: { count: -1 } }, 
				{ $limit: 10 }
			])
		 */

	public List<TagCount> getTags(Long millisecondsBeforeCurrentTime) {

		Aggregation aggregation = Aggregation.newAggregation(
			Aggregation.match(Criteria.where("postDate").gte(millisecondsBeforeCurrentTime)),
			Aggregation.unwind("tags"),
			Aggregation.match(Criteria.where("tags").exists(true).ne("").ne(null)),
			Aggregation.group("tags").count().as("count"),
			Aggregation.sort(Sort.Direction.DESC, "count"),
			Aggregation.limit(10),
			Aggregation.project("count").and("_id").as("tag")
		);

		AggregationResults<TagCount> results = mongoTemplate.aggregate(aggregation, "news", TagCount.class);

        return results.getMappedResults();
	}

	// TODO: Task 3
	// Write the native Mongo query in the comment above the method
		
		/**
		db.news.find({
		 	postDate: { $gte: 1693464288693 },
			tags: "abc"
		})
		*/

	public List<News> getTagNewsWithinTime(Long selectedTime, String tag) {
		Criteria criteria = new Criteria();
		criteria.andOperator(
			Criteria.where("postDate").gte(selectedTime),
			Criteria.where("tags").is(tag)
		);
		return mongoTemplate.find(Query.query(criteria), News.class, "news");
	}
}
