
package com.wartricks.components;

import com.artemis.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public abstract class AbstractScriptAction extends Component {
    public String name;

    public String script;

    public AbstractScriptAction(String scriptName) {
        name = scriptName;
        script = this.reload();
    }

    public String reload() {
        final FileHandle file = Gdx.files.internal("scripts/skills/" + name + ".bsh");
        if (file.exists()) {
            script = file.readString();
        }
        return script;
    }
}
