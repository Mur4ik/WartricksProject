
package com.wartricks.custom;

import com.badlogic.gdx.utils.Array;
import com.wartricks.boards.GameMap;

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

    @Override
    public void add(Pair value) {
        if ((value.x >= 0) && (value.y >= 0) && (value.x < board.width) && (value.y < board.height)) {
            super.add(value);
        }
    }
}
