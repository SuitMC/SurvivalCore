package nahubar65.gmail.com.score.vault;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.Optional;

public class VaultHook {

    private Economy econ;

    private Chat chat;

    private static VaultHook instance;

    public boolean setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
        if (rsp == null) {
            return false;
        }
        chat = rsp.getProvider();
        return chat != null;
    }

    public Optional<Chat> getChat(){
        return Optional.ofNullable(chat);
    }

    public Optional<Economy> getEconomy(){
        return Optional.ofNullable(econ);
    }

    public static VaultHook getInstance() {
        return instance != null ? instance : (instance = new VaultHook());
    }
}