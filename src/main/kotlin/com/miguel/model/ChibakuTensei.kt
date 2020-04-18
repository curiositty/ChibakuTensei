package com.miguel.model

import com.miguel.Manager
import org.bukkit.Location
import org.bukkit.entity.Entity

class ChibakuTensei {

    private val manager = Manager()

    companion object {
        val designatedLocation: HashMap<Entity, Location> = HashMap()
        val put: MutableList<Location> = ArrayList()
    }

    fun chibaku(location: Location, radius: Int) {
        val add = location.clone().add(0.0, 25.0, 0.0)

        val toSphere = manager.generateSphere(add, radius, false)
        var sphere = manager.sphere(location, radius)

        var i = radius
        while(sphere.size < toSphere.size) {
            i++
            sphere = manager.sphere(location, i)
        }

        toSphere.sortedBy { location -> location.y }
        toSphere.reversed()
        toSphere.sortedBy { location -> location.distance(add) }

        sphere.sortedBy { loc -> loc.distance(location) }
        sphere.sortedBy { loc -> loc.y }
        sphere.reversed()


    }
}