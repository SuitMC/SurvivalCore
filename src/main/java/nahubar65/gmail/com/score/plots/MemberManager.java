package nahubar65.gmail.com.score.plots;

import nahubar65.gmail.com.score.utils.Utils;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.*;

public interface MemberManager extends ConfigurationSerializable {

    UUID getOwner();

    void makeOwner(UUID uuid);

    List<UUID> getSubOwners();

    List<UUID> getMembers();

    default List<UUID> all() {
        List<UUID> all = new ArrayList<>();
        all.add(getOwner());
        getSubOwners().forEach(subOwner -> all.add(subOwner));
        getMembers().forEach(member -> all.add(member));
        return all;
    }

    default boolean isOwner(UUID uuid) {
        return uuid.equals(getOwner());
    }

    default boolean isSubOwner(UUID uuid) {
        return getSubOwners().contains(uuid);
    }

    default boolean isMember(UUID uuid) {
        return getMembers().contains(uuid);
    }

    default void makeMember(UUID uuid) {
        if(!isMember(uuid))
            getMembers().add(uuid);
    }

    default void makeSubOwner(UUID uuid) {
        if(!isSubOwner(uuid))
            getSubOwners().add(uuid);
    }

    default void removeMember(UUID uuid){
        getMembers().remove(uuid);
    }

    default void removeSubOwner(UUID uuid){
        getSubOwners().remove(uuid);
    }

    default boolean isOwner(Player player) {
        return isOwner(player.getUniqueId());
    }

    default boolean isSubOwner(Player player){
        return isSubOwner(player.getUniqueId());
    }

    default boolean isMember(Player player) {
        return isMember(player.getUniqueId());
    }

    default void makeMember(Player player) {
        makeMember(player.getUniqueId());
    }

    default void makeSubOwner(Player player){
        makeSubOwner(player.getUniqueId());
    }

    default void makeOwner(Player player) {
        makeOwner(player.getUniqueId());
    }

    default void removeMember(Player player){
        removeMember(player.getUniqueId());
    }

    default void removeSubOwner(Player player){
        removeSubOwner(player.getUniqueId());
    }

    default boolean exists(UUID uuid){
        return isMember(uuid) || isSubOwner(uuid) || isOwner(uuid);
    }

    @Override
    default Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("owner", getOwner().toString());
        map.put("subowners", Utils.stringListFromUuid(getSubOwners()));
        map.put("members", Utils.stringListFromUuid(getMembers()));
        return map;
    }

    static MemberManager deserialize(Map<String, Object> objectMap){
        List<String> subOwners = (List<String>) objectMap.get("subowners");
        List<String> members = (List<String>) objectMap.get("members");
        String owner = (String) objectMap.get("owner");
        return new SimpleMemberManager(Utils.uuidListFromString(members), Utils.uuidListFromString(subOwners), UUID.fromString(owner));
    }

    static MemberManager newMemberManager(UUID uuid){
        return new SimpleMemberManager(uuid);
    }
}