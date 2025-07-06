package git.snowk.staff

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID


class StaffManager (val plugin: JavaPlugin) {

    var staffs : MutableList<UUID> = mutableListOf()
    var vanished : MutableList<UUID> = mutableListOf()


    fun toggleStaffMode(player : Player){
        if (staffs.contains(player.uniqueId)){
            // disableStaffMode(player)
            // disableVanish(player)
            return;
        }

        enableStaffMode(player)
    }

    fun enableStaffMode(staff: Player){
        staffs.add(staff.uniqueId)
    }

    fun disableStaffMode(staff: Player, removeVanish : Boolean){
        staffs.remove(staff.uniqueId)
        if (removeVanish) return //disableVanish(player)
    }

    fun enableVanish(staff: Player){
        if (isVanished(staff)) return
        vanished.add(staff.uniqueId)

        for (noStaff in Bukkit.getOnlinePlayers()){
            noStaff.hidePlayer(staff)
        }
    }

    fun isInStaffMode(player: Player) : Boolean{
        return staffs.contains(player.uniqueId)
    }

    fun isVanished(player: Player) : Boolean{
        return vanished.contains(player.uniqueId)
    }


    fun hideVanishedStaffs(){
        val vanishedStaffs = vanished
        val onlinePlayers = Bukkit.getOnlinePlayers()

        for (noStaff in onlinePlayers){
            for (vanishedStaff in vanishedStaffs){
                noStaff.hidePlayer(Bukkit.getPlayer(vanishedStaff) ?: continue)
            }
        }
    }

    fun showVanishedStaffs(){
        val vanishedStaffs = vanished
        val onlinePlayers = Bukkit.getOnlinePlayers()

        for (noStaff in onlinePlayers){
            for (vanishedStaff in vanishedStaffs){
                noStaff.showPlayer(Bukkit.getPlayer(vanishedStaff) ?: continue)
            }
        }
    }

    fun getOnlineStaffs() : List<Player>{
        return Bukkit.getOnlinePlayers()
            .filter {
                it -> isInStaffMode(it)
            }.toList()
    }

}
