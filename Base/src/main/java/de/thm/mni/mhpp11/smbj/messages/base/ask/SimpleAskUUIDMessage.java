package de.thm.mni.mhpp11.smbj.messages.base.ask;

import de.thm.mni.mhpp11.smbj.messages.base.tell.TellUUIDMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class SimpleAskUUIDMessage<T, D> extends TellUUIDMessage<T> implements SimpleAskMessage<T, D> {
  
  public SimpleAskUUIDMessage(UUID source, T payload) {
    super(source, payload);
  }
  
  public SimpleAskUUIDMessage(UUID source, UUID sink, T payload) {
    super(source, sink, payload);
  }
  
  public SimpleAskUUIDMessage(UUID source, List<UUID> sinks, T payload) {
    super(source, sinks, payload);
  }
  
}
