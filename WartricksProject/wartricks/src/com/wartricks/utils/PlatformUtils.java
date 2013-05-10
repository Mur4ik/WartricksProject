
package com.wartricks.utils;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;

public class PlatformUtils {
    public static String getPath(String path) {
        if (Gdx.app.getType() == ApplicationType.Desktop) {
            path = "./assets/" + path;
        }
        return path;
    }
}
