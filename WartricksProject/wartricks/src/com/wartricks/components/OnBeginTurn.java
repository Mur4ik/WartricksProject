
package com.wartricks.components;

import com.artemis.Entity;
import com.artemis.World;
import com.wartricks.logic.GameMap;
import com.wartricks.utils.LoadScript;

public class OnBeginTurn extends AbstractScriptAction {
    public OnBeginTurn(String scriptName, String methodName) {
        super(scriptName, methodName);
    }

    public void execute(GameMap gameMap, World gameWorld, Entity action) {
        final LoadScript script = new LoadScript(path);
        script.runUnboundScriptFunction(method, gameMap, gameWorld, action);
    }
}
