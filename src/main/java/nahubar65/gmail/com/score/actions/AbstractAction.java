package nahubar65.gmail.com.score.actions;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public abstract class AbstractAction {

    private boolean cancelClick = false;

    private boolean cancelInteract = false;

    public abstract void performSpecialAction(Player player, Event event);

    public boolean cancelClick(){
        return cancelClick;
    }

    public boolean cancelInteract(){
        return cancelInteract;
    }

    public AbstractAction setCancelClick(boolean cancelClick){
        this.cancelClick = cancelClick;
        return this;
    }

    public AbstractAction setCancelInteract(boolean cancelInteract){
        this.cancelInteract = cancelInteract;
        return this;
    }
}
