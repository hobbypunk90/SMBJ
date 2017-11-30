package de.thm.mni.mhpp11.smbj.ui.controllers;

import de.thm.mni.mhpp11.smbj.logging.messages.LogMessage;
import de.thm.mni.mhpp11.smbj.logging.messages.WarnMessage;
import de.thm.mni.mhpp11.smbj.ui.actors.TestActor;
import de.thm.mni.mhpp11.smbj.ui.messages.StartUIMessage;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import lombok.Getter;

/**
 * Created by hobbypunk on 16.01.17.
 */
@Getter
public class TestController extends ActorController {
  @FXML TextArea textArea;
  
  @FXML
  public void onOpenNew() {
    getActor().send(new StartUIMessage(TestActor.class, new Stage()));
  }
  
  @FXML
  public void onPrint() {
    getActor().send(new WarnMessage(this.getClass(), getID(),"Hello World!"));
  }
  
  public void handleLogMessage(LogMessage msg) {
    textArea.appendText(msg.toString() + "\n");
  }
}
