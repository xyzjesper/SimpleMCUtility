package dev.xyzjesper.simplemcutility.events

import dev.xyzjesper.simplemcutility.SimpleMCUtility
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.entity.EntityType
import org.bukkit.entity.HappyGhast
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.persistence.PersistentDataType

object PlayerInteractEvent : Listener {

    val key = NamespacedKey(SimpleMCUtility.instance, "parked")

    @EventHandler
    fun onInteract(event: PlayerInteractAtEntityEvent) {
        val player = event.player
        val mm = MiniMessage.miniMessage()
        val target = event.rightClicked

        if (player.isSneaking && target.type == EntityType.HAPPY_GHAST) {
            if (event.hand === EquipmentSlot.HAND) return

            val flySpeed = (target as HappyGhast).getAttribute(Attribute.FLYING_SPEED)

            if (target.persistentDataContainer.get(key, PersistentDataType.BOOLEAN) == true) {
                flySpeed!!.baseValue = 0.05
                setParked(false, target)
                return event.player.sendMessage(mm.deserialize("<gray>Now your Happy Ghast can drive again!</gray>"))
            } else {
                flySpeed!!.baseValue = 0.0
                setParked(true, target)
                return event.player.sendMessage(mm.deserialize("<color:#57ff45>The Happy Ghast has now been parked.</color>"))
            }
        }
    }

    fun setParked(isParked: Boolean, target: HappyGhast) {
        if (target.persistentDataContainer.has(key, PersistentDataType.BOOLEAN)) {
        } else {
            target.persistentDataContainer.set(key, PersistentDataType.BOOLEAN, isParked)
        }
        target.persistentDataContainer.set(key, PersistentDataType.BOOLEAN, isParked)
    }
}