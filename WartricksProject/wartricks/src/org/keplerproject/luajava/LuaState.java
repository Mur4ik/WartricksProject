/*
 * $Id: LuaState.java,v 1.9 2006/12/22 14:06:40 thiago Exp $
 * Copyright (C) 2003-2007 Kepler Project.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.keplerproject.luajava;

/**
 * LuaState if the main class of LuaJava for the Java developer. LuaState is a mapping of most of
 * Lua's C API functions. LuaState also provides many other functions that will be used to
 * manipulate objects between Lua and Java.
 * 
 * @author Thiago Ponte
 */
public class LuaState {
    private final static String LUAJAVA_LIB = "luajava";

    final public static Integer LUA_GLOBALSINDEX = new Integer(-10002);

    final public static Integer LUA_REGISTRYINDEX = new Integer(-10000);

    final public static Integer LUA_TNONE = new Integer(-1);

    final public static Integer LUA_TNIL = new Integer(0);

    final public static Integer LUA_TBOOLEAN = new Integer(1);

    final public static Integer LUA_TLIGHTUSERDATA = new Integer(2);

    final public static Integer LUA_TNUMBER = new Integer(3);

    final public static Integer LUA_TSTRING = new Integer(4);

    final public static Integer LUA_TTABLE = new Integer(5);

    final public static Integer LUA_TFUNCTION = new Integer(6);

    final public static Integer LUA_TUSERDATA = new Integer(7);

    final public static Integer LUA_TTHREAD = new Integer(8);

    /**
     * Specifies that an unspecified (multiple) number of return arguments will be returned by a
     * call.
     */
    final public static Integer LUA_MULTRET = new Integer(-1);

    /*
     * error codes for `lua_load' and `lua_pcall'
     */
    /**
     * a runtime error.
     */
    final public static Integer LUA_ERRRUN = new Integer(1);

    /**
   * 
   */
    final public static Integer LUA_YIELD = new Integer(2);

    /**
     * syntax error during pre-compilation.
     */
    final public static Integer LUA_ERRSYNTAX = new Integer(3);

    /**
     * memory allocation error. For such errors, Lua does not call the error handler function.
     */
    final public static Integer LUA_ERRMEM = new Integer(4);

    /**
     * error while running the error handler function.
     */
    final public static Integer LUA_ERRERR = new Integer(5);
    /**
     * Opens the library containing the luajava API
     */
    static {
        System.loadLibrary(LUAJAVA_LIB);
    }

    private CPtr luaState;

    private final int stateId;

    /**
     * Constructor to instance a new LuaState and initialize it with LuaJava's functions
     * 
     * @param stateId
     */
    protected LuaState(final int stateId) {
        luaState = this._open();
        this.luajava_open(luaState, stateId);
        this.stateId = stateId;
    }

    /**
     * Receives a existing state and initializes it
     * 
     * @param luaState
     */
    protected LuaState(final CPtr luaState) {
        this.luaState = luaState;
        stateId = LuaStateFactory.insertLuaState(this);
        this.luajava_open(luaState, stateId);
    }

    /**
     * Closes state and removes the object from the LuaStateFactory
     */
    public synchronized void close() {
        LuaStateFactory.removeLuaState(stateId);
        this._close(luaState);
        luaState = null;
    }

    /**
     * Returns <code>true</code> if state is closed.
     */
    public synchronized boolean isClosed() {
        return luaState == null;
    }

    /**
     * Return the long representing the LuaState pointer
     * 
     * @return long
     */
    public long getCPtrPeer() {
        return (luaState != null) ? luaState.getPeer() : 0;
    }

    /********************* Lua Native Interface *************************/
    private synchronized native CPtr _open();

    private synchronized native void _close(CPtr ptr);

    private synchronized native CPtr _newthread(CPtr ptr);

    // Stack manipulation
    private synchronized native int _getTop(CPtr ptr);

