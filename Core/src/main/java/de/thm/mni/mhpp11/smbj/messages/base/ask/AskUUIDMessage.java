package de.thm.mni.mhpp11.smbj.messages.base.ask;

import de.thm.mni.mhpp11.smbj.messages.base.tell.TellUUIDMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AskUUIDMessage<T, D, R> extends TellUUIDMessage<T> implements AskMessage<T, D, R> {
  
  public AskUUIDMessage(UUID source, T payload) {
    super(source, payload);
  }
  
  public AskUUIDMessage(UUID source, UUID sink, T payload) {
    super(source, sink, payload);
  }
  
  public AskUUIDMessage(UUID source, List<UUID> sinks, T payload) {
    super(source, sinks, payload);
  }
  
}
