
package com.wartricks.components;

import java.util.concurrent.LinkedBlockingDeque;

import com.artemis.Component;

public class Path extends Component {
    public LinkedBlockingDeque<Move> path;

    public Path(Move move) {
        path = new LinkedBlockingDeque<Move>();
        path.addLast(move);
    }

    public Path() {
        path = new LinkedBlockingDeque<Move>();
    }
}
