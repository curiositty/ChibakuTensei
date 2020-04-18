package com.miguel

import com.miguel.listener.PlayerEvents
import com.miguel.utils.Strings
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    companion object {
        var instace: Main? = null
    }

    override fun onLoad() {
        println("${Strings.PREFIX} Carregando...")

        instace = this

        dataFolder.mkdir()

        config.options().copyDefaults()
        config.options().copyHeader()

        saveDefaultConfig()

        println("${Strings.PREFIX} Carregado com sucesso!")
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(PlayerEvents(), this)

        println("${Strings.PREFIX} Plugin ativado")
    }

    override fun onDisable() {
        saveConfig()

        HandlerList.unregisterAll(this)

        println("${Strings.PREFIX} Plugin desativado")
    }
}