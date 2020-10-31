package Server.DAO;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;

//import Model.Option;
import Model.IQTest;

public class IQTestDAO {
	ConnectMongoDB mongo = new ConnectMongoDB(); 
	Gson gson;
	MongoCollection<Document> collection;
	public IQTestDAO() {
		// TO DO Auto-generated constructor stub
		mongo.connection();
		this.collection = mongo.database.getCollection("IQTest");
		gson = new Gson();
	}
	public ArrayList<IQTest> readAll(){
		ArrayList<IQTest> arr = new ArrayList<IQTest>();
		for(Document cur : collection.find() ) {
			IQTest p = gson.fromJson(cur.toJson(), IQTest.class);
			arr.add(p);
		}
		return arr;
	}
	// status = 0
	public ArrayList<IQTest> read(){
		ArrayList<IQTest> arr = new ArrayList<IQTest>();
		for(Document cur : collection.find() ) {
			IQTest p = gson.fromJson(cur.toJson(), IQTest.class);
			if( p.getStatus() == 0)
				arr.add(p);
		}
		return arr;
	}
	
	public IQTest getIQTestById(String id) {
		Bson filter = eq("IQTestId",id);
		Document doc = this.collection.find(filter).first();
		return gson.fromJson(doc.toJson(), IQTest.class);
	}
	
	private String generateID() {
		String ans = "";
		Document myDoc = (Document)collection.find().sort(new BasicDBObject("IQTestId",-1)).first();
		int last = Integer.parseInt(myDoc.getString("IQTestId"));
		ans = String.format("%05d", last+1);
		return ans;
	}
	
	public void insert(IQTest p) {
		p.setId(this.generateID());
		String json = this.gson.toJson(p);
		Document doc = Document.parse(json);
		this.collection.insertOne(doc);
	}
	public void delete(String id){
		Bson filter = eq("IQTestId",id);
		this.collection.deleteOne(filter);
	}
	public void active(String id){
		Bson filter = eq("IQTestId",id);
		Bson query = set("Status", 0);
		this.collection.updateOne(filter, query);
	}
	public void disable(String id){
		Bson filter = eq("IQTestId",id);
		Bson query = set("Status", 1);
		this.collection.updateOne(filter, query);
	}
	public void update(IQTest p){
		Bson filter = eq("IQTestId",p.getId());
		String json = this.gson.toJson(p);
		Document query = new Document();
		query.append("$set", Document.parse(json));
	    this.collection.updateOne(filter, query);
	}
	public void close() {
		this.mongo.close();
	}

}
