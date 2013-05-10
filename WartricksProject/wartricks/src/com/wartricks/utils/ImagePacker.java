
package com.wartricks.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;

public class ImagePacker {
    public static void run() {
        final Settings settings = new Settings();
        settings.filterMin = Texture.TextureFilter.Linear;
        settings.filterMag = Texture.TextureFilter.Linear;
        settings.pot = false;
        TexturePacker2.process(settings, PlatformUtils.getPath("sprites/"),
                PlatformUtils.getPath("textures/"), "pack");
    }
}
