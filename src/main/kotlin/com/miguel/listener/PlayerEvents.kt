package com.miguel.listener

import com.miguel.model.ChibakuTensei
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class PlayerEvents : Listener {

    companion object {
        val chibakuTensei = ChibakuTensei()
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player
        val location = event.block.location

        chibakuTensei.chibaku(location, 10)
    }
}