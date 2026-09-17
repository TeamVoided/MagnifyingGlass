package org.teamvoided.template.payloads

import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type
import net.minecraft.world.item.ItemStack
import org.teamvoided.template.Template

class ClientBoundOminousItemsPayload(val items: List<ItemStack>) : CustomPacketPayload {

    override fun type(): Type<out CustomPacketPayload?> = TYPE

    companion object {
        val TYPE: Type<ClientBoundOminousItemsPayload> = Type(Template.id("items"))
        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, ClientBoundOminousItemsPayload> = StreamCodec.composite(
            ByteBufCodecs.list<RegistryFriendlyByteBuf, ItemStack>().apply(ItemStack.STREAM_CODEC),
            ClientBoundOminousItemsPayload::items,
        ) { ClientBoundOminousItemsPayload(it) }
    }
}