    private synchronized native void _setTop(CPtr ptr, int idx);

    private synchronized native void _pushValue(CPtr ptr, int idx);

    private synchronized native void _remove(CPtr ptr, int idx);

    private synchronized native void _insert(CPtr ptr, int idx);

    private synchronized native void _replace(CPtr ptr, int idx);

    private synchronized native int _checkStack(CPtr ptr, int sz);

    private synchronized native void _xmove(CPtr from, CPtr to, int n);

    // Access functions
    private synchronized native int _isNumber(CPtr ptr, int idx);

    private synchronized native int _isString(CPtr ptr, int idx);

    private synchronized native int _isCFunction(CPtr ptr, int idx);

    private synchronized native int _isUserdata(CPtr ptr, int idx);

    private synchronized native int _type(CPtr ptr, int idx);

    private synchronized native String _typeName(CPtr ptr, int tp);

    private synchronized native int _equal(CPtr ptr, int idx1, int idx2);

    private synchronized native int _rawequal(CPtr ptr, int idx1, int idx2);

    private synchronized native int _lessthan(CPtr ptr, int idx1, int idx2);

    private synchronized native double _toNumber(CPtr ptr, int idx);

    private synchronized native int _toInteger(CPtr ptr, int idx);

    private synchronized native int _toBoolean(CPtr ptr, int idx);

    private synchronized native String _toString(CPtr ptr, int idx);

    private synchronized native int _objlen(CPtr ptr, int idx);

    private synchronized native CPtr _toThread(CPtr ptr, int idx);

    // Push functions
    private synchronized native void _pushNil(CPtr ptr);

    private synchronized native void _pushNumber(CPtr ptr, double number);

    private synchronized native void _pushInteger(CPtr ptr, int integer);

    private synchronized native void _pushString(CPtr ptr, String str);

    private synchronized native void _pushString(CPtr ptr, byte[] bytes, int n);

    private synchronized native void _pushBoolean(CPtr ptr, int bool);

    // Get functions
    private synchronized native void _getTable(CPtr ptr, int idx);

    private synchronized native void _getField(CPtr ptr, int idx, String k);

    private synchronized native void _rawGet(CPtr ptr, int idx);

    private synchronized native void _rawGetI(CPtr ptr, int idx, int n);

    private synchronized native void _createTable(CPtr ptr, int narr, int nrec);

    private synchronized native int _getMetaTable(CPtr ptr, int idx);

    private synchronized native void _getFEnv(CPtr ptr, int idx);

    // Set functions
    private synchronized native void _setTable(CPtr ptr, int idx);

    private synchronized native void _setField(CPtr ptr, int idx, String k);

    private synchronized native void _rawSet(CPtr ptr, int idx);

    private synchronized native void _rawSetI(CPtr ptr, int idx, int n);

    private synchronized native int _setMetaTable(CPtr ptr, int idx);

    private synchronized native int _setFEnv(CPtr ptr, int idx);

    private synchronized native void _call(CPtr ptr, int nArgs, int nResults);

    private synchronized native int _pcall(CPtr ptr, int nArgs, int Results, int errFunc);

    // Coroutine Functions
    private synchronized native int _yield(CPtr ptr, int nResults);

    private synchronized native int _resume(CPtr ptr, int nargs);

    private synchronized native int _status(CPtr ptr);

    // Gargabe Collection Functions
    final public static Integer LUA_GCSTOP = new Integer(0);

    final public static Integer LUA_GCRESTART = new Integer(1);

    final public static Integer LUA_GCCOLLECT = new Integer(2);

    final public static Integer LUA_GCCOUNT = new Integer(3);

    final public static Integer LUA_GCCOUNTB = new Integer(4);

    final public static Integer LUA_GCSTEP = new Integer(5);

    final public static Integer LUA_GCSETPAUSE = new Integer(6);

    final public static Integer LUA_GCSETSTEPMUL = new Integer(7);

