
package com.wartricks.custom;

import com.badlogic.gdx.utils.Array;
import com.wartricks.logic.GameMap;

public class PositionArray extends Array<Pair> {
    private GameMap board;

    public PositionArray(GameMap board) {
        super();
        this.board = board;
    }

    public PositionArray(GameMap board, Pair value) {
        super();
        this.board = board;
        this.add(value);
    }

    public void add(int x, int y) {
        this.add(new Pair(x, y));
    }

    @Override
    public void add(Pair value) {
        if ((value.x >= 0) && (value.y >= 0) && (value.x < board.width) && (value.y < board.height)
                && !this.contains(value, false)) {
            super.add(value);
        }
    }
}
