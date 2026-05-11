package sh.ftp.schipao.schipaoadventure

import net.minecraft.world.PersistentState;
import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.RegistryWrapper


class SavedDataTest : PersistentState() {

    override fun writeNbt(nbt: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup) = nbt.apply {
        putInt("Funy number", 67)
        put("nested", NbtCompound().apply{
            listOf(
                "a" to 1,
                "b" to 2,
                "c" to 3
            ).forEach{
                nested.putInt(it.first, it.second)
            }
        })
    }
    
}