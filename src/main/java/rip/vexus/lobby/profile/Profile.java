package rip.vexus.lobby.profile;

import static com.mongodb.client.model.Filters.eq;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

import org.bson.Document;
import org.bukkit.entity.Player;

import rip.vexus.lobby.LobbyPlugin;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;

@Getter
public class Profile {

    @Getter private static LobbyPlugin main = LobbyPlugin.getPlugin();
    @Getter private static MongoCollection collection = main.getSuiteDatabase().getProfiles();
    @Getter private static Map<UUID, Profile> profiles = new HashMap<>();

    private UUID uuid;
    private Map<ProfileProtectionLifeType, Integer> lives;
    @Setter private String name;
    @Setter private ProfileDeathban deathban;

    public Profile(UUID uuid) {
        this.uuid = uuid;
       
        this.lives = new HashMap<>();

      
        for (ProfileProtectionLifeType type : ProfileProtectionLifeType.values()) {
            this.lives.put(type, 0);
        }

        load();

        profiles.put(uuid, this);
    }

    
    

    public ProfileDeathban getDeathban() {
    	load();
        if (this.deathban != null && (this.deathban.getCreatedAt() + this.deathban.getDuration() <= System.currentTimeMillis())) {
            this.deathban = null;
        }

        return this.deathban;
    }


    public void save() {
        Document document = new Document();
        document.put("uuid", uuid.toString());

        if (this.name != null) {
            document.put("recentName", this.name);
            document.put("recentNameLowercase", this.name.toLowerCase());
        }

 
        Document livesDocument = new Document();

        for (ProfileProtectionLifeType type : this.lives.keySet()) {
            int amount = this.lives.get(type);

            if (amount > 0) {
                livesDocument.put(type.name().toLowerCase(), amount);
            }
        }

        if (!(livesDocument.isEmpty())) {
            document.put("lives", livesDocument);
        }

        if (this.deathban != null) {
            Document deathbanDocument = new Document();
            deathbanDocument.put("createdAt", this.deathban.getCreatedAt());
            deathbanDocument.put("duration", this.deathban.getDuration());
            document.put("deathban", deathbanDocument);
        }

        collection.replaceOne(eq("uuid", this.uuid.toString()), document, new UpdateOptions().upsert(true));

        load();
    }

    private void load() {
        Document document = (Document) collection.find(eq("uuid", uuid.toString())).first();

        if (document != null) {
            if (document.containsKey("recentName")) {
                this.name = document.getString("recentName");
            }

            if (document.containsKey("deathban")) {
                Document deathbanDocument = (Document) document.get("deathban");
                this.deathban = new ProfileDeathban(deathbanDocument.getLong("createdAt"), deathbanDocument.getLong("duration"));
            }

            if (document.containsKey("lives")) {
                Document livesDocument = (Document) document.get("lives");

                for (String key : livesDocument.keySet()) {
                    this.lives.put(ProfileProtectionLifeType.valueOf(key.toUpperCase()), livesDocument.getInteger(key));
                }
            }

           
        }
    }



    public static Profile getByName(String name) {
        for (Profile profile : profiles.values()) {
            if (profile.getName() != null && profile.getName().equals(name)) {
                return profile;
            }
        }

        Document document = (Document) collection.find(eq("recentNameLowercase", name.toLowerCase())).first();

        if (document != null) {
            return new Profile(UUID.fromString(document.getString("uuid")));
        }

        return null;
    }

    public static Profile getByUuid(UUID uuid) {
        Profile profile = profiles.get(uuid);

        if (profile == null) {
            profile = new Profile(uuid);
        }

        return profile;
    }

    public static Profile getByPlayer(Player player) {
        Profile profile = profiles.get(player.getUniqueId());

        if (profile == null) {
            profile = new Profile(player.getUniqueId());
        }

        return profile;
    }

    public static Map<UUID, Profile> getProfilesMap() {
        return profiles;
    }

    public static Collection<Profile> getProfiles() {
        return profiles.values();
    }

}