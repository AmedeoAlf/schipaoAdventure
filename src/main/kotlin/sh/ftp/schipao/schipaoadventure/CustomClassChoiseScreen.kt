package sh.ftp.schipao.schipaoadventure

import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.gui.DrawContext
import net.minecraft.text.Text
import net.minecraft.client.MinecraftClient
import net.minecraft.util.Identifier

class CustomClassChoiceScreen(title: Text, val onClose: () -> Unit = {}) :Screen(title) {
    var app = 0

    override fun init() {
        val centerX = width / 2
        val centerY = height / 2

        // Chooser Button
        val btnClass = ButtonWidget.builder( Text.literal("Select") )
        {
            showToast()
        }
            .dimensions(centerX - 60, centerY + 80, 120, 20)
            .build()

        // Arrows Buttons
        val btnArrowR = ButtonWidget.builder( Text.literal(">") )
        {
            app = (app + 1) % 4
        }
            .dimensions(centerX + 125, centerY - 15, 10, 30)
            .build()
        val btnArrowL = ButtonWidget.builder( Text.literal("<") )
        {
            app = (app - 1 + 4) % 4
        }
            .dimensions(centerX - 135, centerY - 15, 10, 30)
            .build()

        addDrawableChild(btnClass)
        addDrawableChild(btnArrowR)
        addDrawableChild(btnArrowL)
    }

    private fun showToast() {
        val player = MinecraftClient.getInstance().player

        if (player != null) {

            val data = player as PlayerData
            data.playerClass = app

            MinecraftClient.getInstance().player?.sendMessage(
                Text.literal("Class: ${(player as PlayerData).playerClass}"), false
            )

            onClose()
            MinecraftClient.getInstance().setScreen(null)
        }
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(context, mouseX, mouseY, delta)

        val texture = Identifier.of(
            SchipaoAdventure.MOD_ID,
            "textures/gui/book.png"
        )

        val rune = Identifier.of(
            SchipaoAdventure.MOD_ID,
            "textures/gui/class/$app.png"
        )

        context.drawTexture(
            texture,
            width / 2 - 133, height / 2 - 88, // Coordinate angolo NW
            0f, 0f,
            266, 176, // Texture che viene mostrata
            266, 176 // Pixel texture
        )

        context.drawTexture(
            rune,
            width / 2 - 8, height / 2 - 8,
            0f, 0f,
            16, 16,
            16, 16
        )
    }
}