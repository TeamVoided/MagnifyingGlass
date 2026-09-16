package org.teamvoided.template.datagen.data.tags

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags
import net.minecraft.core.HolderLookup
import net.minecraft.tags.ItemTags
import org.teamvoided.template.data.tags.TemplateBlockTags
import org.teamvoided.template.data.tags.TemplateItemTags
import org.teamvoided.template.init.TemplateBlocks
import org.teamvoided.template.init.TemplateItems
import java.util.concurrent.CompletableFuture

class ItemTagsProvider(o: FabricDataOutput, p: CompletableFuture<HolderLookup.Provider>, blockTag: BlockTagProvider) :
    ItemTagProvider(o, p, blockTag) {

    override fun addTags(lookup: HolderLookup.Provider) {
        copy(TemplateBlockTags.APPLE_LIKE, TemplateItemTags.APPLE_LIKE)
        copy(ConventionalBlockTags.STORAGE_BLOCKS, ConventionalItemTags.STORAGE_BLOCKS)

        getOrCreateTagBuilder(ItemTags.SWORD_ENCHANTABLE)
            .add(TemplateBlocks.APPLE_BLOCK.asItem())
        getOrCreateTagBuilder(TemplateItemTags.APPLE_LIKE)
            .add(TemplateItems.APPLE_2)
        getOrCreateTagBuilder(ConventionalItemTags.HIDDEN_FROM_RECIPE_VIEWERS)
            .add(TemplateItems.APPLE_2)
    }

}