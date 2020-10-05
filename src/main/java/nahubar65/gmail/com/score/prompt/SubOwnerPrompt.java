package nahubar65.gmail.com.score.prompt;

import nahubar65.gmail.com.score.decorator.TextDecorator;
import nahubar65.gmail.com.score.plots.MemberManager;
import nahubar65.gmail.com.score.plots.PlotRegion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.conversations.*;

public class SubOwnerPrompt extends ValidatingPrompt {

    private PlotRegion plotRegion;

    public SubOwnerPrompt(PlotRegion plotRegion){
        this.plotRegion = plotRegion;
    }

    @Override
    protected boolean isInputValid(ConversationContext conversationContext, String s) {
        return true;
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext conversationContext, String input) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(input);
        MemberManager memberManager = plotRegion.getMemberManager();
        Conversable conversable = conversationContext.getForWhom();
        if (offlinePlayer != null) {
            if (!memberManager.isSubOwner(offlinePlayer.getUniqueId())) {
                if (memberManager.isMember(offlinePlayer.getUniqueId())) {
                    conversable.sendRawMessage(TextDecorator.color("&9" + offlinePlayer.getName() + " &aahora es &eSub-Propietario &ade &b" + plotRegion.getRegion().getName() + "&a."));
                    memberManager.makeSubOwner(offlinePlayer.getUniqueId());
                } else {
                    conversable.sendRawMessage(TextDecorator.color("&cEse jugador no es parte de &b" + plotRegion.getRegion().getName() + "&c."));
                }
            } else {
                conversable.sendRawMessage(TextDecorator.color("&cEse jugador ya es &eSub-Propietario&c."));
            }
        }
        return END_OF_CONVERSATION;
    }

    @Override
    public String getPromptText(ConversationContext conversationContext) {
        return TextDecorator.color("&aEscribe el 6enombre &adel jugador en el chat");
    }

    public Conversation begin(ConversationFactory factory, Conversable conversable) {
        Conversation conversation = factory.withFirstPrompt(this).buildConversation(conversable);
        conversation.begin();
        return conversation;
    }
}