package org.teamvoided.template.datagen.data.tags

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.BlockTagProvider
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags
import net.minecraft.core.HolderLookup
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Blocks
import org.teamvoided.template.data.tags.TemplateBlockTags
import org.teamvoided.template.init.TemplateBlocks
import java.util.concurrent.CompletableFuture

class BlockTagsProvider(o: FabricDataOutput, p: CompletableFuture<HolderLookup.Provider>) : BlockTagProvider(o, p) {

    override fun addTags(lookup: HolderLookup.Provider) {
        getOrCreateTagBuilder(BlockTags.LOGS)
            .add(TemplateBlocks.SPECIAL_APPLE_BLOCK)

        getOrCreateTagBuilder(TemplateBlockTags.APPLE_LIKE)
            .add(TemplateBlocks.APPLE_BLOCK, Blocks.RED_TERRACOTTA)

        getOrCreateTagBuilder(ConventionalBlockTags.STORAGE_BLOCKS)
            .forceAddTag(TemplateBlockTags.APPLE_LIKE)
    }

}