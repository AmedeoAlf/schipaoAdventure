package sh.ftp.schipao.schipaoadventure.mixin

import kotlin.reflect.full.memberProperties
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Unique
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import sh.ftp.schipao.schipaoadventure.PlayerData

@Mixin(PlayerEntity::class)
class PlayerEntityMixin : PlayerData {

    /*
        0 -> poaceae
        1 -> aer
        2 -> aqua
        3 -> ignis
     */

    @Unique
    override var playerClass: Int = -1

    @Inject(method = ["writeCustomDataToNbt"], at = [At("TAIL")])
    private fun writeCustomDataToNbt(nbt: NbtCompound, ci: CallbackInfo) {
        nbt.putInt("SelectedClass", playerClass)
        for (member in PlayerData::class.memberProperties) {
            nbt.putInt(member.name, member.get(this) as Int)
        }
        
    }

    @Inject(method = ["readCustomDataFromNbt"], at = [At("TAIL")])
    private fun readCustomDataFromNbt(nbt: NbtCompound, ci: CallbackInfo) {
        playerClass = nbt.getInt("SelectedClass")
    }
}