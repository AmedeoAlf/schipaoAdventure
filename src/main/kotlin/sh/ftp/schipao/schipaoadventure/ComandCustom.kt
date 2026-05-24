package com.example.command

import com.mojang.brigadier.arguments.IntegerArgumentType
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.server.command.CommandManager
import net.minecraft.text.Text
import sh.ftp.schipao.schipaoadventure.PlayerData

object ComandCustom {

    fun register() {

        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->

            dispatcher.register(
                CommandManager.literal("setClass")
                    .then(
                        CommandManager.argument(
                            "import",
                            IntegerArgumentType.integer()
                        ).executes { context ->
                            val player = context.source.playerOrThrow

                            val data = player as PlayerData
                            data.playerClass = -1

                            context.source.sendFeedback(
                                { Text.literal("Class reset successful") },
                                false
                            )

                            1
                        }
                    )
            )

            dispatcher.register(
                CommandManager.literal("seeClass")
            )
        }
    }
}