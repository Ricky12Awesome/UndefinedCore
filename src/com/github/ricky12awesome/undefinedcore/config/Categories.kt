package me.ricky.fabric.config

import com.github.ricky12awesome.undefinedcore.config.builders.subCategory
import com.github.ricky12awesome.undefinedcore.config.delegation.FieldBuilderProvider
import com.github.ricky12awesome.undefinedcore.config.delegation.fieldBuilderProvider
import java.util.function.Supplier

interface Category {
  val translationKey get() = "${root.translationPrefix}.$id"

  val root: Configuration
  val entries: MutableList<FieldBuilderProvider>
  val id: String

  fun <R> load(id: String): R? = root.load("${this.id}.$id")
  fun save(id: String, data: Any?) = root.save("${this.id}.$id", data)
}

open class ConfigurationCategory protected constructor(
  final override val root: Configuration,
  final override val id: String
) : Category {
  final override val entries: MutableList<FieldBuilderProvider> = mutableListOf()

  init {
    root.categories += Supplier { this }
  }
}

open class SubCategory protected constructor(
  parent: Category,
  id: String
) : Category {
  final override val root: Configuration = parent.root
  final override val id: String = "${parent.id}.$id"
  final override val entries: MutableList<FieldBuilderProvider> = mutableListOf()

  init {
    parent.entries += fieldBuilderProvider {
      val copy = copy(category = this@SubCategory)

      subCategory(entries.map {
        it(copy).build()
      })
    }
  }
}