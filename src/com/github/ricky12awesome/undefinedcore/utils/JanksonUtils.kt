package com.github.ricky12awesome.undefinedcore.utils

import blue.endless.jankson.Jankson
import blue.endless.jankson.JsonElement
import blue.endless.jankson.JsonObject

inline fun <reified T> Jankson.fromJson(json: String): T = fromJson(json, T::class.java)
inline fun <reified T> Jankson.fromJson(element: JsonObject): T = fromJson(element, T::class.java)

private fun MutableMap<String, JsonElement>.addElements(
  path: String,
  element:JsonElement
): Unit = when (element) {
  is JsonObject -> {
    element.forEach { (key, value) ->
      addElements("$path.$key", value)
    }
  }
  else -> this[path] = element
}

fun JsonObject.flattenMap(

): Map<String, JsonElement> = mutableMapOf<String, JsonElement>().apply {
  this@flattenMap.forEach { (k, v) -> addElements(k, v) }
}