package git.snowk.staff.listener

import git.snowk.staff.StaffManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class StaffListener (manager : StaffManager) : Listener {

    init {
        manager.plugin.server.pluginManager.registerEvents(this, manager.plugin)
    }

    @EventHandler
    fun onJoin(){

    }

    @EventHandler
    fun onQuit(){

    }

    @EventHandler
    fun onInteract(){

    }

    @EventHandler
    fun onPlace(){

    }

    @EventHandler
    fun onBreak(){

    }
}