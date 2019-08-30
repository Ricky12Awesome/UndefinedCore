package com.github.ricky12awesome.undefinedcore.utils

import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.util.function.Supplier

fun id(namespace: String, path: String) = Identifier(namespace, path)
fun minecraft(path: String) = Identifier("minecraft", path)
fun id(from: Identifier, append: String) = Identifier(from.namespace, from.path + append)

val Block.id: Identifier get() = Registry.BLOCK.getId(this)
val Item.id: Identifier get() = Registry.ITEM.getId(this)

fun <T : BlockEntity> blockEntityOf(
  supplier: () -> T,
  vararg blocks: Block
): BlockEntityType<T> = BlockEntityType.Builder.create(Supplier(supplier), *blocks).build(null)
