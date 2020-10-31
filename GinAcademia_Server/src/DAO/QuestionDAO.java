package DAO;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;

import Model.Option;
import Model.Question;

public class QuestionDAO {
	ConnectMongoDB mongo = new ConnectMongoDB(); 
	Gson gson;
	MongoCollection<Document> collection;
	public QuestionDAO() {
		// TO DO Auto-generated constructor stub
		mongo.connection();
		this.collection = mongo.database.getCollection("Question");
		gson = new Gson();
	}
	public ArrayList<Question> readAll(){
		ArrayList<Question> arr = new ArrayList<Question>();
		for(Document cur : collection.find() ) {
			Question p = gson.fromJson(cur.toJson(), Question.class);
			arr.add(p);
		}
		return arr;
	}
	// status = 0
	public ArrayList<Question> read(){
		ArrayList<Question> arr = new ArrayList<Question>();
		for(Document cur : collection.find() ) {
			Question p = gson.fromJson(cur.toJson(), Question.class);
			if( p.getStatus() == 0)
				arr.add(p);
		}
		return arr;
	}
	
	public Question getQuestionById(String id) {
		Bson filter = eq("QuestionId",id);
		Document doc = this.collection.find(filter).first();
		return gson.fromJson(doc.toJson(), Question.class);
	}
	
	private String generateID() {
		String ans = "";
		Document myDoc = (Document)collection.find().sort(new BasicDBObject("QuestionId",-1)).first();
		int last = Integer.parseInt(myDoc.getString("QuestionId"));
		ans = String.format("%05d", last+1);
		return ans;
	}
	
	public void insert(Question p) {
		p.setId(this.generateID());
		String json = this.gson.toJson(p);
		Document doc = Document.parse(json);
		this.collection.insertOne(doc);
	}
	public void delete(String id){
		Bson filter = eq("QuestionId",id);
		this.collection.deleteOne(filter);
	}
	public void active(String id){
		Bson filter = eq("QuestionId",id);
		Bson query = set("Status", 0);
		this.collection.updateOne(filter, query);
	}
	public void disable(String id){
		Bson filter = eq("QuestionId",id);
		Bson query = set("Status", 1);
		this.collection.updateOne(filter, query);
	}
	public void update(Question p){
		Bson filter = eq("QuestionId",p.getId());
		String json = this.gson.toJson(p);
		Document query = new Document();
		query.append("$set", Document.parse(json));
	    this.collection.updateOne(filter, query);
	}
	public void close() {
		this.mongo.close();
	}
	public static void main(String[] args) {
		QuestionDAO dao = new QuestionDAO();
//		// insert
		ArrayList<Option> ar = new ArrayList<Option>();
		ar.add(new Option(1,"UEF"));
		ar.add(new Option(2,"SGU"));
		ar.add(new Option(3,"STU"));
		ar.add(new Option(4,"HUTECH"));
		Question p = new Question("00001", "Đại học Sài Gòn viết tắt là gì?", ar, 2 );
		dao.insert(p);
////		
////		ArrayList<Option> ar2 = new ArrayList<Option>();
////		ar2.add(new Option(1,"C++"));
////		ar2.add(new Option(2,"C#"));
////		ar2.add(new Option(3,"Python"));
////		ar2.add(new Option(4,"Java"));
////		Question p2 = new Question("10002", "Ngôn ngữ lập trình nào bậc cao nhất trong các ngôn ngữ sau", ar2, 3 );
////		dao.insert(p2);
//		
//		ArrayList<Question> arr = dao.readAll();
//		Question q = arr.get(0);
//		q.setAnswer(3);
//		ArrayList<Option> ar = new ArrayList<Option>();
//		ar.add(new Option(1,"1"));
//		ar.add(new Option(2,"16"));
//		ar.add(new Option(3,"3"));
//		ar.add(new Option(4,"9"));
//		q.setOptions(ar);
//		q.setStatus(2);
//		dao.update(q);
//		
//		System.out.println("\nEnd");
	}

}
