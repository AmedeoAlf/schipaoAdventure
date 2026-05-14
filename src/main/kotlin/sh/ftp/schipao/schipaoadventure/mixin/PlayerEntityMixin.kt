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
import sh.ftp.schipao.schipaoadventure.toNbtElement
import sh.ftp.schipao.schipaoadventure.toOriginal
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties

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

    @Inject(method = ["writeCustomDataToNbt"], at = [At("TAIL")])
    private fun writeCustomDataToNbt(nbt: NbtCompound, ci: CallbackInfo) {
        for (member in PlayerData::class.memberProperties) {
            nbt.put(
                member.name, member.get(this)?.toNbtElement(member.returnType.arguments)
            )
        }
    }

    @Inject(method = ["readCustomDataFromNbt"], at = [At("TAIL")])
    private fun readCustomDataFromNbt(nbt: NbtCompound, ci: CallbackInfo) {
        for (member in PlayerData::class.memberProperties) {
            if (member !is KMutableProperty<*>) continue
            member.setter.call(this, nbt.get(member.name)!!.toOriginal(member.returnType))
        }
    }
}