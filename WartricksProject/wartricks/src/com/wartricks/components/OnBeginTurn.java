
package com.wartricks.components;

public class OnBeginTurn extends AbstractScriptAction {
    private boolean active;

    private int casterId;

    public OnBeginTurn(String scriptName, String methodName) {
        super(scriptName, methodName);
        active = false;
        casterId = -1;
    }

    public boolean activate(int casterId) {
        this.casterId = casterId;
        active = true;
        return true;
    }

    public boolean deactivate() {
        casterId = -1;
        active = false;
        return true;
    }

    public boolean isActive() {
        return active;
    }
}
