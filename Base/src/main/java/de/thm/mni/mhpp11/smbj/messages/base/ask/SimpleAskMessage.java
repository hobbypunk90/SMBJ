package de.thm.mni.mhpp11.smbj.messages.base.ask;

import de.thm.mni.mhpp11.smbj.messages.base.tell.TellUUIDMessage;

import java.util.UUID;

public interface SimpleAskMessage<T, D> extends AskMessage<T, D, TellUUIDMessage<D>> {
  T getPayload();
  UUID getSource();
  
  default TellUUIDMessage<D> answer(UUID source, D payload) {
    return new TellUUIDMessage<>(source, this.getSource(), payload);
  }
}
