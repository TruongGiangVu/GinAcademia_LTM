package Server.DAO;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class ConnectMongoDB {
	String host= "localhost";
	int port = 27017;
	MongoClient mongoClient;
	String databaseName = "GinAcademiaDB";
	MongoDatabase database;
	public ConnectMongoDB() {
		// TO DO Auto-generated constructor stub
	}
	public ConnectMongoDB(String host, int port, String database) {
		this.host = host;
		this.port = port;
		this.databaseName = database;
	}
	public void connection() {
		Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
		mongoClient = new MongoClient(this.host, this.port);
		database = mongoClient.getDatabase(this.databaseName);
	}
	public void close() {
		this.mongoClient.close();
	}
}