    private synchronized native int _gc(CPtr ptr, int what, int data);

    // Miscellaneous Functions
    private synchronized native int _error(CPtr ptr);

    private synchronized native int _next(CPtr ptr, int idx);

    private synchronized native void _concat(CPtr ptr, int n);

    // Some macros
    private synchronized native void _pop(CPtr ptr, int n);

    private synchronized native void _newTable(CPtr ptr);

    private synchronized native int _strlen(CPtr ptr, int idx);

    private synchronized native int _isFunction(CPtr ptr, int idx);

    private synchronized native int _isTable(CPtr ptr, int idx);

    private synchronized native int _isNil(CPtr ptr, int idx);

    private synchronized native int _isBoolean(CPtr ptr, int idx);

    private synchronized native int _isThread(CPtr ptr, int idx);

    private synchronized native int _isNone(CPtr ptr, int idx);

    private synchronized native int _isNoneOrNil(CPtr ptr, int idx);

    private synchronized native void _setGlobal(CPtr ptr, String name);

    private synchronized native void _getGlobal(CPtr ptr, String name);

    private synchronized native int _getGcCount(CPtr ptr);

    // LuaLibAux
    private synchronized native int _LdoFile(CPtr ptr, String fileName);

    private synchronized native int _LdoString(CPtr ptr, String string);

    // private synchronized native int _doBuffer(CPtr ptr, byte[] buff, long sz, String n);
    private synchronized native int _LgetMetaField(CPtr ptr, int obj, String e);

    private synchronized native int _LcallMeta(CPtr ptr, int obj, String e);

    private synchronized native int _Ltyperror(CPtr ptr, int nArg, String tName);

    private synchronized native int _LargError(CPtr ptr, int numArg, String extraMsg);

    private synchronized native String _LcheckString(CPtr ptr, int numArg);

    private synchronized native String _LoptString(CPtr ptr, int numArg, String def);

    private synchronized native double _LcheckNumber(CPtr ptr, int numArg);

    private synchronized native double _LoptNumber(CPtr ptr, int numArg, double def);

    private synchronized native int _LcheckInteger(CPtr ptr, int numArg);

    private synchronized native int _LoptInteger(CPtr ptr, int numArg, int def);

    private synchronized native void _LcheckStack(CPtr ptr, int sz, String msg);

    private synchronized native void _LcheckType(CPtr ptr, int nArg, int t);

    private synchronized native void _LcheckAny(CPtr ptr, int nArg);

    private synchronized native int _LnewMetatable(CPtr ptr, String tName);

    private synchronized native void _LgetMetatable(CPtr ptr, String tName);

    private synchronized native void _Lwhere(CPtr ptr, int lvl);

    private synchronized native int _Lref(CPtr ptr, int t);

    private synchronized native void _LunRef(CPtr ptr, int t, int ref);

    private synchronized native int _LgetN(CPtr ptr, int t);

    private synchronized native void _LsetN(CPtr ptr, int t, int n);

    private synchronized native int _LloadFile(CPtr ptr, String fileName);

    private synchronized native int _LloadBuffer(CPtr ptr, byte[] buff, long sz, String name);

    private synchronized native int _LloadString(CPtr ptr, String s);

    private synchronized native String _Lgsub(CPtr ptr, String s, String p, String r);

    private synchronized native String _LfindTable(CPtr ptr, int idx, String fname, int szhint);

    private synchronized native void _openBase(CPtr ptr);

    private synchronized native void _openTable(CPtr ptr);

    private synchronized native void _openIo(CPtr ptr);

    private synchronized native void _openOs(CPtr ptr);

    private synchronized native void _openString(CPtr ptr);

    private synchronized native void _openMath(CPtr ptr);

    private synchronized native void _openDebug(CPtr ptr);

    private synchronized native void _openPackage(CPtr ptr);

    private synchronized native void _openLibs(CPtr ptr);

