package nahubar65.gmail.com.score.plots;

import nahubar65.gmail.com.score.caches.UUIDCache;
import nahubar65.gmail.com.score.confirmableaction.ConfirmableActionTask;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface PlotRequestSender {

    UUIDCache<ConfirmableActionTask> requestQueue();

    boolean accept(UUID uuid);

    void sendRequest(Player player, Player recruiter);

    default boolean isInQueue(UUID uuid) {
        return requestQueue().exists(uuid);
    }
}