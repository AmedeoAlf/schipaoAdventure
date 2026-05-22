package sh.ftp.schipao.schipaoadventure.item

import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import sh.ftp.schipao.schipaoadventure.SchipaoAdventure
import sh.ftp.schipao.schipaoadventure.item.custom.FeatherItem
import sh.ftp.schipao.schipaoadventure.item.custom.OriginStarItem

object ModItems {
    val RAW_MITHRIL :Item = registerItem("raw_mithril", Item(Item.Settings()))
    val MITHRIL_INGOT :Item = registerItem("mithril_ingot", Item(Item.Settings()))

    val MITHRIL_FEATHER :Item = registerItem("mithril_feather", FeatherItem(Item.Settings().maxCount(1)))
    val STAR_OF_ORIGINS :Item = registerItem("star_of_origins", OriginStarItem(Item.Settings().maxCount(1).rarity(Rarity.RARE)))

    fun registerModItems(){
        SchipaoAdventure.LOGGER.info("Registering Mod Items for "+ SchipaoAdventure.MOD_ID)
    }

    private fun registerItem(name :String, item :Item) :Item{
        return Registry.register(Registries.ITEM, Identifier.of(SchipaoAdventure.MOD_ID, name), item)
    }

}