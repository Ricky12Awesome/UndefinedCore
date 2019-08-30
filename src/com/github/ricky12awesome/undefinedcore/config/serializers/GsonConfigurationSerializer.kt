package com.github.ricky12awesome.undefinedcore.config.serializers

import com.github.ricky12awesome.undefinedcore.utils.createIfNotExists
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.github.ricky12awesome.undefinedcore.utils.flattenMap
import com.github.ricky12awesome.undefinedcore.utils.fromJson
import java.nio.file.Files
import java.nio.file.Path

@Suppress("UNCHECKED_CAST")
class GsonConfigurationSerializer(
  configDirectory: Path,
  name: String
) : ConfigurationSerializer {
  override val data: MutableMap<String, Any?> = mutableMapOf()
  override val path: Path = configDirectory.resolve("$name.json")

  private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

  override fun serialize(id: String, value: Any?) {
    data[id] = value
  }

  override fun <T> deserialize(id: String): T? = data[id] as? T

  private fun JsonObject.addData(key: String, data: Any?) {
    val split = key.split(".")
    val id = split.lastOrNull() ?: return
    val json = jsonObjectOf(split)

    when (data) {
      is Number -> json.addProperty(id, data)
      is String -> json.addProperty(id, data)
      is Boolean -> json.addProperty(id, data)
      is Char -> json.addProperty(id, data)
      else -> json.add(id, gson.toJsonTree(data))
    }
  }

  private tailrec fun JsonObject.jsonObjectOf(key: List<String>, index: Int = 0): JsonObject {
    if (index == key.lastIndex) return this

    val s = key[index]
    var json = getAsJsonObject(s)

    if (json == null) {
      json = JsonObject()
      add(s, json)
    }

    return json.jsonObjectOf(key, index + 1)
  }

  override fun save() {
    val json = JsonObject()

    data.forEach { (key, data) ->
      json.addData(key, data)
    }

    Files.newBufferedWriter(path.createIfNotExists()).use {
      gson.toJson(json, it)
    }
  }

  override fun load() {
    if (Files.notExists(path)) save()

    Files.newBufferedReader(path).use {
      data += gson.fromJson<JsonObject>(it).flattenMap().mapValues { (_, value) ->
        gson.fromJson<Any?>(value)
      }
    }
  }
}