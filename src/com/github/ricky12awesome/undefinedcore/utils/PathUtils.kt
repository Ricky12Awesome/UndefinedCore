package com.github.ricky12awesome.undefinedcore.utils

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

fun path(first: String, vararg more: String): Path = Paths.get(first, *more)

infix fun Path.moveTo(to: Path): Path = Files.move(this, to)

fun Path.createIfNotExists() = apply {
  if (parent != null) {
    Files.createDirectories(parent)
  }

  if (Files.notExists(this)) {
    when {
      Files.isDirectory(this) -> Files.createDirectory(this)
      Files.isRegularFile(this) -> Files.createFile(this)
    }
  }
}