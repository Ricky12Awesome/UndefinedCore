pluginManagement {
  repositories {
    jcenter()
    mavenLocal()
    maven("https://maven.fabricmc.net/")
    maven("https://jitpack.io")
    gradlePluginPortal()
  }

  resolutionStrategy {
    eachPlugin {
      if (requested.id.id == "parseLangFile") {
        val versions = requested.version!!.split(":")
        val moduleVersion = versions.getOrElse(1) { "-SNAPSHOT" }
        useModule("com.github.Ricky12Awesome:ParseLangFile:$moduleVersion")
        useVersion(versions[0])
      }
    }
  }
}