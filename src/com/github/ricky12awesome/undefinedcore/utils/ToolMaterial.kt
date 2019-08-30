package com.github.ricky12awesome.undefinedcore.utils

import net.minecraft.item.ToolMaterial
import net.minecraft.item.ToolMaterials
import net.minecraft.recipe.Ingredient

class CustomToolMaterial(
  private val repairIngredient: Ingredient,
  private val attackDamage: Float,
  private val miningSpeed: Float,
  private val miningLevel: Int,
  private val durability: Int,
  private val enchantability: Int
) : ToolMaterial {
  override fun getAttackDamage(): Float = attackDamage
  override fun getMiningSpeed(): Float = miningSpeed
  override fun getMiningLevel(): Int = miningLevel
  override fun getDurability(): Int = durability
  override fun getEnchantability(): Int = enchantability
  override fun getRepairIngredient(): Ingredient = repairIngredient
}

fun toolMaterial(
  from: ToolMaterial = ToolMaterials.WOOD,
  repairIngredient: Ingredient = from.repairIngredient,
  attackDamage: Float = from.attackDamage,
  miningSpeed: Float = from.miningSpeed,
  miningLevel: Int = from.miningLevel,
  durability: Int = from.durability,
  enchantability: Int = from.enchantability
) = CustomToolMaterial(repairIngredient, attackDamage, miningSpeed, miningLevel, durability, enchantability)