
package com.wartricks.ui;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class FramedDialog {
    private Dialog dialog;

    private Image frame;

    private Skin skin;

    float width, height;

    public FramedDialog(Skin skin, String title, String message, float width, float height) {
        this.skin = skin;
        this.width = width;
        this.height = height;
        dialog = new Dialog(title, skin);
        dialog.setBackground(skin.getTiledDrawable("menuTexture"));
        dialog.getContentTable().defaults().expandX().fillX();
        dialog.getButtonTable().defaults().width(50).fillX();
        final Label label = new Label(message, skin);
        label.setAlignment(Align.center);
        label.setWrap(true);
        dialog.text(label);
        frame = new Image(skin.getPatch("frame"));
    }

    public void addButton(String text, ChangeListener changeListener) {
        final TextButton button = new TextButton(text, skin);
        button.addListener(changeListener);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                frame.addAction(sequence(fadeOut(0.0f, Interpolation.fade), Actions.removeActor()));
            }
        });
        dialog.button(button);
    }

    public void addButton(Button button) {
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                frame.addAction(sequence(fadeOut(0.0f, Interpolation.fade), Actions.removeActor()));
            }
        });
        dialog.button(button);
    }

    public void addToStage(Stage stage, float x, float y) {
        stage.addActor(dialog);
        stage.addActor(frame);
        dialog.setX(x);
        dialog.setY(y);
        dialog.setWidth(width);
        dialog.setHeight(height);
        frame.setX(x - 1);
        frame.setY(y - 3);
        frame.setWidth(width + 4);
        frame.setHeight(height + 4);
        frame.setTouchable(Touchable.disabled);
    }

    public void clear() {
        dialog.cancel();
    }
}
