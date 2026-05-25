package sh.ftp.schipao.schipaoadventure

import com.mojang.brigadier.arguments.IntegerArgumentType
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.server.command.CommandManager
import net.minecraft.text.Text
import sh.ftp.schipao.schipaoadventure.PlayerData
import sh.ftp.schipao.schipaoadventure.playerclasses.playerClasses

object ComandCustom {

    fun register() {

        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->

            dispatcher.register(
                CommandManager.literal("class")
                    .then(CommandManager.argument(
                        "name",
                        StringArgumentType.word()
                    )
                        .suggests{ctx, builder -> {
                            playerClasses.forEach { 
                                builder.suggest(it.name)
                            }

                            builder.build()
                        }}
                        .executes { context ->
                            val player = context.source.playerOrThrow

                            val data = player as PlayerData
                            val decided = context.getArgument("name", String.javaClass)

                            data.playerClass = playerClasses.indexOfFirst { it.name == decided }

                            context.source.sendFeedback(
                                { 
                                    if (decided == -1)
                                        Text.literal("Class reset successful")
                                    else 
                                        Text.literal("Class changed successfully to ${playerClasses[decided].name}") 
                                },
                                false
                            )

                            1
                        }
                    )                   
                    .executes { context ->
                        val player = context.source.playerOrThrow

                        val data = player as PlayerData
                        val playerClass = data.playerClass

                        context.source.sendFeedback(
                            { Text.literal("Your class is: $playerClass") },
                            false
                        )

                        1
                    }
            )
        }
    }
}