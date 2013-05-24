
package com.wartricks.components;

import java.util.concurrent.LinkedBlockingDeque;

import com.artemis.Component;

public class Path extends Component {
    public LinkedBlockingDeque<Movement> path;

    public Path(Movement move) {
        path = new LinkedBlockingDeque<Movement>();
        path.addLast(move);
    }

    public Path() {
        path = new LinkedBlockingDeque<Movement>();
    }
}
