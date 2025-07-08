package git.snowk.staff

import git.snowk.staff.hotbar.StaffHotbar
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.UUID

class StaffMode(val manager : StaffManager, val uniqueId : UUID) {

    var vanished : Boolean = false
    lateinit var contents : Array<ItemStack?>
    lateinit var armorContents : Array<ItemStack?>
    var gameMode : GameMode

    init {
        manager.addStaff(getStaff())
        this.gameMode = getStaff().gameMode
    }

    fun enable(){
        hide()
        val staff = getStaff()

        this.contents = staff.inventory.contents
        this.armorContents = staff.inventory.armorContents

        staff.inventory.clear()
        StaffHotbar.give(staff)
        staff.gameMode = GameMode.CREATIVE
    }

    fun disable(){
        show();
        manager.removeStaff(getStaff())
        val staff = getStaff()

        staff.inventory.clear()
        staff.inventory.contents = contents
        staff.inventory.armorContents = armorContents
        staff.gameMode = gameMode
    }

    fun hide(){
        if (vanished) return
        vanished = true
        val staff = getStaff()
        for (player in Bukkit.getOnlinePlayers()){
            if (manager.isInStaffMode(player)) continue
            player.hidePlayer(staff)
        }
    }

    fun show(){
        if (!vanished) return
        vanished = false
        val staff = getStaff()
        for (player in Bukkit.getOnlinePlayers()){
            if (manager.isInStaffMode(player)) continue
            player.showPlayer(staff)
        }
    }

    fun getStaff() : Player{
        return Bukkit.getPlayer(uniqueId) ?: throw NullPointerException("Player not found")
    }
}