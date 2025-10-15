package dev.xyzjesper.simplemcutility.commands

import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.doubleArgument
import dev.jorel.commandapi.kotlindsl.literalArgument
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.xyzjesper.simplemcutility.SimpleMCUtility
import gg.flyte.twilight.extension.Location
import gg.flyte.twilight.gui.GUI.Companion.openInventory
import gg.flyte.twilight.gui.gui
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.entity.EntityType
import org.bukkit.entity.HappyGhast
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.util.*

object Happy {

    val mm = MiniMessage.miniMessage()

    val command = commandTree("happy", "simplemcutility") {
        withAliases("happyghast")
        withHelp("Use this command to simply manage the Happy Ghast!", """
                 /happy silent - Mute or Unmute the Ghast Sounds
                 /happy speed - Sets the current Fly Speed of the Ghast (0.0-0.5)
                 /happy passengers - Opens a Passenger Manage Menu to kick passenger from the Ghast
        """.trimIndent())
        
        literalArgument("silent") {
            playerExecutor { sender, args ->
                val targets = sender.location.getNearbyEntities(10.0, 10.0, 10.0)
                targets.forEach { target ->
                    if (target.type == EntityType.HAPPY_GHAST && target is HappyGhast) {
                        if (target.isSilent) {
                            target.isSilent = false
                            sender.sendActionBar(mm.deserialize("<color:#9cffc3>The yawn com Ghast can be heard again.</color>"))
                        } else {
                            target.isSilent = true
                            sender.sendActionBar(mm.deserialize("<color:#82c5ff>The ghast is now quiet.</color>"))
                        }
                    }

                }
            }
        }
        literalArgument("speed") {
            doubleArgument("speed", 0.0, 0.5) {
                playerExecutor { sender, args ->

                    val targets = sender.location.getNearbyEntities(10.0, 10.0, 10.0)
                    targets.forEach { target ->
                        if (target.type == EntityType.HAPPY_GHAST && target is HappyGhast) {
                            target.getAttribute(Attribute.FLYING_SPEED)?.baseValue = args[0] as Double
                            SimpleMCUtility.instance.setSpeed(args[0] as Double, target)
                            sender.sendActionBar(mm.deserialize("<color:#57ff45>You have now set the speed to ${args[0]}</color>"))
                        }
                    }
                }
            }
        }
        literalArgument("passengers") {
            playerExecutor { sender, args ->
                val entityId = SimpleMCUtility.instance.happyPassengers[sender.entityId.toString()]

                if (entityId == null) {
                    sender.sendActionBar(mm.deserialize("<red>You are not a happy owner of a Ghast</red>"))
                } else {
                    val entity = Bukkit.getServer().getEntity(UUID.fromString(entityId))
                    if (entity == null) {
                        sender.sendActionBar(mm.deserialize("<red>No Happy Ghast found!</red>"))
                    } else {
                        if (entity.type != EntityType.HAPPY_GHAST) {
                            sender.sendActionBar(mm.deserialize("<red>No Happy Ghast found!</red>"))
                        } else {
                            val gui = gui(mm.deserialize("<gray>Happy Ghast Passengers</gray>")) {
                                val happyGhast = entity as HappyGhast

                                var slot = 0

                                happyGhast.passengers.forEach { player ->
                                    player as Player

                                    if (player.uniqueId != sender.uniqueId) {
                                        set(slot, ItemStack(Material.PLAYER_HEAD).apply {
                                            slot += 1
                                            val meta = itemMeta as SkullMeta
                                            meta.displayName(mm.deserialize("<gray>${player.name}</gray>"))
                                            meta.owningPlayer = player

                                            meta.playerProfile = player.playerProfile
                                            itemMeta = meta
                                        }) {
                                            isCancelled = true
                                            happyGhast.removePassenger(player)
                                            player.teleport(
                                                Location(
                                                    happyGhast.location.world,
                                                    happyGhast.location.x,
                                                    happyGhast.location.y - 5,
                                                    happyGhast.location.z
                                                )
                                            )
                                            viewer.sendActionBar(mm.deserialize("<green>You removed ${player.name} from the Happy Ghast</green>"))
                                        }
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
}