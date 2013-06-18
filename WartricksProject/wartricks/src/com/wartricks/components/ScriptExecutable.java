
package com.wartricks.components;

import com.artemis.Component;
import com.wartricks.custom.WartricksInterpreter;

public class ScriptExecutable extends Component {
    public WartricksInterpreter interpreter;

    public String name;

    public ScriptExecutable(String scriptName) {
        name = scriptName;
        interpreter = new WartricksInterpreter(name);
    }

    public ScriptExecutable(String scriptName, WartricksInterpreter interpreter) {
        name = scriptName;
        this.interpreter = interpreter;
    }

    public void reload() {
        interpreter.reload();
    }
}
