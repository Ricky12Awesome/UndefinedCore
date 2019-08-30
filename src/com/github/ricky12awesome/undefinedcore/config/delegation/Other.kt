package com.github.ricky12awesome.undefinedcore.config.delegation

import me.ricky.fabric.config.Category
import com.github.ricky12awesome.undefinedcore.config.builders.ConfigurationEntryBuilder
import me.shedaniel.clothconfig2.impl.builders.FieldBuilder
import java.util.function.Supplier
import kotlin.reflect.KProperty

typealias FieldBuilderProvider = ConfigurationEntryBuilder.() -> FieldBuilder<*, *>

fun fieldBuilderSupplier(supplier: () -> FieldBuilder<*, *>) = Supplier(supplier)
fun fieldBuilderProvider(provider: FieldBuilderProvider) = provider

class CategoryPropertyDelegate<R>(
  val key: String,
  val default: R
) {
  operator fun getValue(category: Category, property: KProperty<*>): R {
    return category.root.load<R>(key) ?: default
  }
}