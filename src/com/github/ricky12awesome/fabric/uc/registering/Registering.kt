@file:Suppress("UNCHECKED_CAST")

package com.github.ricky12awesome.fabric.uc.registering

import com.github.ricky12awesome.fabric.uc.utils.initializeObjectClasses
import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import kotlin.reflect.KClass

data class BlockItemPair<T : Block>(val block: T, val item: BlockItem)

abstract class Registerer(val modid: String) {
  fun with(id: String) = id(modid, id)
  fun asBlockItem(id: String, settings: Item.Settings) = id(modid, id) to settings

  open fun initialize() {
    this::class.objectInstance

    initializeObjectClasses(this::class.nestedClasses)
  }

  fun <T> register(
    entry: T,
    id: String,
    registry: Registry<T>
  ): T = register(entry, with(id), registry)

  infix fun <T : Item> T.register(id: Identifier): T = registerItem(this, id)
  infix fun <T : Block> T.register(id: Identifier): T = registerBlock(this, id)
  infix fun <T : BlockEntityType<*>> T.register(id: Identifier): T = registerBlockEntity(this, id)

  infix fun <T : Block> T.register(pair: Pair<Identifier, Item.Settings>): BlockItemPair<T> {
    val (id, settings) = pair
    val block = registerBlock(this, id)
    val item = registerItem(BlockItem(block, settings), id)

    return BlockItemPair(block, item)
  }
}

fun <T> register(
  entry: T,
  id: Identifier,
  registry: Registry<T>
): T = Registry.register(registry, id, entry)

fun <T : Item> registerItem(
  entry: T,
  id: Identifier
): T = register(entry, id, Registry.ITEM) as T

fun <T : Block> registerBlock(
  entry: T,
  id: Identifier
): T = register(entry, id, Registry.BLOCK) as T

fun <T : BlockEntityType<*>> registerBlockEntity(
  entry: T,
  id: Identifier
): T = register(entry, id, Registry.BLOCK_ENTITY) as T











