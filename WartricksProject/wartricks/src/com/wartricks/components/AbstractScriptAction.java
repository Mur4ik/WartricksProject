
package com.wartricks.components;

import com.artemis.Component;
import com.wartricks.utils.LoadScript;

public abstract class AbstractScriptAction extends Component {
    public String path;

    public String method;

    public AbstractScriptAction(String scriptName, String methodName) {
        path = scriptName;
        method = methodName;
    }

    public void execute(Object parameters) {
        final LoadScript script = new LoadScript(path);
        script.runScriptFunction(method, parameters);
    }
}
