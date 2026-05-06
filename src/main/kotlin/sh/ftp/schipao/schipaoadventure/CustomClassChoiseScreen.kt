package sh.ftp.schipao.schipaoadventure

import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.toast.SystemToast
import net.minecraft.text.Text
import net.minecraft.client.MinecraftClient

class CustomClassChoiceScreen(title: Text) : Screen(title) {

    override fun init() {
        val centerX = width / 2
        val centerY = height / 2

        /*
            texture :Identifier = Identifier.fromNamespaceAndPath(SchipaoAdventure.MOD_ID, "")
            u :int = 10
            v :int = 13
            regionWidth :int = 14
            regionHeight :int = 14

            // renderLayer, texture, x, y, u, v, width, height, regionWidth, regionHeight, textureWidth, textureHeight
            graphics.blit(RenderPipelines.GUI_TEXTURED, texture, 90, 190, u, v, 14, 14, regionWidth, regionHeight, 256, 256)
        */

        // Chooser Button
        val btnClass = ButtonWidget.builder( Text.literal("Hello World") )
        {
            showToast()
        }
            .dimensions(centerX - 60, centerY - 40, 120, 20)
            .build()

        // Arrows Buttons
        val btnArrowR = ButtonWidget.builder( Text.literal(">") )
        {

        }
            .dimensions(centerX + 65, centerY + 25, 10, 10)
            .build()

        val btnArrowL = ButtonWidget.builder( Text.literal("<") )
        {

        }
            .dimensions(centerX - 65, centerY + 25, 10, 10)
            .build()

        addDrawableChild(btnClass)
        addDrawableChild(btnArrowR)
        addDrawableChild(btnArrowL)
    }

    private fun showToast() {
        val client = MinecraftClient.getInstance()

        client.toastManager.add(
            SystemToast.create(
                client,
                SystemToast.Type.NARRATOR_TOGGLE,
                Text.literal("Good choise!"),
                Text.literal("learn to use it")
            )
        )

        MinecraftClient.getInstance().setScreen(null)
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(context, mouseX, mouseY, delta)

        context.drawText(
            textRenderer,
            Text.literal("Special Button"),
            width / 2 - 60,
            height / 2 - 40,
            0xFFFFFF,
            true
        )
    }
}