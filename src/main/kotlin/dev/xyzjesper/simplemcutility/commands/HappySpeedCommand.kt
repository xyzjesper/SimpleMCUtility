package dev.xyzjesper.simplemcutility.commands

import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.doubleArgument
import dev.jorel.commandapi.kotlindsl.playerExecutor
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.attribute.Attribute
import org.bukkit.entity.EntityType
import org.bukkit.entity.HappyGhast

object HappySpeedCommand {
    val mm = MiniMessage.miniMessage()
    val command = commandTree("happyspeed", "simplemcutility") {
        withAliases("hs")
        doubleArgument("speed", 0.0, 0.5) {
            playerExecutor { sender, args ->

                val target = sender.getTargetEntity(5)

                if (target == null || target.type != EntityType.HAPPY_GHAST) {
                    sender.sendMessage(mm.deserialize("<color:#ff0015>You need to look at a Happy Ghast</color>"))
                } else if (target is HappyGhast) {
                    target.getAttribute(Attribute.FLYING_SPEED)?.baseValue = args[0] as Double
                    sender.sendMessage(mm.deserialize("<color:#57ff45>You have now set the speed to ${args[0]}</color>"))
                }

            }
        }
    }
}