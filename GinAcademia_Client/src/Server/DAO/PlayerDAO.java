package Server.DAO;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Model.Player;

public class PlayerDAO {
	ConnectMongoDB mongo = new ConnectMongoDB(); 
	Gson gson;
	MongoCollection<Document> collection;
	
	public PlayerDAO() {
		mongo.connection();
		this.collection = mongo.database.getCollection("Player");
		gson = new GsonBuilder()
		        .setDateFormat("E MMM dd HH:mm:ss Z yyyy")
		        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
		        .create();
	}
	
	public ArrayList<Player> readAll(){
		ArrayList<Player> arr = new ArrayList<Player>();
		for(Document cur : collection.find() ) {
			Player p = gson.fromJson(cur.toJson(), Player.class);
			arr.add(p);
		}
		return arr;
	}
	// status = 0
	public ArrayList<Player> read(){
		ArrayList<Player> arr = new ArrayList<Player>();
		for(Document cur : collection.find() ) {
			Player p = gson.fromJson(cur.toJson(), Player.class);
			if( p.getStatus() == 0)
				arr.add(p);
		}
		return arr;
	}
	public Player getPlayerById(String id) {
		Bson filter = eq("PlayerId",id);
		Document doc = this.collection.find(filter).first();
		return gson.fromJson(doc.toJson(), Player.class);
	}
	
	private String generateID() {
		String ans = "";
		Document myDoc = (Document)collection.find().sort(new BasicDBObject("PlayerId",-1)).first();
		int last = Integer.parseInt(myDoc.getString("PlayerId"));
		ans = String.format("%05d", last+1);
		return ans;
	}
	public void insert(Player p) {
		p.setId(this.generateID());
		String json = this.gson.toJson(p);
		Document doc = Document.parse(json);
		this.collection.insertOne(doc);
	}
	
	public void delete(String id){
		Bson filter = eq("PlayerId",id);
		this.collection.deleteOne(filter);
	}
	
	public void active(String id){
		Bson filter = eq("PlayerId",id);
		Bson query = set("Status", 0);
		this.collection.updateOne(filter, query);
	}
	
	public void disable(String id){
		Bson filter = eq("PlayerId",id);
		Bson query = set("Status", 1);
		this.collection.updateOne(filter, query);
	}
	
	public void update(Player p){
		Bson filter = eq("PlayerId",p.getId());
		String json = this.gson.toJson(p);
		Document query = new Document();
		query.append("$set", Document.parse(json));
	    this.collection.updateOne(filter, query);
	}
	
	public void close() {
		this.mongo.close();
	}
	public static void main(String[] args) {
		// TO DO Auto-generated method stub
		PlayerDAO dao = new PlayerDAO();

		Player a = new Player("00001","Lê Cao Thanh Huyền","user4","12345","abcl@gmail.com","05/06/2000",true);
		dao.insert(a);

	}
}
