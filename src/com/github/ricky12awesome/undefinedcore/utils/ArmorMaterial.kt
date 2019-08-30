package com.github.ricky12awesome.undefinedcore.utils

import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.ArmorMaterial
import net.minecraft.item.ArmorMaterials
import net.minecraft.recipe.Ingredient
import net.minecraft.sound.SoundEvent

data class ArmorValues(
  val helmet: Int,
  val chestplate: Int,
  val leggings: Int,
  val boots: Int
) {
  val values get() = arrayOf(helmet, chestplate, leggings, boots)
}

fun baseDurabilityFrom(material: ArmorMaterial) = when (material) {
  is CustomArmorMaterial -> material.baseDurability
  else -> ArmorValues(13, 15, 16, 11)
}

fun armorValuesFrom(material: ArmorMaterial): ArmorValues = when (material) {
  is CustomArmorMaterial -> material.armorValues
  is ArmorMaterials -> when (material) {
    ArmorMaterials.LEATHER -> ArmorValues(1, 2, 3, 1)
    ArmorMaterials.CHAIN -> ArmorValues(1, 4, 5, 2)
    ArmorMaterials.IRON -> ArmorValues(2, 5, 6, 2)
    ArmorMaterials.GOLD -> ArmorValues(1, 3, 5, 2)
    ArmorMaterials.DIAMOND -> ArmorValues(3, 6, 8, 3)
    ArmorMaterials.TURTLE -> ArmorValues(2, 5, 6, 2)
  }
  else -> ArmorValues(1, 2, 3, 1)
}

fun baseDurabilityMultiplierFrom(material: ArmorMaterial): Int = when (material) {
  is CustomArmorMaterial -> material.durabilityMultiplier
  is ArmorMaterials -> when (material) {
    ArmorMaterials.LEATHER -> 5
    ArmorMaterials.CHAIN -> 15
    ArmorMaterials.IRON -> 15
    ArmorMaterials.GOLD -> 7
    ArmorMaterials.DIAMOND -> 33
    ArmorMaterials.TURTLE -> 25
  }
  else -> 5
}

fun armorMaterial(
  from: ArmorMaterial = ArmorMaterials.LEATHER,
  name: String = from.name,
  repairIngredient: Ingredient = from.repairIngredient,
  toughness: Float = from.toughness,
  equipSound: SoundEvent = from.equipSound,
  enchantability: Int = from.enchantability,
  armorValues: ArmorValues = armorValuesFrom(from),
  baseDurability: ArmorValues = baseDurabilityFrom(from),
  durabilityMultiplier: Int = baseDurabilityMultiplierFrom(from)
) = CustomArmorMaterial(
  name = name,
  repairIngredient = repairIngredient,
  toughness = toughness,
  equipSound = equipSound,
  enchantability = enchantability,
  armorValues = armorValues,
  baseDurability = baseDurability,
  durabilityMultiplier = durabilityMultiplier
)

class CustomArmorMaterial(
  private val name: String,
  private val repairIngredient: Ingredient,
  private val toughness: Float,
  private val equipSound: SoundEvent,
  private val enchantability: Int,
  val armorValues: ArmorValues,
  val baseDurability: ArmorValues,
  val durabilityMultiplier: Int
) : ArmorMaterial {
  override fun getName(): String = name
  override fun getRepairIngredient(): Ingredient = repairIngredient
  override fun getToughness(): Float = toughness
  override fun getEquipSound(): SoundEvent = equipSound
  override fun getEnchantability(): Int = enchantability
  override fun getDurability(slot: EquipmentSlot): Int = baseDurability.values[slot.entitySlotId] * durabilityMultiplier
  override fun getProtectionAmount(slot: EquipmentSlot): Int = armorValues.values[slot.entitySlotId]
}