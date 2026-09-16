package org.teamvoided.template.init

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour.Properties.ofFullCopy
import org.teamvoided.template.Template.id
import org.teamvoided.template.util.getModEntries
import org.teamvoided.template.util.register

object TemplateBlocks {

    val BLOCKS get() = getModEntries(BuiltInRegistries.BLOCK)

    val APPLE_BLOCK = register("apple_block", Block(ofFullCopy(Blocks.RED_TERRACOTTA)))
    val SPECIAL_APPLE_BLOCK = registerNoItem("special_apple_block", Block(ofFullCopy(Blocks.NETHERITE_BLOCK)))

    fun init() = Unit

    fun register(id: String, block: Block, properties: Item.Properties = Item.Properties()): Block {
        val registeredBlock = registerNoItem(id, block)
        TemplateItems.register(id, BlockItem(registeredBlock, properties))
        return registeredBlock
    }

    fun registerNoItem(id: String, block: Block): Block = BuiltInRegistries.BLOCK.register(id(id), block)

}