import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
plugins {
    id("java")
    id("io.papermc.paperweight.userdev").version("1.7.1")
    id("xyz.jpenilla.run-paper").version("1.0.6")
    id("io.github.goooler.shadow") version "8.1.5" // Use fork until shadow has updated to Java 21
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
}

group = "de.erethon.atlas"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        setName("papermc-repo")
        setUrl("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        setName("sonatype")
        setUrl("https://oss.sonatype.org/content/groups/public/")
    }
    maven {
        setName("erethon")
        setUrl("https://erethon.de/repo")
    }
}

val papyrusVersion = "1.21-R0.1-SNAPSHOT"

dependencies {
    paperweight.devBundle("de.erethon.papyrus", papyrusVersion) { isChanging = true }
    implementation("net.elytrium:serializer:1.1.1")
    implementation("com.jnngl:mapcolor:1.0.1")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    compileOnly("org.projectlombok:lombok:1.18.30")
    implementation("com.j256.ormlite:ormlite-jdbc:6.1")
    implementation("org.xerial:sqlite-jdbc:3.45.0.0")
}

java {
    // Configure the java toolchain. This allows gradle to auto-provision JDK 17 on systems that only have JDK 8 installed for example.
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks {
    runServer {
        if (!project.buildDir.exists()) {
            project.buildDir.mkdir()
        }
        val f = File(project.buildDir, "server.jar");
        uri("https://github.com/DRE2N/Papyrus/releases/download/latest/papyrus-paperclip-$papyrusVersion-reobf.jar").toURL().openStream().use { it.copyTo(f.outputStream()) }
        serverJar(f)
    }
    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything

        // Set the release flag. This configures what version bytecode the compiler will emit, as well as what JDK APIs are usable.
        // See https://openjdk.java.net/jeps/247 for more information.
        options.release.set(21)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    shadowJar {
        relocate("net.elytrium.serializer", "com.jnngl.vanillaminimaps.serializer")
        exclude("org/slf4j/**")
    }
    bukkit {
        load = BukkitPluginDescription.PluginLoadOrder.POSTWORLD
        main = "com.jnngl.vanillaminimaps.VanillaMinimaps"
        apiVersion = "1.21"
        authors = listOf("JNNGL", "Malfrador")
    }
}
