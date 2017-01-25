package de.thm.mni.mhpp11.jActor.actors.ui.messages;

import de.thm.mni.mhpp11.jActor.actors.utilities.Constants;
import de.thm.mni.mhpp11.jActor.messages.interfaces.Message;
import lombok.NonNull;

import java.util.UUID;

/**
 * Created by hobbypunk on 24.01.17.
 */
public abstract class UIMessage extends Message {
  public UIMessage(@NonNull UUID group) {
    super(group, Constants.UI);
  }
}
