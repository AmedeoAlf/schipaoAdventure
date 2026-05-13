package sh.ftp.schipao.schipaoadventure

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.text.Text
import sh.ftp.schipao.schipaoadventure.item.ModItems

class schipaoAdventureClient : ClientModInitializer {

    private var opened = false
    private var wasUsing = false

    override fun onInitializeClient() {

        ClientTickEvents.END_CLIENT_TICK.register { client ->
            val player = client.player ?: return@register
            val data = player as PlayerData

            if (!opened) {
                if (data.playerClass == -1) {
                    client.setScreen(
                        CustomClassChoiceScreen(
                            Text.literal("Choose Class")
                        )
                    )
                }
                opened = true
            }

            if (client.currentScreen != null) {
                wasUsing = true
                return@register
            }

            val using = client.options.useKey.isPressed

            if (using && !wasUsing) {
                if (player.mainHandStack.item == ModItems.STAR_OF_ORIGINS) {
                    client.setScreen(
                        CustomClassChoiceScreen(
                            Text.literal("Choose Class")
                        )
                    )
                }
            }

            wasUsing = using
        }
    }
}