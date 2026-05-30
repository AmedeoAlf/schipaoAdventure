package sh.ftp.schipao.schipaoadventure

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import sh.ftp.schipao.schipaoadventure.block.ModBlocks
import sh.ftp.schipao.schipaoadventure.item.ModItemGroups
import sh.ftp.schipao.schipaoadventure.item.ModItems

object SchipaoAdventure : ModInitializer {
	const val MOD_ID :String = "schipaoadventure"
    val LOGGER :Logger = LoggerFactory.getLogger(MOD_ID)

	fun handlePlayerDataPayload(payload: PlayerDataPayload, ctx: ServerPlayNetworking.Context) =
		(ctx.player() as PlayerData).deserialize(payload.data)

	override fun onInitialize() {
		PayloadTypeRegistry.playS2C().register(PlayerDataPayload.ID, PlayerDataPayload.CODEC)
		PayloadTypeRegistry.playC2S().register(PlayerDataPayload.ID, PlayerDataPayload.CODEC)
		ServerPlayNetworking.registerGlobalReceiver(PlayerDataPayload.ID, this::handlePlayerDataPayload)

		ModItemGroups.registerItemGroups()

		ModItems.registerModItems()
		ModBlocks.registerModBlocks()

		ComandCustom.register()
	}
}