package git.snowk.staff.frozen

import git.snowk.staff.StaffManager
import git.snowk.staff.Staff
import org.bukkit.ChatColor
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.inventory.ItemStack

class FrozedPlayer(val manager : StaffManager, val staff : Staff, val uniqueId : String) {

    lateinit var lastHelmet : ItemStack

    fun handleChat(event : AsyncPlayerChatEvent){
        val player = event.player
        val message = event.message

        event.isCancelled = true

        staff.getPlayer().sendMessage("${ChatColor.DARK_RED}[FROZEN] ${player.name}: ${ChatColor.RED}${message}")
        
    }

}