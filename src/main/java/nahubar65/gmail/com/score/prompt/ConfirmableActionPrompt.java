package nahubar65.gmail.com.score.prompt;

import nahubar65.gmail.com.score.confirmableaction.ConfirmableAction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.conversations.*;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfirmableActionPrompt extends ValidatingPrompt implements Runnable {

    private ConfirmableAction confirmableAction;

    private ConversationFactory conversationFactory;

    private Conversation conversation;

    protected Conversable conversable;

    private boolean start;

    private int seconds;

    public ConfirmableActionPrompt(ConfirmableAction confirmableAction, ConversationFactory factory, Conversable conversable) {
        this.confirmableAction = confirmableAction;
        this.conversationFactory = factory;
        this.conversable = conversable;
    }

    @Override
    protected boolean isInputValid(ConversationContext conversationContext, String s) {
        return true;
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext conversationContext, String input) {
        if (input.equalsIgnoreCase(".confirm")) {
            confirmableAction.onConfirm();
            return END_OF_CONVERSATION;
        }
        return this;
    }

    @Override
    public String getPromptText(ConversationContext conversationContext) {
        if (!start) {
            start = true;
            if(seconds > 0)
                conversationContext.getForWhom().sendRawMessage(color("&c¡Tienes " + seconds + " segundos para confirmar!"));
            return color("&ePara confirmar, escribe \".confirm\" en el chat.");
        }
        return "";
    }

    private String color(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public Conversation begin(int seconds, ConversationFactory factory, Conversable conversable) {
        this.seconds = seconds;
        Conversation conversation = factory.withFirstPrompt(this).buildConversation(conversable);
        conversation.begin();
        return conversation;
    }

    public Conversation begin(ConversationFactory factory, Conversable conversable) {
        Conversation conversation = factory.withFirstPrompt(this).buildConversation(conversable);
        conversation.begin();
        return conversation;
    }

    public void start(int seconds, JavaPlugin plugin) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, this, seconds * 20);
        this.conversation = begin(seconds, conversationFactory, conversable);
    }

    public void start() {
        this.conversation = begin(conversationFactory, conversable);
    }

    @Override
    public void run() {
        if(conversation != null)
            conversation.abandon();
    }
}