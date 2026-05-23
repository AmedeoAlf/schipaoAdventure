package sh.ftp.schipao.schipaoadventure.item.custom

import net.minecraft.client.MinecraftClient
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World
import sh.ftp.schipao.schipaoadventure.CustomClassChoiceScreen

class OriginStarItem(settings: Settings) : Item(settings){

    override fun use(
        world: World,
        user: PlayerEntity,
        hand: Hand
    ): TypedActionResult<ItemStack?> {

        val stack = user.getStackInHand(hand)

        if (world.isClient) {
            MinecraftClient.getInstance().setScreen(
                CustomClassChoiceScreen(
                    Text.literal("Choose Class")
                )
            )
        }

        return TypedActionResult.success(stack, world.isClient)
    }
}
