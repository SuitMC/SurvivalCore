package nahubar65.gmail.com.score.vault;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;

import java.util.Optional;
import java.util.UUID;

public class VaultEconomy {

    private Economy economy;

    public VaultEconomy(){
        Optional<Economy> optionalEconomy = VaultHook.getInstance().getEconomy();
        if (!optionalEconomy.isPresent())
            throw new UnsupportedOperationException("[SurvivalCore] Vault Economy has not been hooked!");
        optionalEconomy.ifPresent(economy1 -> {
            economy = economy1;
        });
    }

    public boolean hasMoney(UUID uuid, double money){
        return economy.has(Bukkit.getOfflinePlayer(uuid), money);
    }

    public void deposit(UUID uuid, double money) {
        economy.depositPlayer(Bukkit.getOfflinePlayer(uuid), money);
    }

    public void remove(UUID uuid, double money) {
        economy.withdrawPlayer(Bukkit.getOfflinePlayer(uuid), money);
    }
}