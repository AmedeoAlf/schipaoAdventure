package sh.ftp.schipao.schipaoadventure

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.network.packet.CustomPayload
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier
import sh.ftp.schipao.schipaoadventure.SchipaoAdventure.MOD_ID
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties

interface PlayerData {
    var playerClass: Int

    fun sync() = if (this !is ServerPlayerEntity)
        ClientPlayNetworking.send(PlayerDataPayload(this))
    else
        server!!.execute {
            ServerPlayNetworking.send(this, PlayerDataPayload(this))
        }

    fun serialize() = NbtCompound().also { this.serialize(it) }
    fun serialize(into: NbtCompound) {
        for (member in PlayerData::class.memberProperties) {
            into.put(
                member.name, member.get(this)?.toNbtElement(member.returnType.arguments)
            )
        }
    }

    fun deserialize(data: NbtCompound) {
        for (member in PlayerData::class.memberProperties) {
            if (member !is KMutableProperty<*>) continue
            member.setter.call(this, data.get(member.name)!!.toOriginal(member.returnType))
        }
    }
}

class PlayerDataPayload(val data: NbtCompound) : CustomPayload {
    companion object {
        val ID = CustomPayload.Id<PlayerDataPayload>(Identifier.of(MOD_ID, "playerdata"))
        val CODEC = PacketCodec.tuple(PacketCodecs.NBT_COMPOUND, PlayerDataPayload::data) { PlayerDataPayload(it) }
    }

    constructor(playerData: PlayerData) : this(playerData.serialize())

    override fun getId() = ID
}
