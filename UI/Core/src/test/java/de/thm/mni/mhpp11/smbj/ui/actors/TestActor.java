package de.thm.mni.mhpp11.smbj.ui.actors;

import de.thm.mni.mhpp11.smbj.logging.messages.LogMessage;
import de.thm.mni.mhpp11.smbj.manager.messages.StartedMessage;
import de.thm.mni.mhpp11.smbj.messages.base.Message;
import de.thm.mni.mhpp11.smbj.ui.controllers.TestController;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.List;
import java.util.UUID;

public class TestActor extends AbstractUIActor<TestController> {
  
  public TestActor(UUID id, Stage stage, List<String> params) {
    super(id, stage);
  }
  
  @Override
  public void executeUI(Message msg) {
    if(msg instanceof StartedMessage)
      getController().show();
    else if(msg instanceof LogMessage) {
      Platform.runLater(() -> getController().handleLogMessage((LogMessage)msg));
    }
  }
}
