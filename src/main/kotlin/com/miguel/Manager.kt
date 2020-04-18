package com.miguel

import org.bukkit.Location
import java.util.*

class Manager {

    fun generateSphere(centerBlock: Location, radius: Int, hollow: Boolean): MutableList<Location> {
        val circleBlocks: MutableList<Location> = ArrayList<Location>()

        val bx: Int = centerBlock.blockX
        val by: Int = centerBlock.blockY
        val bz: Int = centerBlock.blockZ

        for (x in bx - radius..bx + radius) {
            for (y in by - radius..by + radius) {
                for (z in bz - radius..bz + radius) {
                    val distance =
                        ((bx - x) * (bx - x) + (bz - z) * (bz - z) + (by - y) * (by - y)).toDouble()

                    if (distance < radius * radius && !(hollow && distance < (radius - 1) * (radius - 1))) {
                        val l = Location(centerBlock.world, x.toDouble(), y.toDouble(), z.toDouble())
                        circleBlocks.add(l)
                    }
                }
            }
        }
        return circleBlocks
    }

    fun sphere(location: Location, radius: Int): MutableList<Location> {
        val list: MutableList<Location> = ArrayList<Location>()
        for (i in 0 until radius) {
            for (x in -radius..radius) {
                for (z in -radius..radius) {
                    val loc = Location(
                        location.world,
                        location.x + (x - i / 2),
                        location.y - i,
                        location.z + (z - i / 2)
                    )

                    if (loc.distance(location) < radius) {
                        list.add(loc)
                    }
                }
            }
        }
        return list
    }
}