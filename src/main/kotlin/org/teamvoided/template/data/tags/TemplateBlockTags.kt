package org.teamvoided.template.data.tags

import net.minecraft.core.registries.Registries
import org.teamvoided.template.Template.id
import org.teamvoided.template.util.tag

object TemplateBlockTags {

    val APPLE_LIKE = create("apple_like")

    fun create(id: String) = Registries.BLOCK.tag(id(id))

}