package me.drawethree.buildbattleexpansion;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.drawe.buildbattle.BuildBattle;
import me.drawe.buildbattle.objects.bbobjects.BBPlayerStats;
import me.drawe.buildbattle.objects.bbobjects.BBStat;
import me.drawe.buildbattle.objects.bbobjects.arena.BBArena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BuildBattleExpansion extends PlaceholderExpansion {

    // We get an instance of the plugin later.
    private BuildBattle plugin;

    /**
     * Since this expansion requires api access to the plugin "SomePlugin"
     * we must check if said plugin is on the server or not.
     *
     * @return true or false depending on if the required plugin is installed.
     */
    @Override
    public boolean canRegister(){
        return Bukkit.getPluginManager().getPlugin(getPlugin()) != null;
    }

    /**
     * We can optionally override this method if we need to initialize variables
     * within this class if we need to or even if we have to do other checks to
     * ensure the hook is properly set up.
     *
     * @return true or false depending on if it can register.
     */
    @Override
    public boolean register(){

        // Make sure "SomePlugin" is on the server
        if(!canRegister()){
            return false;
        }

        /*
         * "SomePlugin" does not have static methods to access its api so we must
         * create a variable to obtain access to it.
         */
        plugin = (BuildBattle) Bukkit.getPluginManager().getPlugin(getPlugin());

        // if for some reason we can not get our variable, we should return false.
        if(plugin == null){
            return false;
        }

        /*
         * Since we override the register method, we need to call the super method to actually
         * register this hook
         */
        return super.register();
    }

    /**
     * The name of the person who created this expansion should go here.
     *
     * @return The name of the author as a String.
     */
    @Override
    public String getAuthor(){
        return "Drawethree";
    }

    /**
     * The placeholder identifier should go here.
     * <br>This is what tells PlaceholderAPI to call our onRequest
     * method to obtain a value if a placeholder starts with our
     * identifier.
     * <br>This must be unique and can not contain % or _
     *
     * @return The identifier in {@code %<identifier>_<value>%} as String.
     */
    @Override
    public String getIdentifier(){
        return "buildbattlepro";
    }

    /**
     * if the expansion requires another plugin as a dependency, the
     * proper name of the dependency should go here.
     * <br>Set this to {@code null} if your placeholders do not require
     * another plugin to be installed on the server for them to work.
     * <br>
     * <br>This is extremely important to set your plugin here, since if
     * you don't do it, your expansion will throw errors.
     *
     * @return The name of our dependency.
     */
    @Override
    public String getRequiredPlugin(){
        return "BuildBattlePro";
    }

    /**
     * This is the version of this expansion.
     * <br>You don't have to use numbers, since it is set as a String.
     *
     * @return The version as a String.
     */
    @Override
    public String getVersion(){
        return "1.0.0";
    }

    /**
     * This is the method called when a placeholder with our identifier
     * is found and needs a value.
     * <br>We specify the value identifier in this method.
     * <br>Since version 2.9.1 can you use OfflinePlayers in your requests.
     *
     * @param  player
     *         A {@link org.bukkit.Player Player}.
     * @param  identifier
     *         A String containing the identifier/value.
     *
     * @return possibly-null String of the requested identifier.
     */
    @Override
    public String onPlaceholderRequest(Player p, String identifier){

        if (p == null) {
            return "";
        }

        if (identifier.contains("status")) {
            final BBArena arena = plugin.getArenaManager().getArena(identifier.replaceAll("status_", ""));
            if (arena != null) {
                return arena.getBBArenaState().getPrefix();
            }
        }

        BBPlayerStats stats = plugin.getPlayerManager().getPlayerStats(p);

        if (stats == null) {
            return "Loading...";
        } else {
            return String.valueOf(stats.getStat(BBStat.getStat(identifier)));
        }
    }
}
