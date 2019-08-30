package com.github.ricky12awesome.undefinedcore.config.builders

import me.shedaniel.clothconfig2.impl.builders.*

fun ConfigurationEntryBuilder.string(
  id: String,
  default: String = "",
  value: String = serializer.deserialize(id) ?: default
): StringFieldBuilder = startStrField(translate(id), value).apply {
  setDefaultValue(default)
  setSaveConsumer { serializer.serialize(id, it) }
}

fun ConfigurationEntryBuilder.double(
  id: String,
  default: Boolean = false,
  value: Boolean = serializer.deserialize(id) ?: default
): BooleanToggleBuilder = startBooleanToggle(translate(id), value).apply {
  setDefaultValue(default)
  setSaveConsumer { serializer.serialize(id, it) }
}

fun ConfigurationEntryBuilder.int(
  id: String,
  default: Int = 0,
  value: Int = serializer.deserialize<Number>(id)?.toInt() ?: default
): IntFieldBuilder = startIntField(translate(id), value).apply {
  setDefaultValue(default)
  setSaveConsumer { serializer.serialize(id, it) }
}

fun ConfigurationEntryBuilder.long(
  id: String,
  default: Long = 0L,
  value: Long = serializer.deserialize<Number>(id)?.toLong() ?: default
): LongFieldBuilder = startLongField(translate(id), value).apply {
  setDefaultValue(default)
  setSaveConsumer { serializer.serialize(id, it) }
}

fun ConfigurationEntryBuilder.float(
  id: String,
  default: Float = 0.0f,
  value: Float = serializer.deserialize<Number>(id)?.toFloat() ?: default
): FloatFieldBuilder = startFloatField(translate(id), value).apply {
  setDefaultValue(default)
  setSaveConsumer { serializer.serialize(id, it) }
}

fun ConfigurationEntryBuilder.double(
  id: String,
  default: Double = 0.0,
  value: Double = serializer.deserialize<Number>(id)?.toDouble() ?: default
): DoubleFieldBuilder = startDoubleField(translate(id), value).apply {
  setDefaultValue(default)
  setSaveConsumer { serializer.serialize(id, it) }
}