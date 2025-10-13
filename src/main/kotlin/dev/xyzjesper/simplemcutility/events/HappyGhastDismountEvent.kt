package dev.xyzjesper.simplemcutility.events

import dev.xyzjesper.simplemcutility.SimpleMCUtility
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.attribute.Attribute
import org.bukkit.entity.EntityType
import org.bukkit.entity.HappyGhast
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDismountEvent

object HappyGhastDismountEvent : Listener {
    val mm = MiniMessage.miniMessage()

    @EventHandler
    fun onEntityDismountEvent(event: EntityDismountEvent) {
        if (event.dismounted.type == EntityType.HAPPY_GHAST && event.entity.passengers.isEmpty()) {

            SimpleMCUtility.instance.happyPassengers.remove(event.entity.entityId.toString())

            (event.dismounted as HappyGhast).getAttribute(Attribute.FLYING_SPEED)!!.baseValue = 0.05
            event.entity.sendMessage(mm.deserialize("<gray>No Passengers left! Set base speed for your Happy Ghast</gray>"))
        }
    }

}