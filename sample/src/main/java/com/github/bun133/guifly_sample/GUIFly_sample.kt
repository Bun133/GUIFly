package com.github.bun133.guifly_sample

import dev.kotx.flylib.command.Command
import dev.kotx.flylib.flyLib
import org.bukkit.plugin.java.JavaPlugin

class GUIFly_sample :JavaPlugin() {
    init {
        flyLib{
            command()
        }
    }

    override fun onEnable() {
    }

    override fun onDisable() {
    }
}

class GUISampleCommand:Command("s"){
    init {
        description("Open sample GUI")
        usage{
            executes{

            }
        }
    }
}