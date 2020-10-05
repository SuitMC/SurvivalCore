package nahubar65.gmail.com.score.prompt;

import nahubar65.gmail.com.score.confirmableaction.ConfirmableAction;
import nahubar65.gmail.com.score.decorator.TextDecorator;
import nahubar65.gmail.com.score.menu.ConfirmableActionMenu;
import nahubar65.gmail.com.score.plots.MemberManager;
import nahubar65.gmail.com.score.plots.PlotRegion;
import nahubar65.gmail.com.score.services.RegionService;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class OwnerPrompt extends ValidatingPrompt {

    private PlotRegion plotRegion;

    public OwnerPrompt(PlotRegion plotRegion){
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
        Player player = (Player) conversationContext.getForWhom();
        if (offlinePlayer != null) {
            if (offlinePlayer.getUniqueId().equals(player.getUniqueId())) {
                player.sendRawMessage((TextDecorator.color("&cYa eres Propietario :)")));
            } else {
                Inventory inventory = new ConfirmableActionMenu().getBuilder(6, new ConfirmableAction() {
                    @Override
                    public void onConfirm() {
                        if (!offlinePlayer.isOnline()) {
                            player.sendMessage(TextDecorator.color("&c¡Ese jugador no esta conectado!"));
                            return;
                        }
                        if (RegionService.getPlotOptions().bypassRegionLimit(offlinePlayer.getPlayer())) {
                            player.sendMessage(TextDecorator.color("&9"+offlinePlayer.getName()+" &aahora es &cPropietario &ade &b" + plotRegion.getRegion().getName() + "&a."));
                            memberManager.makeOwner(offlinePlayer.getUniqueId());
                            memberManager.makeSubOwner(player.getUniqueId());
                            RegionService.getPlotManager().giveRegion(offlinePlayer.getUniqueId(), plotRegion);
                            RegionService.getPlotManager().removeRegion(player.getUniqueId(), plotRegion);
                        } else {
                            player.sendMessage(TextDecorator.color("&cEse jugador ha alcanzado el maximo de parcelas."));
                        }
                    }

                    @Override
                    public void orElse() {

                    }
                }).build();
                player.openInventory(inventory);
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