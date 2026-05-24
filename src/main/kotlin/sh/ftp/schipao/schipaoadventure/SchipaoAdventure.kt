package sh.ftp.schipao.schipaoadventure

import com.example.command.ComandCustom
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sh.ftp.schipao.schipaoadventure.block.ModBlocks
import sh.ftp.schipao.schipaoadventure.item.ModItemGroups
import sh.ftp.schipao.schipaoadventure.item.ModItems

object SchipaoAdventure : ModInitializer {
	const val MOD_ID :String = "schipaoadventure"
    val LOGGER :Logger = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		ModItemGroups.registerItemGroups()

		ModItems.registerModItems()
		ModBlocks.registerModBlocks()

		ComandCustom.register()
	}
}