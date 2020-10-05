package nahubar65.gmail.com.score.prompt;

import nahubar65.gmail.com.score.decorator.TextDecorator;
import nahubar65.gmail.com.score.plots.MemberManager;
import nahubar65.gmail.com.score.plots.PlotOptions;
import nahubar65.gmail.com.score.plots.PlotRegion;
import nahubar65.gmail.com.score.plots.PlotRequestSender;
import nahubar65.gmail.com.score.services.RegionService;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;

public class MemberPrompt extends ValidatingPrompt {

    private PlotRegion plotRegion;

    public MemberPrompt(PlotRegion plotRegion){
        this.plotRegion = plotRegion;
    }

    @Override
    protected boolean isInputValid(ConversationContext conversationContext, String s) {
        return true;
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext conversationContext, String input) {
        Player player = Bukkit.getPlayer(input);
        MemberManager memberManager = plotRegion.getMemberManager();
        Conversable conversable = conversationContext.getForWhom();
        PlotRequestSender plotRequestSender = plotRegion.getPlotRequestSender();
        if (player != null) {
            PlotOptions plotOptions = RegionService.getPlotOptions();
            if (plotOptions.bypassMemberLimit(player, memberManager)) {
                if (!memberManager.isMember(player.getUniqueId())) {
                    if (!plotRequestSender.isInQueue(player.getUniqueId())) {
                        conversable.sendRawMessage(TextDecorator.color("&bSe le ha enviado una solicitud a &9" + player.getName() + "&b."));
                        plotRegion.getPlotRequestSender().sendRequest(player, (Player) conversable);
                    } else {
                        player.sendMessage(TextDecorator.color("&c¡Ese jugador ya fue invitado!"));
                    }
                } else {
                    conversable.sendRawMessage(TextDecorator.color("&cEse jugador ya forma parte de &b" + plotRegion.getRegion().getName() + "&c."));
                }
            } else {
                conversable.sendRawMessage(TextDecorator.color("&cHas alcanzado el limite de jugadores por parcela. &6(" + plotOptions.getMaxMembersPerRegion() + ")"));
                conversable.sendRawMessage(TextDecorator.color("&bCompra &aVIP &ben la tienda para poder aumentar el limite de jugadores por region."));
            }
        }
        return END_OF_CONVERSATION;
    }

    @Override
    public String getPromptText(ConversationContext conversationContext) {
        return TextDecorator.color("&aEscribe el &enombre &adel jugador en el chat");
    }

    public Conversation begin(ConversationFactory factory, Conversable conversable) {
        Conversation conversation = factory.withFirstPrompt(this).buildConversation(conversable);
        conversation.begin();
        return conversation;
    }
}