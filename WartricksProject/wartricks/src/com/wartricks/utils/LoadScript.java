
package com.wartricks.utils;

import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class LoadScript {
    public static LuaState luaState;

    private final String fileName;

    /**
     * Constructor
     * 
     * @param fileName File name with Lua script.
     */
    public LoadScript(String fileName) {
        if (null == luaState) {
            luaState = LuaStateFactory.newLuaState();
        }
        luaState.openLibs();
        fileName = "scripts/" + fileName;
        // This next part we do because Android cant use LdoFile instead
        // we load the lua file using gdx and convert it into a string and load it
        final FileHandle handle = Gdx.files.internal(PlatformUtils.getPath(fileName));
        final String file = handle.readString();
        luaState.LdoString(file);
        this.fileName = fileName;
    }

    public void update() {
        final FileHandle handle = Gdx.files.internal(fileName);
        final String file = handle.readString();
        luaState.LdoString(file);
    }

    /**
     * Ends the use of Lua environment.
     */
    public void closeScript() {
        luaState.close();
    }

    /**
     * Call a Lua function inside the Lua script to insert data into a Java object passed as
     * parameter
     * 
     * @param functionName Name of Lua function.
     * @param obj A Java object.
     */
    public void runScriptFunction(final String functionName, final Object obj) {
        luaState.getGlobal(functionName);
        luaState.pushJavaObject(obj);
        luaState.call(1, 0);
    }

    // Same thing as above but with another object
    public void runScriptFunction(final String functionName, final Object obj, final Object obj2) {
        luaState.getGlobal(functionName);
        luaState.pushJavaObject(obj);
        luaState.pushJavaObject(obj2);
        luaState.call(2, 0);
    }

    // Same thing as above but with any number of objects
    public void runUnboundScriptFunction(final String functionName, final Object... obj) {
        luaState.getGlobal(functionName);
        for (final Object parameter : obj) {
            luaState.pushJavaObject(parameter);
        }
        luaState.call(obj.length, 0);
    }

    public String getGlobalString(final String globalName) {
        luaState.getGlobal(globalName);
        return luaState.toString(luaState.getTop());
    }
}
