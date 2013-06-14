
package com.wartricks.components;

import com.artemis.Component;
import com.wartricks.utils.Constants.Players;

public class Owner extends Component {
    public Players owner;

    public Owner(Players owner) {
        super();
        this.owner = owner;
    }
}
