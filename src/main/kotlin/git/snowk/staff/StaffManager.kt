package git.snowk.staff

import git.snowk.staff.listener.StaffListener
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin


class StaffManager (val plugin: JavaPlugin) {

    val staffs : MutableList<Staff> = mutableListOf()

    init{
        StaffListener(this)
    }

    fun addStaff(player : Player) : Staff{
        val staff = Staff(this, player.uniqueId)
        staffs.add(staff)
        return staff
    }

    fun removeStaff(player : Player){
        staffs.removeIf { mode -> mode.uniqueId == player.uniqueId }
    }

    fun isInStaffMode(player: Player) : Boolean{
        return staffs.any { staffMode -> staffMode.uniqueId == player.uniqueId }
    }

    fun getOnlineStaffs() : List<Player>{
        return Bukkit.getOnlinePlayers()
            .filter {
                it -> isInStaffMode(it)
            }.toList()
    }

}
