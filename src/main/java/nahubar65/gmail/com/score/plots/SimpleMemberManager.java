package nahubar65.gmail.com.score.plots;

import java.util.*;

public class SimpleMemberManager implements MemberManager {

    private UUID owner;

    private List<UUID> members;

    private List<UUID> subOwners;

    public SimpleMemberManager(List<UUID> members, List<UUID> subOwners, UUID owner) {
        this.owner = owner;
        this.subOwners = subOwners;
        this.members = members;
    }

    public SimpleMemberManager(UUID uuid){
        this(new ArrayList<>(), new ArrayList<>(), uuid);
    }

    @Override
    public UUID getOwner() {
        return owner;
    }

    @Override
    public void makeOwner(UUID uuid) {
        this.owner = uuid;
    }

    @Override
    public List<UUID> getSubOwners() {
        return subOwners;
    }

    @Override
    public List<UUID> getMembers() {
        return members;
    }
}