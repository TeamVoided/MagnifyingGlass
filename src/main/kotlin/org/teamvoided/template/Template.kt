package org.teamvoided.template

import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.Mth
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.storage.loot.BuiltInLootTables
import net.minecraft.world.level.storage.loot.LootParams
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.teamvoided.template.payloads.ClientBoundOminousItemsPayload
import org.teamvoided.template.payloads.ServerBoundRequestOminousItemsPayload

object Template {

    const val MODID = "magnify"

    @JvmField
    val log: Logger = LoggerFactory.getLogger(Template::class.simpleName)

    var items: List<ItemStack>? = null // This is only used clientside

    fun init() {
        ConfigApi.network().registerS2C(
            ClientBoundOminousItemsPayload.TYPE,
            ClientBoundOminousItemsPayload.STREAM_CODEC
        ) { payload, context -> context.execute { items = payload.items } }
        ConfigApi.network().registerC2S(
            ServerBoundRequestOminousItemsPayload.TYPE,
            ServerBoundRequestOminousItemsPayload.STREAM_CODEC,
        ) { payload, context ->
            context.execute {
                val player = context.player()
                if (!ConfigApi.network().canSend(ClientBoundOminousItemsPayload.TYPE.id, player)) return@execute
                ConfigApi.network().send(
                    ClientBoundOminousItemsPayload(getDispensingItems(player.serverLevel(), player.blockPosition())),
                    player
                )
            }
        }
    }

    fun getDispensingItems(
        serverLevel: ServerLevel,
        blockPos: BlockPos
    ): List<ItemStack> {
        val lootTable =
            serverLevel.server.reloadableRegistries()
                .getLootTable(BuiltInLootTables.SPAWNER_TRIAL_ITEMS_TO_DROP_WHEN_OMINOUS)
        val lootParams = (LootParams.Builder(serverLevel)).create(LootContextParamSets.EMPTY)
        val seed = serverLevel.seed + spawnerSectionPos(blockPos).asLong()
        return lootTable.getRandomItems(lootParams, seed)
    }

    fun spawnerSectionPos(blockPos: BlockPos): BlockPos {
        return BlockPos(
            Mth.floor(blockPos.x.toFloat() / 30f),
            Mth.floor(blockPos.y.toFloat() / 20f),
            Mth.floor(blockPos.z.toFloat() / 30f)
        )
    }

    fun id(namespace: String, path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(namespace, path)
    fun mc(path: String): ResourceLocation = ResourceLocation.withDefaultNamespace(path)
    fun id(path: String) = id(MODID, path)

}