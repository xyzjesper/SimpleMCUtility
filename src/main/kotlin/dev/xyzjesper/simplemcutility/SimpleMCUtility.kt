package dev.xyzjesper.simplemcutility

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIPaperConfig
import dev.xyzjesper.simplemcutility.commands.HappyPassengers
import dev.xyzjesper.simplemcutility.commands.HappySpeedCommand
import dev.xyzjesper.simplemcutility.events.HappyGhastDismountEvent
import dev.xyzjesper.simplemcutility.events.HappyGhastMountEvent
import dev.xyzjesper.simplemcutility.events.PlayerInteractEvent
import gg.flyte.twilight.twilight
import org.bukkit.entity.HappyGhast
import org.bukkit.plugin.java.JavaPlugin

class SimpleMCUtility : JavaPlugin() {

    companion object {
        lateinit var instance: SimpleMCUtility
    }
    
    init {
        instance = this
    }
    
    val happyPassengers = mutableMapOf<String, String>()
    
    override fun onLoad() {
        CommandAPI.onLoad(CommandAPIPaperConfig(this).silentLogs(true))

        logger.info("Loading Plugin...")

    }

    override fun onEnable() {
        CommandAPI.onEnable()

        val tw = twilight(this)
        
        HappySpeedCommand
        HappyPassengers
        server.pluginManager.registerEvents(HappyGhastDismountEvent, this)
        server.pluginManager.registerEvents(PlayerInteractEvent, this)
        server.pluginManager.registerEvents(HappyGhastMountEvent, this)
        
        logger.info("Plugin enabled!")

    }

    override fun onDisable() {
        CommandAPI.onDisable()

        logger.info("Plugin disabled!")
    }

}