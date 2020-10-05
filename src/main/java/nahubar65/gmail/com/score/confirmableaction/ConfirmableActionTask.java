package nahubar65.gmail.com.score.confirmableaction;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ConfirmableActionTask extends ConfirmableAction implements Runnable {

    private int cooldown;

    private int taskID;

    private boolean cancel;

    @Override
    public void run() {
        if(this.cancel) {
            Bukkit.getScheduler().cancelTask(taskID);
            orElse();
            return;
        }
        if (cooldown <= 0)
            cancel = true;
        cooldown-=1;
    }

    public void start(int cooldown, JavaPlugin plugin){
        this.cooldown = cooldown;
        this.taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, 0, 20);
    }

    public void confirm() {
        this.cancel = true;
        this.onConfirm();
    }

    public void cancel() {
        this.cancel = true;
        this.orElse();
    }
}