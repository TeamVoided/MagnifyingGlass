package org.teamvoided.template.data.registry

import net.minecraft.core.registries.Registries
import org.teamvoided.template.Template
import org.teamvoided.template.util.key

object TemplatePaintings {

    val APPLE_PAINT = create("apple_paint")

    fun create(id: String) = Registries.PAINTING_VARIANT.key(Template.id(id))

}