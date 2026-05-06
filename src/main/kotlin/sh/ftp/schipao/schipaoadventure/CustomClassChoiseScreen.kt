package sh.ftp.schipao.schipaoadventure

import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.toast.SystemToast
import net.minecraft.text.Text
import net.minecraft.client.MinecraftClient
import net.minecraft.util.Identifier

class CustomClassChoiceScreen(title: Text) : Screen(title) {

    override fun init() {
        val centerX = width / 2
        val centerY = height / 2

        // Chooser Button
        val btnClass = ButtonWidget.builder( Text.literal("Hello World") )
        {
            showToast()
        }
            .dimensions(centerX - 60, centerY + 40, 120, 20)
            .build()

        // Arrows Buttons
        val btnArrowR = ButtonWidget.builder( Text.literal(">") )
        {

        }
            .dimensions(centerX + 65, centerY - 25, 10, 10)
            .build()

        val btnArrowL = ButtonWidget.builder( Text.literal("<") )
        {

        }
            .dimensions(centerX - 75, centerY - 25, 10, 10)
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

        super.render(context, mouseX, mouseY, delta)

        val texture = Identifier.of(
            SchipaoAdventure.MOD_ID,
            "textures/gui/icon.png"
        )

        context.drawTexture(
            texture,
            0, 0, // Coordinate angolo NW
            0f, 0f,
            300, 300, // Texture che viene mostrata
            1065, 405 // Pixel texture
        )
    }
}