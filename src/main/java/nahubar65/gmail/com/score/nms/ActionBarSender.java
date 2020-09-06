package nahubar65.gmail.com.score.nms;

import org.bukkit.entity.Player;

import java.lang.reflect.Method;

public class ActionBarSender {

    public static void sendActionbar(Player player, String message) {
        if (player == null || message == null) return;
        String nmsVersion = NMS.VERSION;
        nmsVersion = nmsVersion.substring(nmsVersion.lastIndexOf(".") + 1);

        try {
            Class<?> ppoc = Class.forName("net.minecraft.server." + nmsVersion + ".PacketPlayOutChat");
            Object packetPlayOutChat;
            Class<?> chat = Class.forName("net.minecraft.server." + nmsVersion + (nmsVersion.equalsIgnoreCase("v1_8_R1") ? ".ChatSerializer" : ".ChatComponentText"));
            Class<?> chatBaseComponent = Class.forName("net.minecraft.server." + nmsVersion + ".IChatBaseComponent");

            Method method = null;
            if (nmsVersion.equalsIgnoreCase("v1_8_R1")) method = chat.getDeclaredMethod("a", String.class);

            Object object = nmsVersion.equalsIgnoreCase("v1_8_R1") ? chatBaseComponent.cast(method.invoke(chat, "{'text': '" + message + "'}")) : chat.getConstructor(new Class[]{String.class}).newInstance(message);
            packetPlayOutChat = ppoc.getConstructor(new Class[]{chatBaseComponent, Byte.TYPE}).newInstance(object, (byte) 2);
            NMS.sendPacket(player, packetPlayOutChat);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}