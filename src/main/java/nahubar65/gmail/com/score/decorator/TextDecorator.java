package nahubar65.gmail.com.score.decorator;

import org.bukkit.ChatColor;

public class TextDecorator {

    public static String color(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}