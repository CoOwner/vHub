package rip.vexus.lobby;

import java.util.Arrays;

import lombok.Getter;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class LobbyDatabase {

    @Getter private MongoClient client;
    @Getter private MongoDatabase database;
    @Getter private MongoCollection profiles;

    public LobbyDatabase(LobbyPlugin plugin) {
        if (plugin.getDatabaseFile().getBoolean("DATABASE.MONGO.AUTHENTICATION.ENABLED")) {
            client = new MongoClient(new ServerAddress(plugin.getDatabaseFile().getString("DATABASE.MONGO.HOST"), plugin.getDatabaseFile().getInt("DATABASE.MONGO.PORT")), Arrays.asList(MongoCredential.createCredential(plugin.getDatabaseFile().getString("DATABASE.MONGO.AUTHENTICATION.USER"), plugin.getDatabaseFile().getString("DATABASE.MONGO.AUTHENTICATION.DATABASE"), plugin.getDatabaseFile().getString("DATABASE.MONGO.AUTHENTICATION.PASSWORD").toCharArray())));
        }
        else {
            client = new MongoClient(new ServerAddress(plugin.getDatabaseFile().getString("DATABASE.MONGO.HOST"), plugin.getDatabaseFile().getInt("DATABASE.MONGO.PORT")));
        }

        database = client.getDatabase(plugin.getDatabaseFile().getString("DATABASE.MONGO.DATABASE"));
        profiles = database.getCollection("profiles");
    }

}