    // Java Interface -----------------------------------------------------
    public LuaState newThread() {
        final LuaState l = new LuaState(this._newthread(luaState));
        LuaStateFactory.insertLuaState(l);
        return l;
    }

    // STACK MANIPULATION
    public int getTop() {
        return this._getTop(luaState);
    }

    public void setTop(final int idx) {
        this._setTop(luaState, idx);
    }

    public void pushValue(final int idx) {
        this._pushValue(luaState, idx);
    }

    public void remove(final int idx) {
        this._remove(luaState, idx);
    }

    public void insert(final int idx) {
        this._insert(luaState, idx);
    }

    public void replace(final int idx) {
        this._replace(luaState, idx);
    }

    public int checkStack(final int sz) {
        return this._checkStack(luaState, sz);
    }

    public void xmove(final LuaState to, final int n) {
        this._xmove(luaState, to.luaState, n);
    }

    // ACCESS FUNCTION
    public boolean isNumber(final int idx) {
        return (this._isNumber(luaState, idx) != 0);
    }

    public boolean isString(final int idx) {
        return (this._isString(luaState, idx) != 0);
    }

    public boolean isFunction(final int idx) {
        return (this._isFunction(luaState, idx) != 0);
    }

    public boolean isCFunction(final int idx) {
        return (this._isCFunction(luaState, idx) != 0);
    }

    public boolean isUserdata(final int idx) {
        return (this._isUserdata(luaState, idx) != 0);
    }

    public boolean isTable(final int idx) {
        return (this._isTable(luaState, idx) != 0);
    }

    public boolean isBoolean(final int idx) {
        return (this._isBoolean(luaState, idx) != 0);
    }

    public boolean isNil(final int idx) {
        return (this._isNil(luaState, idx) != 0);
    }

    public boolean isThread(final int idx) {
        return (this._isThread(luaState, idx) != 0);
    }

    public boolean isNone(final int idx) {
        return (this._isNone(luaState, idx) != 0);
    }

    public boolean isNoneOrNil(final int idx) {
        return (this._isNoneOrNil(luaState, idx) != 0);
    }

    public int type(final int idx) {
        return this._type(luaState, idx);
    }

    public String typeName(final int tp) {
        return this._typeName(luaState, tp);
    }

    public int equal(final int idx1, final int idx2) {
        return this._equal(luaState, idx1, idx2);
    }

    public int rawequal(final int idx1, final int idx2) {
        return this._rawequal(luaState, idx1, idx2);
    }

    public int lessthan(final int idx1, final int idx2) {
        return this._lessthan(luaState, idx1, idx2);
    }

    public double toNumber(final int idx) {
        return this._toNumber(luaState, idx);
    }

    public int toInteger(final int idx) {
        return this._toInteger(luaState, idx);
    }

    public boolean toBoolean(final int idx) {
        return (this._toBoolean(luaState, idx) != 0);
    }

    public String toString(final int idx) {
        return this._toString(luaState, idx);
    }

    public int strLen(final int idx) {
        return this._strlen(luaState, idx);
    }

    public int objLen(final int idx) {
        return this._objlen(luaState, idx);
    }

    public LuaState toThread(final int idx) {
        return new LuaState(this._toThread(luaState, idx));
    }

    // PUSH FUNCTIONS
    public void pushNil() {
        this._pushNil(luaState);
    }

    public void pushNumber(final double db) {
        this._pushNumber(luaState, db);
    }

    public void pushInteger(final int integer) {
        this._pushInteger(luaState, integer);
    }

    public void pushString(final String str) {
        if (str == null) {
            this._pushNil(luaState);
        } else {
            this._pushString(luaState, str);
        }
    }

    public void pushString(final byte[] bytes) {
        if (bytes == null) {
            this._pushNil(luaState);
        } else {
            this._pushString(luaState, bytes, bytes.length);
        }
    }

