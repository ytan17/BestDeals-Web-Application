import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;


public class MongoDBDataStoreUtilities{
	private static final Object reviewText = null;
	static DBCollection myReviews;
	public static void getConnection(){
		MongoClient mongo;
		mongo = new MongoClient("localhost", 27017);

		DB db = mongo.getDB("CustomerReviews");
		myReviews = db.getCollection("myReviews");
	}
	
	public static HashMap<String, ArrayList<Review>> selectReview()
	{
		getConnection();
		HashMap<String, ArrayList<Review>> reviewHashmap=new HashMap<String, ArrayList<Review>>();
		DBCursor cursor = myReviews.find();
		while (cursor.hasNext())
		{
			BasicDBObject obj = (BasicDBObject) cursor.next();
			if(! reviewHashmap.containsKey(obj.getString("productName")))
			{
				ArrayList<Review> arr = new ArrayList<Review>();
				reviewHashmap.put(obj.getString("productName"), arr);
			}
			ArrayList<Review> listReview = reviewHashmap.get(obj.getString("productName"));
			Review review =new Review(obj.getString("productName"),obj.getString("userName"),obj.getString("userAge"),
					obj.getString("userGender"), obj.getString("userOccupation"),
					obj.getString("retailerZip"),obj.getString("retailerCity"),obj.getString("retailerState"),
					obj.getString("productOnSale"),obj.getString("manufaturerRebate"),
					obj.getInt("reviewRate"),obj.getString("reviewDate"),obj.getString("reviewText"));
			listReview.add(review);
		}
		return reviewHashmap;
	}
	
	public static void insertReview(String productName, String userName, String userAge, String userGender,
			String userOccupation, String retailerZip, String retailerCity, String retailerState, String productOnSale,
			Object manufaturerRebate, int reviewRate, String reviewDate, String reviewText) {
		getConnection();
		Object producttype;
		BasicDBObject doc = new BasicDBObject("title", "myReviews").
				append("productName", productName).
				append("userName", userName).
				append("userAge",userAge).
				append("userGender",userGender).
				append("userOccupation",userOccupation).
				append("retailerZip",retailerZip).
				append("retailerCity",retailerCity).
				append("retailerState",retailerState).
				append("productOnSale",productOnSale).
				append("manufaturerRebate",manufaturerRebate).
				append("reviewRate", reviewRate).
				append("reviewDate", reviewRate).
				append("reviewText", reviewText);
				myReviews.insert(doc);
		
	}
	
//	db.myReviews.aggregate([{$group: {aveRating:{$avg:"$reviewRate"}, _id:"$productName"}},
//	                        {$sort: {"aveRating":-1}},{$limit: 5}])
	
	public static AggregationOutput rateAggregate(){
		getConnection();
		BasicDBObject groupFields = new BasicDBObject("_id",0);
		groupFields.put("aveRating", new BasicDBObject("$avg","$reviewRate"));
		groupFields.put("_id","$productName");
		BasicDBObject group = new BasicDBObject("$group",groupFields);
		BasicDBObject sort = new BasicDBObject();
		BasicDBObject projectFields = new BasicDBObject("_id",0);
		projectFields.put("value","$_id");
		projectFields.put("ReviewValue", "$aveRating");
		BasicDBObject project = new BasicDBObject("$project",projectFields);
		sort.put("ReviewValue", -1);
		BasicDBObject orderby = new BasicDBObject("$sort",sort);
		BasicDBObject limit = new BasicDBObject("$limit",5);
		AggregationOutput aggregate = myReviews.aggregate(group,project,orderby,limit);
		return aggregate;
	}
	
	
//	 db.myReviews.aggregate([{$group: {count:{$sum:1}, _id:"$retailerZip"}}])
	public static AggregationOutput zipAggregate(){
		getConnection();
		BasicDBObject groupFields = new BasicDBObject("_id",0);
		groupFields.put("count", new BasicDBObject("$sum",1));
		groupFields.put("_id","$retailerZip");
		BasicDBObject group = new BasicDBObject("$group",groupFields);
		BasicDBObject sort = new BasicDBObject();
		BasicDBObject projectFields = new BasicDBObject("_id",0);
		projectFields.put("value","$_id");
		projectFields.put("ReviewValue", "$count");
		BasicDBObject project = new BasicDBObject("$project",projectFields);
		sort.put("ReviewValue", -1);
		BasicDBObject orderby = new BasicDBObject("$sort",sort);
		BasicDBObject limit = new BasicDBObject("$limit",5);
		AggregationOutput aggregate = myReviews.aggregate(group,project,orderby,limit);
		return aggregate;
	}
	
	public static AggregationOutput productReviewAggregate(){
		getConnection();
		BasicDBObject groupFields = new BasicDBObject("_id",0);
		groupFields.put("count", new BasicDBObject("$sum",1));
		groupFields.put("_id","$productName");
		BasicDBObject group = new BasicDBObject("$group",groupFields);
		BasicDBObject sort = new BasicDBObject();
		BasicDBObject projectFields = new BasicDBObject("_id",0);
		projectFields.put("value","$_id");
		projectFields.put("ReviewValue", "$count");
		BasicDBObject project = new BasicDBObject("$project",projectFields);
		sort.put("ReviewValue", -1);
		BasicDBObject orderby = new BasicDBObject("$sort",sort);
		BasicDBObject limit = new BasicDBObject("$limit",5);
		AggregationOutput aggregate = myReviews.aggregate(group,project,orderby,limit);
		return aggregate;
	}

	
	
	
}