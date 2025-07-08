package git.snowk.staff.frozen

import git.snowk.staff.StaffManager
import git.snowk.staff.Staff
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.inventory.ItemStack

class FrozenPlayer(val manager : StaffManager, val staff : Staff, val uniqueId : String) {

    lateinit var lastHelmet : ItemStack
    var frozen : Boolean = false

    init {
        staff.frozenPlayers.add(this)
        freeze()
    }

    fun freeze(){
        frozen = true

    }

    fun handleChat(event : AsyncPlayerChatEvent){
        val player = event.player
        val message = event.message
        event.isCancelled = true
        staff.getPlayer().sendMessage("${ChatColor.DARK_RED}[FROZEN] ${player.name}: ${ChatColor.RED}${message}")
    }

    fun handleMove(event : PlayerMoveEvent){
        if (!frozen){
            return
        }
        event.to = event.from
    }

    fun getPlayer() : Player{
        return manager.plugin.server.getPlayer(uniqueId) ?: throw NullPointerException("Player not found")
    }
}