    public void pushBoolean(final boolean bool) {
        this._pushBoolean(luaState, bool ? 1 : 0);
    }

    // GET FUNCTIONS
    public void getTable(final int idx) {
        this._getTable(luaState, idx);
    }

    public void getField(final int idx, final String k) {
        this._getField(luaState, idx, k);
    }

    public void rawGet(final int idx) {
        this._rawGet(luaState, idx);
    }

    public void rawGetI(final int idx, final int n) {
        this._rawGetI(luaState, idx, n);
    }

    public void createTable(final int narr, final int nrec) {
        this._createTable(luaState, narr, nrec);
    }

    public void newTable() {
        this._newTable(luaState);
    }

    // if returns 0, there is no metatable
    public int getMetaTable(final int idx) {
        return this._getMetaTable(luaState, idx);
    }

    public void getFEnv(final int idx) {
        this._getFEnv(luaState, idx);
    }

    // SET FUNCTIONS
    public void setTable(final int idx) {
        this._setTable(luaState, idx);
    }

    public void setField(final int idx, final String k) {
        this._setField(luaState, idx, k);
    }

    public void rawSet(final int idx) {
        this._rawSet(luaState, idx);
    }

    public void rawSetI(final int idx, final int n) {
        this._rawSetI(luaState, idx, n);
    }

    // if returns 0, cannot set the metatable to the given object
    public int setMetaTable(final int idx) {
        return this._setMetaTable(luaState, idx);
    }

    // if object is not a function returns 0
    public int setFEnv(final int idx) {
        return this._setFEnv(luaState, idx);
    }

    public void call(final int nArgs, final int nResults) {
        this._call(luaState, nArgs, nResults);
    }

    // returns 0 if ok of one of the error codes defined
    public int pcall(final int nArgs, final int nResults, final int errFunc) {
        return this._pcall(luaState, nArgs, nResults, errFunc);
    }

    public int yield(final int nResults) {
        return this._yield(luaState, nResults);
    }

    public int resume(final int nArgs) {
        return this._resume(luaState, nArgs);
    }

    public int status() {
        return this._status(luaState);
    }

    public int gc(final int what, final int data) {
        return this._gc(luaState, what, data);
    }

    public int getGcCount() {
        return this._getGcCount(luaState);
    }

    public int next(final int idx) {
        return this._next(luaState, idx);
    }

    public int error() {
        return this._error(luaState);
    }

    public void concat(final int n) {
        this._concat(luaState, n);
    }

    // FUNCTION FROM lauxlib
    // returns 0 if ok
    public int LdoFile(final String fileName) {
        return this._LdoFile(luaState, fileName);
    }

    // returns 0 if ok
    public int LdoString(final String str) {
        return this._LdoString(luaState, str);
    }

    public int LgetMetaField(final int obj, final String e) {
        return this._LgetMetaField(luaState, obj, e);
    }

    public int LcallMeta(final int obj, final String e) {
        return this._LcallMeta(luaState, obj, e);
    }

    public int Ltyperror(final int nArg, final String tName) {
        return this._Ltyperror(luaState, nArg, tName);
    }

    public int LargError(final int numArg, final String extraMsg) {
        return this._LargError(luaState, numArg, extraMsg);
    }

    public String LcheckString(final int numArg) {
        return this._LcheckString(luaState, numArg);
    }

    public String LoptString(final int numArg, final String def) {
        return this._LoptString(luaState, numArg, def);
    }

    public double LcheckNumber(final int numArg) {
        return this._LcheckNumber(luaState, numArg);
    }

    public double LoptNumber(final int numArg, final double def) {
        return this._LoptNumber(luaState, numArg, def);
    }

    public int LcheckInteger(final int numArg) {
        return this._LcheckInteger(luaState, numArg);
    }

    public int LoptInteger(final int numArg, final int def) {
        return this._LoptInteger(luaState, numArg, def);
    }

