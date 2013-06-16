
package com.wartricks.components;

import com.artemis.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public abstract class AbstractScriptAction extends Component {
    public String path;

    public String script;

    public AbstractScriptAction(String scriptName) {
        path = scriptName;
        final FileHandle file = Gdx.files.internal("scripts/skills/" + scriptName + ".bsh");
        if (file.exists()) {
            script = file.readString();
        }
    }
}
