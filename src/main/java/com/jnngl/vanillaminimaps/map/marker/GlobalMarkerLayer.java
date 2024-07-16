package com.jnngl.vanillaminimaps.map.marker;

import com.jnngl.vanillaminimaps.map.MinimapLayer;
import com.jnngl.vanillaminimaps.map.SecondaryMinimapLayer;
import com.jnngl.vanillaminimaps.map.renderer.SecondaryMinimapLayerRenderer;
import org.bukkit.World;

public class GlobalMarkerLayer extends SecondaryMinimapLayer {

    public GlobalMarkerLayer(MinimapLayer baseLayer, SecondaryMinimapLayerRenderer renderer, boolean trackLocation, boolean keepOnEdge, World world, int positionX, int positionZ, float depth) {
        super(baseLayer, renderer, trackLocation, keepOnEdge, world, positionX, positionZ, depth);
    }
}