    public void LcheckStack(final int sz, final String msg) {
        this._LcheckStack(luaState, sz, msg);
    }

    public void LcheckType(final int nArg, final int t) {
        this._LcheckType(luaState, nArg, t);
    }

    public void LcheckAny(final int nArg) {
        this._LcheckAny(luaState, nArg);
    }

    public int LnewMetatable(final String tName) {
        return this._LnewMetatable(luaState, tName);
    }

    public void LgetMetatable(final String tName) {
        this._LgetMetatable(luaState, tName);
    }

    public void Lwhere(final int lvl) {
        this._Lwhere(luaState, lvl);
    }

    public int Lref(final int t) {
        return this._Lref(luaState, t);
    }

    public void LunRef(final int t, final int ref) {
        this._LunRef(luaState, t, ref);
    }

    public int LgetN(final int t) {
        return this._LgetN(luaState, t);
    }

    public void LsetN(final int t, final int n) {
        this._LsetN(luaState, t, n);
    }

    public int LloadFile(final String fileName) {
        return this._LloadFile(luaState, fileName);
    }

    public int LloadString(final String s) {
        return this._LloadString(luaState, s);
    }

    public int LloadBuffer(final byte[] buff, final String name) {
        return this._LloadBuffer(luaState, buff, buff.length, name);
    }

    public String Lgsub(final String s, final String p, final String r) {
        return this._Lgsub(luaState, s, p, r);
    }

    public String LfindTable(final int idx, final String fname, final int szhint) {
        return this._LfindTable(luaState, idx, fname, szhint);
    }

    // IMPLEMENTED C MACROS
    public void pop(final int n) {
        // setTop(- (n) - 1);
        this._pop(luaState, n);
    }

    public synchronized void getGlobal(final String global) {
        // pushString(global);
        // getTable(LUA_GLOBALSINDEX.intValue());
        this._getGlobal(luaState, global);
    }

    public synchronized void setGlobal(final String name) {
        // pushString(name);
        // insert(-2);
        // setTable(LUA_GLOBALSINDEX.intValue());
        this._setGlobal(luaState, name);
    }

    // Functions to open lua libraries
    public void openBase() {
        this._openBase(luaState);
    }

    public void openTable() {
        this._openTable(luaState);
    }

    public void openIo() {
        this._openIo(luaState);
    }

    public void openOs() {
        this._openOs(luaState);
    }

    public void openString() {
        this._openString(luaState);
    }

    public void openMath() {
        this._openMath(luaState);
    }

    public void openDebug() {
        this._openDebug(luaState);
    }

    public void openPackage() {
        this._openPackage(luaState);
    }

    public void openLibs() {
        this._openLibs(luaState);
    }

    /********************** Luajava API Library **********************/
    /**
     * Initializes lua State to be used by luajava
     * 
     * @param cptr
     * @param stateId
     */
    private synchronized native void luajava_open(CPtr cptr, int stateId);

    /**
     * Gets a Object from a userdata
     * 
     * @param L
     * @param idx index of the lua stack
     * @return Object
     */
    private synchronized native Object _getObjectFromUserdata(CPtr L, int idx) throws LuaException;

    /**
     * Returns whether a userdata contains a Java Object
     * 
     * @param L
     * @param idx index of the lua stack
     * @return boolean
     */
    private synchronized native boolean _isObject(CPtr L, int idx);

    /**
     * Pushes a Java Object into the state stack
     * 
     * @param L
     * @param obj
     */
    private synchronized native void _pushJavaObject(CPtr L, Object obj);

    /**
     * Pushes a JavaFunction into the state stack
     * 
     * @param L
     * @param func
     */
    private synchronized native void _pushJavaFunction(CPtr L, JavaFunction func)
            throws LuaException;

    /**
     * Returns whether a userdata contains a Java Function
     * 
     * @param L
     * @param idx index of the lua stack
     * @return boolean
     */
    private synchronized native boolean _isJavaFunction(CPtr L, int idx);

