package org.teamvoided.template.client

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import org.teamvoided.template.Template

object TemplateClient {

    fun init() {
        Template.log.info("Hello from Client")
        HudRenderCallback.EVENT.register { graphics, tracker ->
            Template.items ?: return@register
            val font = Minecraft.getInstance().font
            val component = Component.literal("Ominous Spawner Items: ")
            Template.items!!.forEach { stack ->
                component.append(stack?.hoverName ?: Component.literal("Empty")).append(", ")
            }
            graphics.drawCenteredString(font, component, graphics.guiWidth() / 2, graphics.guiHeight() - 80, 0xFFFFFF)
        }
    }

}