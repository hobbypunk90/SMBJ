package de.thm.mni.mhpp11.smbj.messages.base.ask;

import de.thm.mni.mhpp11.smbj.actors.IActor;
import de.thm.mni.mhpp11.smbj.messages.base.tell.TellClassMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class SimpleAskClassMessage<T, D> extends TellClassMessage<T> implements SimpleAskMessage<T, D> {
  public SimpleAskClassMessage(UUID source, T payload) {
    super(source, payload);
  }
  
  public SimpleAskClassMessage(UUID source, Class<? extends IActor> sink, T payload) {
    super(source, sink, payload);
  }
  
  public SimpleAskClassMessage(UUID source, List<Class<? extends IActor>> sinks, T payload) {
    super(source, sinks, payload);
  }
  
}
