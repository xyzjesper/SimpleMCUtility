package dev.xyzjesper.simplemcutility.commands

import com.destroystokyo.paper.profile.PlayerProfile
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.xyzjesper.simplemcutility.SimpleMCUtility
import gg.flyte.twilight.gui.GUI.Companion.openInventory
import gg.flyte.twilight.gui.gui
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.HappyGhast
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.net.URL
import java.util.*

object HappyPassengers {
    val mm = MiniMessage.miniMessage()

    val command = commandTree("happypassengers", "simplemcutility") {
        withAliases("hp")
        playerExecutor { sender, args ->
            val entityId = SimpleMCUtility.instance.happyPassengers[sender.entityId.toString()]

            if (entityId == null) {
                sender.sendMessage(mm.deserialize("<red>You need a happy passenger from a Ghast</red>"))
            } else {
                val entity = Bukkit.getServer().getEntity(UUID.fromString(entityId))
                if (entity == null) {
                    sender.sendMessage(mm.deserialize("<red>No Happy Ghast found!</red>"))
                } else {
                    if (entity.type != EntityType.HAPPY_GHAST) {
                        sender.sendMessage(mm.deserialize("<red>No Happy Ghast found!</red>"))
                    } else {
                        val gui = gui(mm.deserialize("<gray>Happy Ghast Passenger!</gray>")) {
                            val happyGhast = entity as HappyGhast

                            var slot = 0

                            happyGhast.passengers.forEach { player ->
                                player as Player

                                set(slot, ItemStack(Material.PLAYER_HEAD).apply {
                                    slot += 1
                                    val meta = itemMeta as SkullMeta
                                    meta.displayName(mm.deserialize(viewer.name))
                                    meta.owningPlayer = player
                                    
                                    meta.playerProfile = player.playerProfile
                                    itemMeta = meta
                                }) {
                                    isCancelled = true
                                    happyGhast.removePassenger(player)
                                    viewer.sendMessage(mm.deserialize("<green>You removed ${player.name} from the Happy Ghast</green>"))
                                }

                            }

                        }
                        sender.openInventory(gui)
                    }
                }
            }
        }
    }
}