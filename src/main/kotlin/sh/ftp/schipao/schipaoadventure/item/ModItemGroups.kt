package sh.ftp.schipao.schipaoadventure.item

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import sh.ftp.schipao.schipaoadventure.SchipaoAdventure
import sh.ftp.schipao.schipaoadventure.block.ModBlocks

object ModItemGroups {
    val SCHIPAOADVENTURE_ITEMS :ItemGroup = Registry.register(Registries.ITEM_GROUP,
        Identifier.of(SchipaoAdventure.MOD_ID, "schipaoadventure_items"),
        FabricItemGroup.builder()
            .displayName(Text.translatable("itemgroup.schipaoadventure_items"))
            .icon { ItemStack(ModItems.MITHRIL_INGOT) }
            .entries { _, entries ->
                entries.add(ModItems.RAW_MITHRIL)
                entries.add(ModItems.MITHRIL_INGOT)

                entries.add(ModBlocks.MITHRIL_ORE)
                entries.add(ModBlocks.DEEPSLATE_MITHRIL_ORE)
                entries.add(ModBlocks.RAW_MITHRIL_BLOCK)
                entries.add(ModBlocks.MITHRIL_BLOCK)

                entries.add(ModItems.MITHRIL_FEATHER)
                entries.add(ModItems.STAR_OF_ORIGINS)
            }
            .build()
    )

    fun registerItemGroups() {
        SchipaoAdventure.LOGGER.info("Registering Item Groups for " + SchipaoAdventure.MOD_ID)
    }
}