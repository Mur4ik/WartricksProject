
package com.wartricks.custom;

import bsh.EvalError;
import bsh.Interpreter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class WartricksInterpreter extends Interpreter {
    private static final String INIT_SCRIPT = "scripts/init.bsh";

    public String scriptName;

    private static final long serialVersionUID = 532787351354649447L;

    public WartricksInterpreter() {
        scriptName = "";
        this.reload();
    }

    public WartricksInterpreter(String scriptName) {
        this.scriptName = scriptName;
        this.reload();
    }

    public void reload() {
        try {
            FileHandle file = Gdx.files.internal(INIT_SCRIPT);
            if (file.exists()) {
                this.eval(file.readString());
            }
            if (!scriptName.isEmpty()) {
                file = Gdx.files.internal("scripts/skills/" + scriptName + ".bsh");
                if (file.exists()) {
                    this.eval(file.readString());
                }
            }
        } catch (final EvalError e) {
            e.printStackTrace();
        }
    }
}
