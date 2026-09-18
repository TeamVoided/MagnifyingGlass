package org.teamvoided.template.client

import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import me.fzzyhmstrs.fzzy_config.api.RegisterType
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import org.teamvoided.template.Template
import org.teamvoided.template.config.TemplateConfig
import org.teamvoided.template.payloads.ServerBoundRequestOminousItemsPayload

object TemplateClient {

    @JvmField
    var config = ConfigApi.registerAndLoadConfig(::TemplateConfig, RegisterType.CLIENT)

    var oldPos: BlockPos? = null
    var oldSectionPos: BlockPos? = null

    fun init() {
        Template.log.info("Hello from Client")
        ClientTickEvents.END_WORLD_TICK.register {
            if (!config.displayOminousItems) return@register
            val pos = Minecraft.getInstance().gameRenderer.mainCamera.blockPosition.immutable() ?: return@register
            if (pos != oldPos) {
                oldPos = pos

                val sectionPos = Template.spawnerSectionPos(pos)
                if (oldSectionPos != sectionPos) {
                    oldSectionPos = sectionPos

                    if (ConfigApi.network().canSend(ServerBoundRequestOminousItemsPayload.TYPE.id, null)) {
                        ConfigApi.network().send(ServerBoundRequestOminousItemsPayload(pos), null)
                    }
                }
            }
        }

        HudRenderCallback.EVENT.register { graphics, tracker ->
            if (!config.displayOminousItems) return@register
            Template.items ?: return@register
            val font = Minecraft.getInstance().font
            val component = Component.literal("Ominous Spawner Items: ")
            Template.items!!.forEach { stack ->
                component.append(stack.hoverName ?: Component.literal("Empty")).append(", ")
            }
            graphics.drawCenteredString(font, component, graphics.guiWidth() / 2, graphics.guiHeight() - 80, 0xFFFFFF)
        }
    }

}