package org.teamvoided.template.datagen.assets


import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.ItemModelGenerators
import net.minecraft.data.models.model.ModelTemplates
import net.minecraft.data.models.model.TexturedModel
import org.teamvoided.template.init.TemplateBlocks
import org.teamvoided.template.init.TemplateItems

class ModelProvider(o: FabricDataOutput) : FabricModelProvider(o) {

    override fun generateBlockStateModels(gen: BlockModelGenerators) {
        gen.createTrivialCube(TemplateBlocks.APPLE_BLOCK)
        gen.createTrivialBlock(TemplateBlocks.SPECIAL_APPLE_BLOCK, TexturedModel.LEAVES)
    }

    val single = listOf(TemplateItems.APPLE_2)

    override fun generateItemModels(gen: ItemModelGenerators) {
        single.forEach { gen.generateFlatItem(it, ModelTemplates.FLAT_ITEM) }
    }

}