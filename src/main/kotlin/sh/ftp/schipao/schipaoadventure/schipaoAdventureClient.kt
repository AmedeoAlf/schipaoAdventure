package sh.ftp.schipao.schipaoadventure

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.text.Text

class schipaoAdventureClient :ClientModInitializer {
    companion object {
        fun handlePlayerDataPayload(payload: PlayerDataPayload, ctx: ClientPlayNetworking.Context) {
            val player = ctx.client().player!!
            player.sendMessage(Text.literal("got ${payload.data}"))
            (player as PlayerData).deserialize(payload.data)
        }
    }

    private var opened = false

    override fun onInitializeClient() {

        ClientTickEvents.END_CLIENT_TICK.register { client ->

            if (opened) return@register

            val player = client.player ?: return@register
            val data = player as PlayerData

            if (data.playerClass == -1) {
                client.setScreen(
                    CustomClassChoiceScreen(
                        Text.literal("Choose Class")
                    ) {
                        opened = false
                    })
                opened = true
            }
        }

        ClientPlayNetworking.registerGlobalReceiver(PlayerDataPayload.ID, schipaoAdventureClient::handlePlayerDataPayload)
    }
}