    /**
     * Gets a Object from Lua
     * 
     * @param idx index of the lua stack
     * @return Object
     * @throws LuaException if the lua object does not represent a java object.
     */
    public Object getObjectFromUserdata(final int idx) throws LuaException {
        return this._getObjectFromUserdata(luaState, idx);
    }

    /**
     * Tells whether a lua index contains a java Object
     * 
     * @param idx index of the lua stack
     * @return boolean
     */
    public boolean isObject(final int idx) {
        return this._isObject(luaState, idx);
    }

    /**
     * Pushes a Java Object into the lua stack.<br>
     * This function does not check if the object is from a class that could be represented by a lua
     * type. Eg: java.lang.String could be a lua string.
     * 
     * @param obj Object to be pushed into lua
     */
    public void pushJavaObject(final Object obj) {
        this._pushJavaObject(luaState, obj);
    }

    /**
     * Pushes a JavaFunction into the state stack
     * 
     * @param func
     */
    public void pushJavaFunction(final JavaFunction func) throws LuaException {
        this._pushJavaFunction(luaState, func);
    }

    /**
     * Returns whether a userdata contains a Java Function
     * 
     * @param idx index of the lua stack
     * @return boolean
     */
    public boolean isJavaFunction(final int idx) {
        return this._isJavaFunction(luaState, idx);
    }

    /**
     * Pushes into the stack any object value.<br>
     * This function checks if the object could be pushed as a lua type, if not pushes the java
     * object.
     * 
     * @param obj
     */
    public void pushObjectValue(final Object obj) throws LuaException {
        if (obj == null) {
            this.pushNil();
        } else if (obj instanceof Boolean) {
            final Boolean bool = (Boolean)obj;
            this.pushBoolean(bool.booleanValue());
        } else if (obj instanceof Number) {
            this.pushNumber(((Number)obj).doubleValue());
        } else if (obj instanceof String) {
            this.pushString((String)obj);
        } else if (obj instanceof JavaFunction) {
            final JavaFunction func = (JavaFunction)obj;
            this.pushJavaFunction(func);
        } else if (obj instanceof LuaObject) {
            final LuaObject ref = (LuaObject)obj;
            ref.push();
        } else if (obj instanceof byte[]) {
            this.pushString((byte[])obj);
        } else {
            this.pushJavaObject(obj);
        }
    }

    /**
     * Function that returns a Java Object equivalent to the one in the given position of the Lua
     * Stack.
     * 
     * @param idx Index in the Lua Stack
     * @return Java object equivalent to the Lua one
     */
    public synchronized Object toJavaObject(final int idx) throws LuaException {
        Object obj = null;
        if (this.isBoolean(idx)) {
            obj = new Boolean(this.toBoolean(idx));
        } else if (this.type(idx) == LuaState.LUA_TSTRING.intValue()) {
            obj = this.toString(idx);
        } else if (this.isFunction(idx)) {
            obj = this.getLuaObject(idx);
        } else if (this.isTable(idx)) {
            obj = this.getLuaObject(idx);
        } else if (this.type(idx) == LuaState.LUA_TNUMBER.intValue()) {
            obj = new Double(this.toNumber(idx));
        } else if (this.isUserdata(idx)) {
            if (this.isObject(idx)) {
                obj = this.getObjectFromUserdata(idx);
            } else {
                obj = this.getLuaObject(idx);
            }
        } else if (this.isNil(idx)) {
            obj = null;
        }
        return obj;
    }

    /**
     * Creates a reference to an object in the variable globalName
     * 
     * @param globalName
     * @return LuaObject
     */
    public LuaObject getLuaObject(final String globalName) {
        return new LuaObject(this, globalName);
    }

