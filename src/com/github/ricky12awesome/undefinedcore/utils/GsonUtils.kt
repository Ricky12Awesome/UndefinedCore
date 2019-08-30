package com.github.ricky12awesome.undefinedcore.utils

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.io.Reader

inline fun <reified T> Gson.fromJson(json: String): T = fromJson(json, T::class.java)
inline fun <reified T> Gson.fromJson(reader: Reader): T = fromJson(reader, T::class.java)
inline fun <reified T> Gson.fromJson(element: JsonElement): T = fromJson(element, T::class.java)

private fun MutableMap<String, JsonElement>.addElements(
  path: String,
  element: JsonElement
): Unit = when (element) {
  is JsonObject -> {
    element.entrySet().forEach { (key, value) ->
      addElements("$path.$key", value)
    }
  }
  else -> this[path] = element
}

fun JsonObject.flattenMap(): Map<String, JsonElement> = mutableMapOf<String, JsonElement>().apply {
  entrySet().forEach { (k, v) -> addElements(k, v) }
}
