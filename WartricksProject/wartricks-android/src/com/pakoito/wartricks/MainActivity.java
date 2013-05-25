
package com.pakoito.wartricks;

import android.os.Bundle;
import android.view.Display;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.wartricks.lifecycle.WartricksGame;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
        final Display display = this.getWindowManager().getDefaultDisplay();
        this.initialize(new WartricksGame(display.getWidth(), display.getHeight()), cfg);
    }
}
