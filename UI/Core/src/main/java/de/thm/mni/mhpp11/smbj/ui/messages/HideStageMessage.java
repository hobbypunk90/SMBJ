package de.thm.mni.mhpp11.smbj.ui.messages;

import de.thm.mni.mhpp11.smbj.messages.base.tell.TellUUIDMessage;
import javafx.stage.Stage;

import java.util.UUID;

public class HideStageMessage extends TellUUIDMessage<Stage> {
  public HideStageMessage(UUID source, Stage payload) {
    super(source, payload);
  }
}
