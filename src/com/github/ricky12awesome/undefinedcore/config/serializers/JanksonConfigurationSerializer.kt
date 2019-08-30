package me.ricky.fabric.config.serializers

import blue.endless.jankson.*
import com.github.ricky12awesome.undefinedcore.config.serializers.ConfigurationSerializer
import com.github.ricky12awesome.undefinedcore.utils.createIfNotExists
import com.github.ricky12awesome.undefinedcore.utils.flattenMap
import java.nio.file.Path

@Suppress("UNCHECKED_CAST")
class JanksonConfigurationSerializer(
  configDirectory: Path,
  name: String
) : ConfigurationSerializer {
  override val data: MutableMap<String, Any?> = mutableMapOf()
  override val path: Path = configDirectory.resolve("$name.json5")

  val jankson: Jankson = Jankson.builder().build()

  override fun serialize(id: String, value: Any?) {
    data[id] = value
  }

  override fun <T> deserialize(id: String): T? = data[id] as? T

  private fun JsonObject.addData(key: String, data: Any?) {
    val split = key.split(".")
    val id = split.lastOrNull() ?: return
    val json = jsonObjectOf(split)

    when (data) {
      is Number -> json[id] = JsonPrimitive(data)
      is String -> json[id] = JsonPrimitive(data)
      is Boolean -> json[id] = JsonPrimitive(data)
      is Char -> json[id] = JsonPrimitive(data)
      else -> json[id] = jankson.toJson(data)
    }
  }

  private tailrec fun JsonObject.jsonObjectOf(key: List<String>, index: Int = 0): JsonObject {
    if (index == key.lastIndex) return this

    val s = key[index]
    var json = this[s] as? JsonObject

    if (json == null) {
      json = JsonObject()
      this[s] = json
    }

    return json.jsonObjectOf(key, index + 1)
  }

  override fun save() {
    val json = JsonObject()

    data.forEach { (key, data) ->
      json.addData(key, data)
    }

    path.toFile().writeText(json.toJson(true, true))
  }

  override fun load() {
    val json = jankson.load(path.createIfNotExists().toFile())

    data += json.mapValues()
  }

  fun JsonArray.map(): List<Any?> = map {
    when (it) {
      is JsonPrimitive -> it.value
      is JsonArray -> it.map()
      is JsonObject -> it.mapValues()
      else -> null
    }
  }

  fun JsonObject.mapValues(): Map<String, Any?> = flattenMap().mapValues { (_, value) ->
    when (value) {
      is JsonPrimitive -> value.value
      is JsonArray -> value.map()
      else -> null
    }
  }


}