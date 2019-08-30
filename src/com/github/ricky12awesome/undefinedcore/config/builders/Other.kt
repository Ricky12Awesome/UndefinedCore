package com.github.ricky12awesome.undefinedcore.config.builders

import me.shedaniel.clothconfig2.api.AbstractConfigListEntry
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder

fun ConfigurationEntryBuilder.subCategory(
  entries: List<AbstractConfigListEntry<*>> = listOf()
): SubCategoryBuilder = startSubCategory(translate("name"), entries)