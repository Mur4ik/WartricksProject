
package com.wartricks.components;

import com.artemis.Component;
import com.wartricks.utils.Constants.Players;

public class Owner extends Component {
    private Players owner;

    public Owner(Players owner) {
        super();
        this.setOwner(owner);
    }

    public Players getOwner() {
        return owner;
    }

    public void setOwner(Players owner) {
        this.owner = owner;
    }
}
