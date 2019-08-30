package com.github.ricky12awesome.undefinedcore.utils

import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.item.FoodComponent
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry
import java.awt.Color
import java.util.*
import kotlin.reflect.KClass


val Int.positive get() = if (this > 0) this else this.opposite
val Int.opposite get() = this - (this * 2)
val Int.negative get() = if (this < 0) this else this.opposite

val <T> Optional<T>.value inline get(): T? = if (isPresent) get() else null

/**
 * For initialing objects so you just don't have just "Object" on a single line,
 * this is purely for readability.
 */
fun initialize(vararg obj: Any) = Unit

fun initializeObjectClasses(nested: Collection<KClass<*>>) {
  nested.forEach {
    it.objectInstance
    initializeObjectClasses(it.nestedClasses)
  }
}

fun langKey(modid: String, group: String, id: String) = "$group.$modid.$id"

fun settings(
  material: Material,
  noCollision: Boolean = false,
  slipperiness: Float = 0.0f,
  strength: Pair<Float, Float> = 0.0f to 0.0f,
  dropLike: Block? = null
): Block.Settings = Block.Settings.of(material).apply {
  if (noCollision) noCollision()
  if (slipperiness > 0) slipperiness(slipperiness)
  if (strength.first > 0 || strength.second > 0) strength(strength.first, strength.second)
  if (dropLike != null) dropsLike(dropLike)
}

fun settings(
  group: ItemGroup = ItemGroup.MISC,
  maxCount: Int = 64,
  maxDamage: Int = 0,
  rarity: Rarity = Rarity.COMMON,
  recipeReminder: Item? = null,
  food: FoodComponent? = null
): Item.Settings = Item.Settings().apply {
  if (maxDamage > 0) maxDamage(maxDamage)
  recipeRemainder(recipeReminder)
  maxCount(maxCount)
  rarity(rarity)
  group(group)
  food(food)
}

fun food(
  alwaysEdible: Boolean = false,
  snack: Boolean = false,
  meat: Boolean = false,
  hunger: Int = 0,
  saturation: Float = 0.0f,
  statusEffects: Map<StatusEffectInstance, Float> = mapOf()
): FoodComponent = FoodComponent.Builder().apply {
  if (alwaysEdible) alwaysEdible()
  if (snack) snack()
  if (meat) meat()

  statusEffects.forEach(::addStatusEffect)

  hunger(hunger)
  saturationModifier(saturation)
}.build()

fun statusWithChance(
  effect: StatusEffect,
  duration: Int = 0,
  amplifier: Int = 0,
  ambient: Boolean = false,
  showParticles: Boolean = true,
  showIcon: Boolean = showParticles,
  chance: Float = 1f
) = StatusEffectInstance(effect, duration, amplifier, ambient, showParticles, showIcon) to chance

fun status(
  effect: StatusEffect,
  duration: Int = 0,
  amplifier: Int = 0,
  ambient: Boolean = false,
  showParticles: Boolean = true,
  showIcon: Boolean = showParticles
) = StatusEffectInstance(effect, duration, amplifier, ambient, showParticles, showIcon)

fun randomColor(
  r: IntRange = 0..255,
  g: IntRange = 0..255,
  b: IntRange = 0..255,
  a: IntRange = 255..255
): Color = Color(r.random(), g.random(), b.random(), a.random())

fun FoodComponent.Builder.addStatusEffect(key: StatusEffectInstance, value: Float) {
  statusEffect(key, value)
}

fun BlockEntity.saveToTag(): CompoundTag = toTag(CompoundTag()).apply {
  remove("x")
  remove("y")
  remove("z")
}

fun ItemStack.toTagWithBiggerCount(tag: CompoundTag = CompoundTag()): CompoundTag {
  val id = Registry.ITEM.getId(this.item)
  tag.putString("id", id.toString())
  tag.putInt("Count", this.count)

  if (this.tag != null) {
    tag.put("tag", this.tag)
  }

  return tag
}

fun String.chunkedFixedCC(size: Int) = chunked(size).toMutableList().apply {
  forEachIndexed { index, s ->
    if (s.endsWith('\u00a7') && index != lastIndex) {
      val next = this[index + 1]

      set(index, s + next[0])
      set(index + 1, next.removeRange(0, 1))
    }
  }
}