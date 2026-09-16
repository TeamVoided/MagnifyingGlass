package org.teamvoided.template.datagen.data

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.world.item.Items
import org.teamvoided.template.init.TemplateItems
import java.util.concurrent.CompletableFuture

class RecipeProvider(o: FabricDataOutput, p: CompletableFuture<HolderLookup.Provider>) : FabricRecipeProvider(o, p) {

    override fun buildRecipes(e: RecipeOutput) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TemplateItems.APPLE_2)
            .requires(Items.APPLE)
            .requires(Items.APPLE)
            .unlockedBy("has_apple", has(Items.APPLE))
            .unlockedBy("has_apple_2", has(TemplateItems.APPLE_2))
            .save(e)
    }

}