package de.thm.mni.mhpp11.smbj.messages;

import de.thm.mni.mhpp11.smbj.messages.base.Message;

import java.util.UUID;

/**
 * Created by hobbypunk on 12.01.17.
 */
public class ErrorExitMessage extends Message {
  public ErrorExitMessage(UUID source) {
    super(source);
  }
}
