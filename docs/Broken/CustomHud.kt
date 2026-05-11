//package sh.ftp.schipao.schipaoadventure.client
//
//import net.fabricmc.api.ClientModInitializer
//import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
//import net.minecraft.client.MinecraftClient
//import net.minecraft.client.gui.DrawContext
//import net.minecraft.util.Identifier
//
//class CustomHud : ClientModInitializer {
//
//    private val texture = Identifier.of(
//        "schipaoadventure",
//        "textures/gui/hotbar.png"
//    )
//
//    override fun onInitializeClient() {
//        HudRenderCallback.EVENT.register { context, tickDelta ->
//            renderHotbar(context)
//        }
//    }
//
//    private fun renderHotbar(context: DrawContext) {
//        val client = MinecraftClient.getInstance()
//        val width = client.window.scaledWidth
//        val height = client.window.scaledHeight
//
//        val x = width / 2 - 111
//        val y = height - 20
//
//        context.drawTexture(
//            texture,
//            x, y,
//            0f, 0f,
//            222, 41,
//            222, 41
//        )
//    }
//}