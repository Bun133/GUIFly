package com.github.bun133.guifly_sample

import dev.kotx.flylib.command.Command
import dev.kotx.flylib.flyLib
import org.bukkit.plugin.java.JavaPlugin

class GUIFly_sample : JavaPlugin() {
    init {
        flyLib {
            command(GUISampleCommand())
        }
    }

    override fun onEnable() {
    }

    override fun onDisable() {
    }
}

class GUISampleCommand : Command("sample") {
    init {
        description("Open sample GUI")
        usage {
            selectionArgument("selection", "sampleOne", "Infinite","Value")
            executes {
                when (typedArgs[0] as String) {
                    "sampleOne" -> {
                        val gui = SampleOne().main(plugin)
                        if (this.player != null) {
                            gui.open(this.player!!)
                            success("Opened!")
                        } else {
                            fail("Player is null")
                        }
                    }
                    "Infinite" -> {
                        val gui = InfiniteSample().main(plugin)
                        if (this.player != null) {
                            gui.open(this.player!!)
                            success("Opened!")
                        } else {
                            fail("Player is null")
                        }
                    }
                    "Value" -> {
                        val gui = ValueSample().main(plugin)
                        if (this.player != null) {
                            gui.open(this.player!!)
                            success("Opened!")
                        } else {
                            fail("Player is null")
                        }
                    }
                    else -> fail("Invalid argument")
                }

            }
        }
    }
}