    /**
     * Creates a reference to an object inside another object
     * 
     * @param parent The Lua Table or Userdata that contains the Field.
     * @param name The name that index the field
     * @return LuaObject
     * @throws LuaException if parent is not a table or userdata
     */
    public LuaObject getLuaObject(final LuaObject parent, final String name) throws LuaException {
        if (parent.L.getCPtrPeer() != luaState.getPeer()) {
            throw new LuaException("Object must have the same LuaState as the parent!");
        }
        return new LuaObject(parent, name);
    }

    /**
     * This constructor creates a LuaObject from a table that is indexed by a number.
     * 
     * @param parent The Lua Table or Userdata that contains the Field.
     * @param name The name (number) that index the field
     * @return LuaObject
     * @throws LuaException When the parent object isn't a Table or Userdata
     */
    public LuaObject getLuaObject(final LuaObject parent, final Number name) throws LuaException {
        if (parent.L.getCPtrPeer() != luaState.getPeer()) {
            throw new LuaException("Object must have the same LuaState as the parent!");
        }
        return new LuaObject(parent, name);
    }

    /**
     * This constructor creates a LuaObject from a table that is indexed by any LuaObject.
     * 
     * @param parent The Lua Table or Userdata that contains the Field.
     * @param name The name (LuaObject) that index the field
     * @return LuaObject
     * @throws LuaException When the parent object isn't a Table or Userdata
     */
    public LuaObject getLuaObject(final LuaObject parent, final LuaObject name) throws LuaException {
        if ((parent.getLuaState().getCPtrPeer() != luaState.getPeer())
                || (parent.getLuaState().getCPtrPeer() != name.getLuaState().getCPtrPeer())) {
            throw new LuaException("Object must have the same LuaState as the parent!");
        }
        return new LuaObject(parent, name);
    }

    /**
     * Creates a reference to an object in the <code>index</code> position of the stack
     * 
     * @param index position on the stack
     * @return LuaObject
     */
    public LuaObject getLuaObject(final int index) {
        return new LuaObject(this, index);
    }

    /**
     * When you call a function in lua, it may return a number, and the number will be interpreted
     * as a <code>Double</code>.<br>
     * This function converts the number into a type specified by <code>retType</code>
     * 
     * @param db lua number to be converted
     * @param retType type to convert to
     * @return The converted number
     */
    public static Number convertLuaNumber(final Double db, final Class retType) {
        // checks if retType is a primitive type
        if (retType.isPrimitive()) {
            if (retType == Integer.TYPE) {
                return new Integer(db.intValue());
            } else if (retType == Long.TYPE) {
                return new Long(db.longValue());
            } else if (retType == Float.TYPE) {
                return new Float(db.floatValue());
            } else if (retType == Double.TYPE) {
                return db;
            } else if (retType == Byte.TYPE) {
                return new Byte(db.byteValue());
            } else if (retType == Short.TYPE) {
                return new Short(db.shortValue());
            }
        } else if (retType.isAssignableFrom(Number.class)) {
            // Checks all possibilities of number types
            if (retType.isAssignableFrom(Integer.class)) {
                return new Integer(db.intValue());
            } else if (retType.isAssignableFrom(Long.class)) {
                return new Long(db.longValue());
            } else if (retType.isAssignableFrom(Float.class)) {
                return new Float(db.floatValue());
            } else if (retType.isAssignableFrom(Double.class)) {
                return db;
            } else if (retType.isAssignableFrom(Byte.class)) {
                return new Byte(db.byteValue());
            } else if (retType.isAssignableFrom(Short.class)) {
                return new Short(db.shortValue());
            }
        }
        // if all checks fail, return null
        return null;
    }

    public String dumpStack() {
        final int n = this.getTop();
        final StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            final int t = this.type(i);
            sb.append(i).append(": ").append(this.typeName(t));
            if (t == LUA_TNUMBER) {
                sb.append(" = ").append(this.toNumber(i));
            } else if (t == LUA_TSTRING) {
                sb.append(" = '").append(this.toString(i)).append("'");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
