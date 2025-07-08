package git.snowk.staff.listener

import git.snowk.staff.StaffManager
import git.snowk.staff.frozen.FrozenPlayer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
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
        val staff = manager.staffs.find { staffMode -> staffMode.uniqueId == player.uniqueId} ?: return
        staff.disable()
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onStaffInteract(event : PlayerInteractEvent){
        if (!manager.isInStaffMode(event.player)) return
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onFrozenPlayerInteract(event : PlayerInteractEvent){
        val player = event.player
        val isFrozen = manager.staffs.any { staff ->
            staff.frozenPlayers.any { frozenPlayer ->
                frozenPlayer.uniqueId == player.uniqueId.toString()
            }
        }
        if (isFrozen) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onPlace(event : BlockPlaceEvent){
        val player = event.player
        val staff = manager.staffs.find { it.uniqueId == player.uniqueId } ?: return

        if (staff.vanished && !player.hasPermission("staff.mode.place")){
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onBreak(event : BlockBreakEvent){
        val player = event.player
        val staff = manager.staffs.find { it.uniqueId == player.uniqueId } ?: return

        if (staff.vanished && !player.hasPermission("staff.mode.break")){
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onMove(event : PlayerMoveEvent){
        val player = event.player
        val frozenPlayer = findFrozenPlayer(player) ?: return
        frozenPlayer.handleMove(event)
    }

    @EventHandler
    fun onChat(event : AsyncPlayerChatEvent){
        val player = event.player
        val frozenPlayer = findFrozenPlayer(player) ?: return
        frozenPlayer.handleChat(event)
    }

    private fun findFrozenPlayer(player: Player): FrozenPlayer? {
        return manager.staffs.asSequence()
            .flatMap { it.frozenPlayers.asSequence() }
            .find { it.uniqueId == player.uniqueId.toString() }
    }

}