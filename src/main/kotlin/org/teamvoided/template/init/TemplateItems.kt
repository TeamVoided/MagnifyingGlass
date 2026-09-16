package org.teamvoided.template.init

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.Rarity
import org.teamvoided.template.Template.id
import org.teamvoided.template.util.getModEntries
import org.teamvoided.template.util.register

object TemplateItems {

    val ITEMS get() = getModEntries(BuiltInRegistries.ITEM)

    val APPLE_2 = register("apple_2", Item(Properties().rarity(Rarity.RARE)))

    fun init() = Unit

    fun register(id: String, item: Item): Item = BuiltInRegistries.ITEM.register(id(id), item)

}