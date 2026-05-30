package sh.ftp.schipao.schipaoadventure.mixin

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import org.spongepowered.asm.mixin.Debug
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Unique
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import sh.ftp.schipao.schipaoadventure.PlayerData

@Debug(export = true)
@Mixin(PlayerEntity::class)
abstract class PlayerEntityMixin : PlayerData {

    /*
        0 -> poaceae
        1 -> aer
        2 -> aqua
        3 -> ignis
     */

    @Unique
    override var playerClass: Int = -1
        set(value) {
            if (field == value) return
            field = value
            sync()
        }

    @Inject(method = ["writeCustomDataToNbt"], at = [At("TAIL")])
    private fun writeCustomDataToNbt(nbt: NbtCompound, ci: CallbackInfo) {
        (this as PlayerData).serialize(nbt)
        println("SAVING CLASS: $playerClass")
    }

    @Inject(method = ["readCustomDataFromNbt"], at = [At("TAIL")])
    private fun readCustomDataFromNbt(nbt: NbtCompound, ci: CallbackInfo) {
        (this as PlayerData).deserialize(nbt)
        println("Loaded class: $playerClass")
    }
}