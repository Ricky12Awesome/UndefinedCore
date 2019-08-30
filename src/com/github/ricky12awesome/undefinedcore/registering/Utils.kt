package com.github.ricky12awesome.undefinedcore.registering

import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import java.util.function.Supplier
import com.mojang.datafixers.types.Type
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

fun Item.getId(): Identifier = Registry.ITEM.getId(this)
fun Block.getId(): Identifier = Registry.BLOCK.getId(this)
fun BlockEntity.getId(): Identifier = Registry.BLOCK_ENTITY.getId(this.type)!!

fun id(modid: String, id: String) = Identifier(modid, id)

fun blockItemOf(
  block: Block,
  settings: Item.Settings
): BlockItem = BlockItem(block, settings)

fun <T : BlockEntity> blockEntityTypeOf(
  supplier: () -> T,
  vararg blocks: Block,
  type: Type<*>? = null
): BlockEntityType<T> = BlockEntityType.Builder.create(Supplier(supplier), *blocks).build(type)