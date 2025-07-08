package git.snowk.staff.hotbar

import git.snowk.staff.item.ItemBuilder
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

enum class StaffHotbar (val item : ItemStack, val slot : Int) {

    NAVIGATOR(
        ItemBuilder(Material.getMaterial("WATCH")!!)
            .setDisplayName("&aNavigator")
            .build(),
        0
    ),

    ONLINE_STAFFS(
        ItemBuilder(Material.getMaterial("SKULL_ITEM")!!)
            .setDisplayName("&aOnline Staffs")
            .setSkullOwner("MHF_ArrowUp")
            .setData(3)
            .build(),
        4
    ),

    FREEZE(
        ItemBuilder(Material.getMaterial("ICE")!!)
            .setDisplayName("&aFreeze Player")
            .build(),
        8);


    companion object{
        fun give(player : Player){
            entries.forEach { entry -> player.inventory.setItem(entry.slot, entry.item)}
        }
    }
}