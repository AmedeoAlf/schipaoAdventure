package sh.ftp.schipao.schipaoadventure

import net.minecraft.nbt.NbtByte
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtDouble
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtFloat
import net.minecraft.nbt.NbtInt
import net.minecraft.nbt.NbtList
import net.minecraft.nbt.NbtLong
import net.minecraft.nbt.NbtShort
import net.minecraft.nbt.NbtString
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.typeOf

fun Any.toNbtElement(typeParams: List<KTypeProjection>): NbtElement =
    when (this) {
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

fun NbtElement.toOriginal(type: KType): Any = when (this) {
    is NbtByte -> if (type == typeOf<Byte>()) this.byteValue() else throw TypeCastException("Can't assign byte to $type")
    is NbtShort -> if (type == typeOf<Short>()) this.shortValue() else throw TypeCastException("Can't assign short to $type")
    is NbtInt -> if (type == typeOf<Int>()) this.intValue() else throw TypeCastException("Can't assign int to $type")
    is NbtLong -> if (type == typeOf<Long>()) this.longValue() else throw TypeCastException("Can't assign long to $type")

    is NbtFloat -> if (type == typeOf<Float>()) this.floatValue() else throw TypeCastException("Can't assign float to $type")
    is NbtDouble -> if (type == typeOf<Double>()) this.doubleValue() else throw TypeCastException("Can't assign double to $type")

    is NbtString -> if (type == typeOf<String>()) this.asString() else throw TypeCastException("Can't assign string to $type")

    is NbtList -> if ((type as? KClass<*>)?.isSubclassOf(List::class) == true) this.toList()
        .map { it.toOriginal(type.arguments[0].type!!) }
    else throw TypeCastException("Can't assign list to $type")

    is NbtCompound -> if ((type as? KClass<*>)?.isSubclassOf(Map::class) == true) this.keys.associateWith {
        this.get(it)!!.toOriginal(type.arguments[1].type!!)
    }
    else throw TypeCastException("Can't assign compound to $type")


    else -> throw TypeCastException("Can't cast value $this from nbt")
}

