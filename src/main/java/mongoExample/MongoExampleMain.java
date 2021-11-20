package mongoExample;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import mongoExample.entities.TestCollection;



public class MongoExampleMain {
	public static final String PROP_MONGO_CONNECTION = "mongo-connection-string";
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Properties props = new Properties();
		try {
			String configFilePathName = 
					new File(MongoExampleMain.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getAbsolutePath()
					+
					"/properties.properties";
			props.load(new FileInputStream(new File(configFilePathName)));
		}catch(Exception e) {
			System.out.println("----------\ncan't find file properties.properties in the JAR's directory,\nresource from JAR file used\n------------");
			props.load(MongoExampleMain.class.getResourceAsStream("/properties.properties"));
		}
//		String configFilePathName = "src/main/resources/properties.properties";
		//mongodb+srv://root:root1@cluster0-4yzu8.mongodb.net/storehouse?retryWrites=true&w=majority
		String mongoConnectionString = props != null && props.getProperty(PROP_MONGO_CONNECTION)!= null 
				? props.getProperty("mongo-connection-string")
				:null;
		if (mongoConnectionString == null) {
			System.err.println("Can't get mongo connection string ");
			return;
		}
		
		ConnectionString connectionString = new ConnectionString(mongoConnectionString);
//		ConnectionString connectionString = new ConnectionString("mongodb+srv://root:root1@cluster0.4yzu8.mongodb.net/testdb?retryWrites=true&w=majority");
		MongoClientSettings settings = MongoClientSettings.builder()
		        .applyConnectionString(connectionString)
		        .codecRegistry(getCodecRegistry())
		        .build();
		MongoClient mongoClient = MongoClients.create(settings);
		MongoDatabase database = mongoClient.getDatabase("testdb");
		try {
			database.createCollection("testcollection");
		}catch(Exception ex) {
			
		}
		MongoCollection<Document> mongoCollection =  database.getCollection("testcollection");
		TestCollection testCollection = new TestCollection();
		testCollection.setTestIntField(0);
		testCollection.setTestStringField("String field 1");
		Document document = null;

		document = new Document();
		document.put("_id", "id_".concat( String.valueOf( System.nanoTime()) ) );
		document.put("testStringField", testCollection.getTestStringField());
		document.put("testIntField", testCollection.getTestIntField());
		mongoCollection.insertOne(document);

//		document = new Document();
//		document.put("testStringField", testCollection.getTestStringField());
//		document.put("testIntField", testCollection.getTestIntField());
//		mongoCollection.insertOne(document);

//		document = new Document("id0", testCollection);
//		mongoCollection.insertOne(document);
	}
    private static CodecRegistry getCodecRegistry() {
        return fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    }


}
