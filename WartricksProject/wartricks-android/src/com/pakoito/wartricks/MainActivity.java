
package com.pakoito.wartricks;

import android.os.Bundle;
import android.view.Display;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.wartricks.lifecycle.BoardGame;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
        final Display display = this.getWindowManager().getDefaultDisplay();
        this.initialize(new BoardGame(display.getWidth(), display.getHeight()), cfg);
    }
}
