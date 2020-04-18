package com.miguel.model

import com.miguel.Main
import com.miguel.Manager
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.FallingBlock
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.sqrt


class ChibakuTensei {

    private val manager = Manager()

    companion object {
        val designatedLocation: HashMap<Entity, Location> = HashMap()
        val put: MutableList<Location> = ArrayList()
    }

    fun chibaku(location: Location, radius: Int) {
        val add = location.clone().add(0.0, 50.0, 0.0)

        val toSphere = manager.generateSphere(add, radius, false)
        var sphere = manager.sphere(location, radius)

        var i = radius
        while (sphere.size < toSphere.size) {
            i++
            sphere = manager.sphere(location, i)
        }

        toSphere.sortWith(Comparator.comparingDouble { obj: Location -> obj.y })
        toSphere.reverse()
        toSphere.sortWith(Comparator.comparingDouble { value: Location -> value.distance(add) })

        sphere.sortWith(Comparator.comparingDouble { value: Location -> value.distance(location) })
        sphere.sortWith(Comparator.comparingDouble { obj: Location -> obj.y })
        sphere.reverse()

        object : BukkitRunnable() {
            var x = -1
            val world = location.world
            override fun run() {
                if (x != toSphere.size - 1) {
                    x++

                    val fromLocation = sphere[x]
                    val toLocation = toSphere[x]

                    val fallingBlockEntity = world.spawnEntity(fromLocation, EntityType.FALLING_BLOCK)

                    fallingBlockEntity.velocity = Vector(.0, 2.0, .0)
                    designatedLocation[fallingBlockEntity] = toLocation

                } else {
                    cancel()
                }
            }
        }.runTaskTimer(Main.instace, 0L, 0L)

        val s = arrayOf(designatedLocation.values)

        object : BukkitRunnable() {
            override fun run() {
                if (put.size == toSphere.size)
                    cancel()

                designatedLocation.forEach { entry ->
                    val entity = entry.key
                    val designated = entry.value

                    val fallingBlock = entity as FallingBlock

                    put.forEach { putt ->
                        if (entity.location.distance(putt) <= radius - (radius * 0.3)) {
                            designated.block.type = mat(fallingBlock.material)

                            put.add(designated)

                            designatedLocation.remove(entity)

                            entity.remove()
                            return
                        }
                    }

                    if (entity.location.distance(designated) <= radius - (radius * 0.3)) {
                        designated.block.type = mat(fallingBlock.material)

                        put.add(designated)

                        designatedLocation.remove(entity)

                        entity.remove()
                        return
                    }

                    val subtract = designated.toVector().subtract(entity.location.toVector())

                    subtract.y *= 17.5 / (sqrt(designated.y - entity.location.y))

                    entity.velocity = subtract.multiply(0.003).normalize()
                }
            }

            override fun cancel() {
                designatedLocation.clear()
                put.clear()

                super.cancel()
            }
        }.runTaskTimer(Main.instace, 0L, 0L)
    }

    fun mat(material: Material): Material {
        val ret: Material

        when (material) {
            Material.STONE -> {
                ret = if ((0..1).random() > 0.5) Material.STONE else Material.COBBLESTONE
            }

            Material.GRAVEL -> {
                ret = if ((0..1).random() > 0.5) Material.STONE else Material.COBBLESTONE
            }

            Material.GRASS -> {
                ret = Material.DIRT
            }

            Material.LONG_GRASS -> {
                ret = Material.DIRT
            }

            Material.GRASS_PATH -> {
                ret = Material.DIRT
            }

            Material.SAND -> {
                ret = Material.SANDSTONE
            }

            else -> {
                ret = material
            }
        }

        return ret
    }
}