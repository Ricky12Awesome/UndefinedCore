package com.github.ricky12awesome.undefinedcore.config.serializers

import java.nio.file.Path

interface ConfigurationSerializer {
  val data: Map<String, Any?>
  val path: Path

  fun serialize(id: String, value: Any?)
  fun <T> deserialize(id: String): T?

  fun save()
  fun load()
}