package de.thm.mni.mhpp11.smbj.ui.messages;

import de.thm.mni.mhpp11.smbj.messages.base.tell.TellUUIDMessage;
import javafx.stage.Stage;

import java.util.UUID;

public class ShowStageMessage extends TellUUIDMessage<Stage> {
  public ShowStageMessage(UUID source, Stage payload) {
    super(source, payload);
  }
}
