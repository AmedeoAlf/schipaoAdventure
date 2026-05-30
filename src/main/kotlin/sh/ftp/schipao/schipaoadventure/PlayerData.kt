package sh.ftp.schipao.schipaoadventure

import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.network.packet.CustomPayload
import net.minecraft.util.Identifier
import sh.ftp.schipao.schipaoadventure.SchipaoAdventure.MOD_ID

interface PlayerData {
    var playerClass: Int
}

class PlayerClassPayload(val pClass: Int): CustomPayload {
    companion object {
        val ID = CustomPayload.Id<PlayerClassPayload>(Identifier.of(MOD_ID, "playerdata"))
        val CODEC = PacketCodec.tuple(PacketCodecs.INTEGER, PlayerClassPayload::pClass, PlayerClassPayload::make)

        fun make(pClass: Int) = PlayerClassPayload(pClass)
    }

    override fun getId() = ID
}
