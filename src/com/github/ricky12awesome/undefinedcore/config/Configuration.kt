package me.ricky.fabric.config

import com.github.ricky12awesome.undefinedcore.config.builders.ConfigurationEntryBuilder
import com.github.ricky12awesome.undefinedcore.config.serializers.ConfigurationSerializer
import com.github.ricky12awesome.undefinedcore.config.serializers.GsonConfigurationSerializer
import me.shedaniel.clothconfig2.api.ConfigBuilder
import me.shedaniel.clothconfig2.api.ConfigCategory
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.gui.screen.Screen
import java.nio.file.Path
import java.util.function.Supplier
import kotlin.reflect.KClass

open class Configuration(
  name: String,
  group: String = "config",
  serializer: (Path, String) -> ConfigurationSerializer = ::GsonConfigurationSerializer
) {
  private var isInitialized: Boolean = false

  val translationPrefix = "$group.$name"
  val categories: MutableList<Supplier<ConfigurationCategory>> = mutableListOf()
  val serializer: ConfigurationSerializer = serializer(FabricLoader.getInstance().configDirectory.toPath(), name)

  fun loadScreen(parent: Screen? = null): Screen {
    if (!isInitialized) throw IllegalAccessError("${javaClass.canonicalName} must be initialized")
    val builder: ConfigBuilder = ConfigBuilder.create()

    if (parent != null) builder.parentScreen = parent

    load()
    categories.forEach { supplier ->
      val supplied = supplier.get()
      val category: ConfigCategory = builder.getOrCreateCategory("${supplied.translationKey}.name")
      val entryBuilder = ConfigurationEntryBuilder(supplied, serializer)

      builder.setSavingRunnable(::save)

      supplied.entries.forEach {
        val value = it(entryBuilder)

        category.addEntry(value.build())
      }
    }

    return builder.build()
  }
  private fun loadNestedClasses(clazz: Collection<KClass<*>>) {
    clazz.forEach {
      it.objectInstance
      loadNestedClasses(it.nestedClasses)
    }
  }

  fun initialize() {
    loadNestedClasses(this::class.nestedClasses)

    isInitialized = true
  }


  fun save(id: String, value: Any?) = serializer.serialize(id, value)
  fun save() = serializer.save()

  fun <R> load(id: String): R? = serializer.deserialize(id)
  fun load() = serializer.load()
}
