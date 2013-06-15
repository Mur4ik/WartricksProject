
package com.wartricks.components;

import com.artemis.Component;

public abstract class AbstractScriptAction extends Component {
    public String path;

    public String method;

    public AbstractScriptAction(String scriptName, String methodName) {
        path = scriptName;
        method = methodName;
    }
}
