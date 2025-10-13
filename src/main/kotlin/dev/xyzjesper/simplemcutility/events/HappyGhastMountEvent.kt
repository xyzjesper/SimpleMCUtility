package dev.xyzjesper.simplemcutility.events

import dev.xyzjesper.simplemcutility.SimpleMCUtility
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.attribute.Attribute
import org.bukkit.entity.EntityType
import org.bukkit.entity.HappyGhast
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityMountEvent
import org.bukkit.persistence.PersistentDataType

object HappyGhastMountEvent : Listener {
    val mm = MiniMessage.miniMessage()

    @EventHandler
    fun onEntityDismountEvent(event: EntityMountEvent) {
        if (event.mount.type == EntityType.HAPPY_GHAST && event.mount.passengers.isEmpty()) {

            (event.mount as HappyGhast).getAttribute(Attribute.FLYING_SPEED)!!.baseValue =
                (event.mount as HappyGhast).persistentDataContainer.get(
                    SimpleMCUtility.instance.speedKey, PersistentDataType.DOUBLE
                ) ?: 0.05
            
            SimpleMCUtility.instance.happyPassengers[event.entity.entityId.toString()] = event.mount.uniqueId.toString()
            event.entity.sendMessage(mm.deserialize("<gray>You are now the owner of the Happy Ghast!</gray>\n<gray>Use /happypassengers to manage players</gray>"))
        }
    }

}