package com.github.ricky12awesome.undefinedcore.config.delegation

import com.github.ricky12awesome.undefinedcore.config.builders.int
import com.github.ricky12awesome.undefinedcore.config.builders.string
import me.ricky.fabric.config.Category

fun Category.string(
  name: String,
  default: String = ""
) = CategoryPropertyDelegate("$id.$name", default).also {
  entries += fieldBuilderProvider { this.string(name, default) }
}

fun Category.int(
  name: String,
  default: Int = 0
) = CategoryPropertyDelegate("$id.$name", default).also {
  entries += fieldBuilderProvider { this.int(name, default) }
}