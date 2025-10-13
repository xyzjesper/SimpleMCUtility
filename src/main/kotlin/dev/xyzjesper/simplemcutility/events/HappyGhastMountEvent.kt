package dev.xyzjesper.simplemcutility.events

import dev.xyzjesper.simplemcutility.SimpleMCUtility
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityMountEvent

object HappyGhastMountEvent : Listener {
    val mm = MiniMessage.miniMessage()

    @EventHandler
    fun onEntityDismountEvent(event: EntityMountEvent) {
        if (event.mount.type == EntityType.HAPPY_GHAST && event.entity.passengers.isEmpty()) {
            SimpleMCUtility.instance.happyPassengers[event.entity.entityId.toString()] = event.mount.uniqueId.toString()
            event.entity.sendMessage(mm.deserialize("<gray>You are now the owner of the Happy Ghast!</gray>\n<gray>Use /happypassengers to manage players</gray>"))
        }
    }

}