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

fun NbtCompound.putAny(key: String, value: Any?) = when(value) {
    is Byte -> putByte(key, value)
    is Int -> putInt(key, value)
    is Short -> putShort(key, value)
    is Long -> putLong(key, value)

    is String -> putString(key, value)

    is Boolean -> putBoolean(key, value)

    is Float -> putFloat(key, value)
    is Double -> putDouble(key, value)

    // TODO: list, compound

    else -> throw Exception("Can't cast value $value to nbt")
}

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
        for (member in PlayerData::class.memberProperties)
            nbt.putAny(member.name, member.get(this))
    }

    @Inject(method = ["readCustomDataFromNbt"], at = [At("TAIL")])
    private fun readCustomDataFromNbt(nbt: NbtCompound, ci: CallbackInfo) {
        playerClass = nbt.getInt("SelectedClass")
    }
}