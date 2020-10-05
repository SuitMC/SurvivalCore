package nahubar65.gmail.com.score.nms;

import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class ActionBarSender {

    private static Constructor<?> packetPlayOutChatConstructor;

    static {
        try {
            packetPlayOutChatConstructor = NMS.getNMSClass("PacketPlayOutChat").getConstructor(NMS.getNMSClass("IChatBaseComponent"), Byte.TYPE);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void sendActionbar(Player player, String message) {
        if (player == null || message == null) return;
        try {
            Object packetPlayOutChat;
            Object chatBaseComponent = NMS.chatBaseComponent(message);
            packetPlayOutChat = packetPlayOutChatConstructor.newInstance(chatBaseComponent, (byte) 2);
            NMS.sendPacket(player, packetPlayOutChat);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}