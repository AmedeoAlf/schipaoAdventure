package sh.ftp.schipao.schipaoadventure.mixin

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.*
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Unique
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import sh.ftp.schipao.schipaoadventure.PlayerData
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.memberProperties

fun Any.toNbtElement(typeParams: List<KTypeProjection>): NbtElement = when (this) {
    is Byte -> NbtByte.of(this)
    is Int -> NbtInt.of(this)
    is Short -> NbtShort.of(this)
    is Long -> NbtLong.of(this)

    is String -> NbtString.of(this)
    is Boolean -> NbtByte.of(this)

    is Float -> NbtFloat.of(this)
    is Double -> NbtDouble.of(this)

    is List<*> -> NbtList().apply {
        val type = typeParams.first().type!!
        this@toNbtElement.forEach {
            add(it!!.toNbtElement(type.arguments))
        }
    }

    is Map<*, *> -> NbtCompound().apply {
        val type = typeParams[1].type!!
        this@toNbtElement.forEach { (k, v) ->
            if (v != null) put(k.toString(), v.toNbtElement(type.arguments))
        }
    }

    else -> throw Exception("Can't cast value $this to nbt")
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
        for (member in PlayerData::class.memberProperties) nbt.put(
            member.name, member.get(this)?.toNbtElement(member.returnType.arguments)
        )
    }

    @Inject(method = ["readCustomDataFromNbt"], at = [At("TAIL")])
    private fun readCustomDataFromNbt(nbt: NbtCompound, ci: CallbackInfo) {
        playerClass = nbt.getInt("SelectedClass")
    }
}