package git.snowk.staff

import git.snowk.staff.hotbar.StaffHotbar
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID


class StaffManager (val plugin: JavaPlugin) {

    val staffs : MutableList<UUID> = mutableListOf()
    val vanished : MutableList<UUID> = mutableListOf()
    val inventories : MutableMap<UUID, MutableList<ItemStack>> = mutableMapOf()


    fun toggleStaffMode(player : Player){
        if (isInStaffMode(player)){
            disableStaffMode(player, true)
            return;
        }

        enableStaffMode(player)
    }

    fun enableStaffMode(staff: Player){
        staffs.add(staff.uniqueId)
        enableVanish(staff)

        val items = mutableListOf<ItemStack>()
        for (i in 0..35){
            items.add(staff.inventory.getItem(i) ?: continue)
        }

        inventories[staff.uniqueId] = items
        staff.inventory.clear()
        StaffHotbar.give(staff)
        staff.gameMode = GameMode.CREATIVE
    }

    fun disableStaffMode(staff: Player, removeVanish : Boolean){
        staffs.remove(staff.uniqueId)
        if (removeVanish) disableVanish(staff)

        val items = inventories[staff.uniqueId] ?: return
        for (i in 0..35){
            staff.inventory.setItem(i, items[i])
        }
    }

    fun enableVanish(staff: Player){
        if (isVanished(staff)) return
        vanished.add(staff.uniqueId)

        for (noStaff in Bukkit.getOnlinePlayers()){
            noStaff.hidePlayer(staff)
        }
    }

    fun disableVanish(staff: Player){
        if (!isVanished(staff)) return
        vanished.remove(staff.uniqueId)
        for (noStaff in Bukkit.getOnlinePlayers()){
            noStaff.showPlayer(staff)
        }
    }

    fun isInStaffMode(player: Player) : Boolean{
        return staffs.contains(player.uniqueId)
    }

    fun isVanished(player: Player) : Boolean{
        return vanished.contains(player.uniqueId)
    }


    fun hideVanishedStaffs(player : Player, forceVanish : Boolean){
        val vanishedStaffs = vanished
        if (!forceVanish && player.hasPermission("staff.mode")
            || player.hasPermission("staff.vanish")){
            return;
        }

        for (vanishedStaff in vanishedStaffs){
            player.hidePlayer(Bukkit.getPlayer(vanishedStaff) ?: continue)
        }

    }

    fun showVanishedStaffs(){

    }

    fun getOnlineStaffs() : List<Player>{
        return Bukkit.getOnlinePlayers()
            .filter {
                it -> isInStaffMode(it)
            }.toList()
    }

}
