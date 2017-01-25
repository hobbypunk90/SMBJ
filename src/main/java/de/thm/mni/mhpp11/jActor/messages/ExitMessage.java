package de.thm.mni.mhpp11.jActor.messages;

import de.thm.mni.mhpp11.jActor.actors.utilities.Constants;
import de.thm.mni.mhpp11.jActor.messages.interfaces.Message;

import java.util.UUID;

/**
 * Created by hobbypunk on 12.01.17.
 */
public class ExitMessage extends Message {

  public ExitMessage() {
    this(Constants.ALLGROUPS, Constants.getId("ALL"));
  }

  public ExitMessage(UUID group, Integer to) {
    super(group, to);
  }
  
  public ExitMessage(UUID group) {
    this(group, Constants.getId("ALL"));
  }
  
}
