package glorydark.playanimation;

import cn.nukkit.plugin.PluginBase;

public class MainClass extends PluginBase {

    public static MainClass instance;

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onEnable() {
        instance = this;
        this.getLogger().info("PlayAnimation Enable!");
        this.getServer().getCommandMap().register("", new PlayAnimationCommand("playanimation"));
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
