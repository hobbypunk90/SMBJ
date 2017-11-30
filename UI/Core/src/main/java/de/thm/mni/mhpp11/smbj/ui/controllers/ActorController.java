package de.thm.mni.mhpp11.smbj.ui.controllers;

import de.thm.mni.mhpp11.smbj.messages.ExitMessage;
import de.thm.mni.mhpp11.smbj.ui.actors.AbstractUIActor;
import de.thm.mni.mhpp11.smbj.ui.messages.HideStageMessage;
import de.thm.mni.mhpp11.smbj.ui.messages.ShowStageMessage;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by hobbypunk on 18.01.17.
 */
@Getter
@Setter
public abstract class ActorController implements IActorController {
  
  private AbstractUIActor<? extends ActorController> actor;
  
  private Stage stage;
  private Scene scene;
  
  @Setter private List<?> params = Collections.EMPTY_LIST;
  
  @Override
  public UUID getID() {
    return actor.getID();
  }
  
  
  public void lateInitialize() {
    this.getStage().setOnCloseRequest(event -> {
      event.consume();
      close();
    });
  
  }
  
  public void show() {
    this.getStage().toFront();
    this.getStage().show();
    getActor().send(new ShowStageMessage(getActor().getID(), this.getStage()));
  }
  
  public void hide() {
    getActor().send(new HideStageMessage(getActor().getID(), this.getStage()));
    this.getStage().hide();
  }
  
  public void close() {
    getActor().send(new ExitMessage(getID(), getID()));
  }
  
  public void onClose() {
    this.getStage().hide();
  }
}
