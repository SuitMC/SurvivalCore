package nahubar65.gmail.com.score.hook;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.Optional;

public class VaultHook {

    private Economy econ;

    private Chat chat;

    public final static VaultHook INSTANCE = new VaultHook();

    private VaultHook() {
        setupEconomy();
        setupChat();
    }

    private boolean setupEconomy() {
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

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    public Optional<Chat> getChat(){
        return Optional.ofNullable(chat);
    }

    public Optional<Economy> getEconomy(){
        return Optional.ofNullable(econ);
    }
}