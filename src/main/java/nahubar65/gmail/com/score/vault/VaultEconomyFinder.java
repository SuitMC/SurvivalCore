package nahubar65.gmail.com.score.vault;

import nahubar65.gmail.com.score.plugin.PluginDependencyFinder;

public abstract class VaultEconomyFinder extends PluginDependencyFinder {

    private VaultHook vaultHook;

    public VaultEconomyFinder() {
        super(15, "Vault");
        this.vaultHook = VaultHook.getInstance();
    }

    @Override
    public boolean searchDependency() {
        return vaultHook.setupEconomy();
    }
}