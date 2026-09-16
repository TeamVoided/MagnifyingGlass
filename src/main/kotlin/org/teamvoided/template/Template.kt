package org.teamvoided.template

import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
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
import org.teamvoided.template.config.TemplateConfig
import java.util.*

object Template {

    const val MODID = "template"

    @JvmField
    val log: Logger = LoggerFactory.getLogger(Template::class.simpleName)

    @JvmField
    var config = ConfigApi.registerAndLoadConfig(::TemplateConfig)

    var items: List<ItemStack?>? = null
    var oldPlayerPoses = mutableMapOf<UUID, BlockPos>() // TODO cache invalidation :) or better make it so the client toggles it

    fun init() {
        log.info("Hello from Common ${config.commonEntry.get()}")
        ConfigApiJava.network().registerS2C(
            ClientBoundLootPayload.TYPE,
            ClientBoundLootPayload.STREAM_CODEC
        ) { payload, context -> context.execute { items = payload.items } }

        ServerTickEvents.END_WORLD_TICK.register { serverLevel ->
            serverLevel.players().forEach {
                if (oldPlayerPoses[it.uuid] != it.blockPosition()) {
                    oldPlayerPoses[it.uuid] = it.blockPosition()
                    if (!ConfigApiJava.network().canSend(ClientBoundLootPayload.TYPE.id, it)) return@forEach
                    ServerPlayNetworking.send(
                        it,
                        ClientBoundLootPayload(getDispensingItems(serverLevel, it.blockPosition()))
                    )
                }
            }
        }
    }

    fun getDispensingItems(
        serverLevel: ServerLevel,
        blockPos: BlockPos
    ): List<ItemStack?> {
        val lootTable =
            serverLevel.server.reloadableRegistries()
                .getLootTable(BuiltInLootTables.SPAWNER_TRIAL_ITEMS_TO_DROP_WHEN_OMINOUS)
        val lootParams = (LootParams.Builder(serverLevel)).create(LootContextParamSets.EMPTY)
        val seed = lowResolutionPosition(serverLevel, blockPos)
        return lootTable.getRandomItems(lootParams, seed)
    }

    private fun lowResolutionPosition(serverLevel: ServerLevel, blockPos: BlockPos): Long {
        val blockPos2 = BlockPos(
            Mth.floor(blockPos.x.toFloat() / 30f),
            Mth.floor(blockPos.y.toFloat() / 20f),
            Mth.floor(blockPos.z.toFloat() / 30f)
        )
        return serverLevel.seed + blockPos2.asLong()
    }

    fun id(namespace: String, path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(namespace, path)
    fun mc(path: String): ResourceLocation = ResourceLocation.withDefaultNamespace(path)
    fun id(path: String) = id(MODID, path)

}