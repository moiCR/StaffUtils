package git.snowk.staff

import git.snowk.staff.frozen.FrozenPlayer
import git.snowk.staff.hotbar.StaffHotbar
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.UUID

class Staff(val manager : StaffManager, val uniqueId : UUID) {

    var vanished : Boolean = false
    lateinit var contents : Array<ItemStack?>
    lateinit var armorContents : Array<ItemStack?>
    var gameMode : GameMode
    val frozenPlayers : MutableList<FrozenPlayer> = mutableListOf()

    init {
        manager.addStaff(getPlayer())
        this.gameMode = getPlayer().gameMode
    }

    fun enable(){
        hide()
        val staff = getPlayer()

        this.contents = staff.inventory.contents
        this.armorContents = staff.inventory.armorContents

        staff.inventory.clear()
        StaffHotbar.give(staff)
        staff.gameMode = GameMode.CREATIVE
    }

    fun disable(){
        show();
        manager.removeStaff(getPlayer())
        val staff = getPlayer()

        staff.inventory.clear()
        staff.inventory.contents = contents
        staff.inventory.armorContents = armorContents
        staff.gameMode = gameMode
    }

    fun hide(){
        if (vanished) return
        vanished = true
        val staff = getPlayer()
        for (player in Bukkit.getOnlinePlayers()){
            if (manager.isInStaffMode(player)) continue
            player.hidePlayer(staff)
        }
    }

    fun show(){
        if (!vanished) return
        vanished = false
        val staff = getPlayer()
        for (player in Bukkit.getOnlinePlayers()){
            if (manager.isInStaffMode(player)) continue
            player.showPlayer(staff)
        }
    }

    fun getPlayer() : Player{
        return manager.plugin.server.getPlayer(uniqueId) ?: throw NullPointerException("Player not found")
    }
}