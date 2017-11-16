package de.thm.mni.mhpp11.smbj.messages.base.ask;

import de.thm.mni.mhpp11.smbj.actors.IActor;
import de.thm.mni.mhpp11.smbj.messages.base.tell.TellClassMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AskClassMessage<T, D, R> extends TellClassMessage<T> implements AskMessage<T, D, R> {
  public AskClassMessage(UUID source, T payload) {
    super(source, payload);
  }
  
  public AskClassMessage(UUID source, Class<? extends IActor> sink, T payload) {
    super(source, sink, payload);
  }
  
  public AskClassMessage(UUID source, List<Class<? extends IActor>> sinks, T payload) {
    super(source, sinks, payload);
  }
  
}
