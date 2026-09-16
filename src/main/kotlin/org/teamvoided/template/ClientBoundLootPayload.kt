package org.teamvoided.template

import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type
import net.minecraft.world.item.ItemStack

class ClientBoundLootPayload(val items: List<ItemStack?>) : CustomPacketPayload {

    override fun type(): Type<out CustomPacketPayload?> = TYPE

    companion object {
        val TYPE: Type<ClientBoundLootPayload> = Type(Template.id("loot"))
        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, ClientBoundLootPayload> = StreamCodec.composite(
            ByteBufCodecs.list<RegistryFriendlyByteBuf, ItemStack>().apply(ItemStack.STREAM_CODEC),
            ClientBoundLootPayload::items,
        ) { ClientBoundLootPayload(it) }
    }
}