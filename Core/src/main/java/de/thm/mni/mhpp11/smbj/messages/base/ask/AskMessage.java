package de.thm.mni.mhpp11.smbj.messages.base.ask;

import java.util.UUID;

public interface AskMessage<T, D, R> {
  
  UUID getSource();
  
  R answer(UUID source, D payload);
  
  default void error(UUID source, Exception e) {}
}
