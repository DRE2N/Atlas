package com.jnngl.vanillaminimaps.map.marker;

import com.jnngl.vanillaminimaps.map.icon.MinimapIcon;

public record GlobalMarker(String id, MinimapIcon icon, int x, int z, boolean visibleByDefault) {
}
