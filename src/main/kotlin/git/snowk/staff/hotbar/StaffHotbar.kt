package git.snowk.staff.hotbar

import git.snowk.staff.item.ItemBuilder
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

enum class StaffHotbar (val item : ItemStack, val slot : Int) {

    COMPASS(
        ItemBuilder(Material.getMaterial("WATCH")!!).build(),
        0
    ),

    ONLINE_STAFFS(
        ItemBuilder(Material.getMaterial("SKULL_ITEM")!!)
            .setData(3)
            .build(),
        4
    ),

    FREEZE(
        ItemBuilder(Material.getMaterial("ICE")!!)
            .build(),
        8);


    companion object{
        fun give(player : Player){
            entries.forEach { entry -> player.inventory.setItem(entry.slot, entry.item)}
        }
    }
}