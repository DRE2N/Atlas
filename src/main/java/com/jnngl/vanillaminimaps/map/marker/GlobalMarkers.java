package com.jnngl.vanillaminimaps.map.marker;

import com.jnngl.vanillaminimaps.VanillaMinimaps;
import com.jnngl.vanillaminimaps.map.icon.MinimapIcon;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class GlobalMarkers {

    private Set<GlobalMarker> markers = new HashSet<>();
    private final VanillaMinimaps plugin;
    private final File file;

    public GlobalMarkers(VanillaMinimaps plugin, File file) {
        this.plugin = plugin;
        this.file = file;
        load(file);
    }

    public Set<GlobalMarker> getMarkers() {
        return markers;
    }

    public void addMarker(GlobalMarker marker) {
        markers.add(marker);
    }

    public void removeMarker(GlobalMarker marker) {
        markers.remove(marker);
    }

    private void load(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (String key : config.getKeys(false)) {
            ConfigurationSection section = config.getConfigurationSection(key);
            if (section == null) {
                continue;
            }
            File imageFile = new File(plugin.getDataFolder(), "icons/" + section.getString("icon") + ".png");
            BufferedImage image;
            try {
                image = ImageIO.read(imageFile);
            } catch (IOException e) {
                plugin.getLogger().warning("Failed to load image " + imageFile);
                continue;
            }
            MinimapIcon icon = MinimapIcon.fromBufferedImage(key, image);
            boolean visibleByDefault = section.getBoolean("visibleByDefault", true);
            int x = section.getInt("x");
            int z = section.getInt("z");
            addMarker(new GlobalMarker(key, icon, x, z, visibleByDefault));
        }
    }

    public void save() {
        YamlConfiguration config = new YamlConfiguration();
        for (GlobalMarker marker : markers) {
            ConfigurationSection section = config.createSection(marker.id());
            section.set("icon", marker.icon().key() + ".png");
            section.set("visibleByDefault", marker.visibleByDefault());
            section.set("x", marker.x());
            section.set("z", marker.z());
        }
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
