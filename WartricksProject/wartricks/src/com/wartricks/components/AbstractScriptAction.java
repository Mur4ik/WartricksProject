
package com.wartricks.components;

import bsh.EvalError;
import bsh.Interpreter;

import com.artemis.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public abstract class AbstractScriptAction extends Component {
    public String name;

    public String script;

    private Interpreter interpreter;

    public AbstractScriptAction(String scriptName) {
        name = scriptName;
        interpreter = new Interpreter();
        this.reload();
    }

    public void reload() {
        final FileHandle file = Gdx.files.internal("scripts/skills/" + name + ".bsh");
        if (file.exists()) {
            script = file.readString();
        }
        try {
            interpreter.eval(script);
        } catch (final EvalError e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
