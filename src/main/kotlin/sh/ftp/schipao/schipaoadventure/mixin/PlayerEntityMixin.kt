package sh.ftp.schipao.schipaoadventure.mixin

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.network.ServerPlayerEntity
import org.spongepowered.asm.mixin.Debug
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Unique
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import sh.ftp.schipao.schipaoadventure.PlayerClassPayload
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
        set(value) {
            field = value

            val entity = this as PlayerEntity
            if (entity is ServerPlayerEntity) {
                server!!.execute {
                    ServerPlayNetworking.send(entity, PlayerClassPayload(value))
                }
            } else {
                ClientPlayNetworking.send(PlayerClassPayload(value))
            }

        }

    @Inject(method = ["writeCustomDataToNbt"], at = [At("TAIL")])
    private fun writeCustomDataToNbt(nbt: NbtCompound, ci: CallbackInfo) {
        for (member in PlayerData::class.memberProperties) {
            nbt.put(
                member.name, member.get(this)?.toNbtElement(member.returnType.arguments)
            )
        }
        println("SAVING CLASS: $playerClass")
    }

    @Inject(method = ["readCustomDataFromNbt"], at = [At("TAIL")])
    private fun readCustomDataFromNbt(nbt: NbtCompound, ci: CallbackInfo) {
        for (member in PlayerData::class.memberProperties) {
            if (member !is KMutableProperty<*>) continue
            member.setter.call(this, nbt.get(member.name)!!.toOriginal(member.returnType))
        }
        println("Loaded class: $playerClass")
    }
}