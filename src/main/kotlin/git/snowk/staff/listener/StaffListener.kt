package git.snowk.staff.listener

import git.snowk.staff.StaffManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class StaffListener (val manager : StaffManager) : Listener {

    init {
        manager.plugin.server.pluginManager.registerEvents(this, manager.plugin)
    }

    @EventHandler
    fun onJoin(event : PlayerJoinEvent){
        val player = event.player
        if (!player.hasPermission("staff.mode.join")){
            return
        }
        manager.addStaff(player).enable()
    }

    @EventHandler
    fun onQuit(event : PlayerQuitEvent){
        val player = event.player
        val staffMode = manager.staffs.find { staffMode -> staffMode.uniqueId == player.uniqueId} ?: return
        staffMode.disable()
    }

    @EventHandler
    fun onInteract(event : PlayerInteractEvent){
        if (!manager.isInStaffMode(event.player)) return
        event.isCancelled = true
    }

    @EventHandler
    fun onPlace(){

    }

    @EventHandler
    fun onBreak(){

    }
}