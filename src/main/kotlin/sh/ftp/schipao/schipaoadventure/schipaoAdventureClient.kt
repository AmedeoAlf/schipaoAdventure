package sh.ftp.schipao.schipaoadventure

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.text.Text

class schipaoAdventureClient :ClientModInitializer {
    private var opened = false

    override fun onInitializeClient() {

        ClientTickEvents.END_CLIENT_TICK.register { client ->

            if (opened) return@register

            val player = client.player ?: return@register
            val data = player as PlayerData

            //if (data.getSelectedClass() > -1) {
                client.setScreen(
                    CustomClassChoiceScreen(
                        Text.literal("Choose Class")
                    )
                )
                opened = true
            //}
        }
    }
}