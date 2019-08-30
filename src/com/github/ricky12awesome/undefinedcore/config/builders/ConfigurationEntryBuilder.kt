package com.github.ricky12awesome.undefinedcore.config.builders

import me.ricky.fabric.config.Category
import com.github.ricky12awesome.undefinedcore.config.serializers.ConfigurationSerializer
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder

data class ConfigurationEntryBuilder(
  val category: Category,
  val serializer: ConfigurationSerializer
) : ConfigEntryBuilder by ConfigEntryBuilder.create() {
  fun translate(id: String) = "${category.translationKey}.$id"
}