package org.teamvoided.template.payloads

import net.minecraft.core.BlockPos
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type
import org.teamvoided.template.Template

class ServerBoundRequestOminousItemsPayload(val pos: BlockPos) : CustomPacketPayload {

    override fun type(): Type<out CustomPacketPayload?> = TYPE

    companion object {
        val TYPE: Type<ServerBoundRequestOminousItemsPayload> = Type(Template.id("request_ominous_spawner_items"))
        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, ServerBoundRequestOminousItemsPayload> =
            StreamCodec.composite(
                BlockPos.STREAM_CODEC, ServerBoundRequestOminousItemsPayload::pos,
                ::ServerBoundRequestOminousItemsPayload
            )
    }
}