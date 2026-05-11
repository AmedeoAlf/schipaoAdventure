package sh.ftp.schipao.schipaoadventure.mixin

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

    @Unique
    private var selectedClass: Int = -1

    override fun getSelectedClass(): Int {
        return selectedClass
    }

    override fun setSelectedClass(value: Int) {
        selectedClass = value
    }

    @Inject(method = ["writeCustomDataToNbt"], at = [At("TAIL")])
    private fun writeCustomDataToNbt(nbt: NbtCompound, ci: CallbackInfo) {
        nbt.putInt("SelectedClass", selectedClass)
    }

    @Inject(method = ["readCustomDataFromNbt"], at = [At("TAIL")])
    private fun readCustomDataFromNbt(nbt: NbtCompound, ci: CallbackInfo) {
        selectedClass = nbt.getInt("SelectedClass")
    }
}