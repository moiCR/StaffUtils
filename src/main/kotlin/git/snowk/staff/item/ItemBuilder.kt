package git.snowk.staff.item

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.SkullMeta

class ItemBuilder{

    private val item : ItemStack
    private val meta : ItemMeta

    constructor(material: Material){
        this.item = ItemStack(material)
        this.meta = item.itemMeta
    }

    constructor(item : ItemStack){
        this.item = item
        this.meta = item.itemMeta
    }

    fun setDisplayName(displayName : String) : ItemBuilder{
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName))
        return this
    }

    fun setData(data : Int) : ItemBuilder{
        item.durability = data.toShort()
        return this
    }

    fun setSkullOwner(owner : String) : ItemBuilder{
        if (item.type != Material.getMaterial("SKULL_ITEM")!! && item.durability != 3.toShort() || item.type != Material.PLAYER_HEAD) return this
        val skullMeta = item.itemMeta as SkullMeta
        skullMeta.owner = owner
        item.itemMeta = skullMeta
        return this
    }

    fun build() : ItemStack{
        item.itemMeta = meta
        return item
    }


}