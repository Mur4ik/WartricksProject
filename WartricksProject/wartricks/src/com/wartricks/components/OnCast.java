
package com.wartricks.components;

import com.artemis.Component;
import com.wartricks.custom.WartricksInterpreter;

public class OnCast extends Component {
    public final WartricksInterpreter interpreter;

    public String name;

    public OnCast(String scriptName) {
        name = scriptName;
        interpreter = new WartricksInterpreter(name);
    }

    public void reload() {
        interpreter.reload();
    }
}
