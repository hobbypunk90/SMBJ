package de.thm.mni.mhpp11.smbj.messages.base.tell;

import de.thm.mni.mhpp11.smbj.messages.base.ClassMessage;
import de.thm.mni.mhpp11.smbj.actors.IActor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Getter
public class TellClassMessage<T> extends ClassMessage implements TellMessage<T> {
  T payload;
  
  public TellClassMessage(UUID source, T payload) {
    super(source);
    this.payload = payload;
  }
  
  public TellClassMessage(UUID source, Class<? extends IActor> sink, T payload) {
    super(source, sink);
    this.payload = payload;
  }
  
  public TellClassMessage(UUID source, List<Class<? extends IActor>> sinks, T payload) {
    super(source, sinks);
    this.payload = payload;
  }
  
  public String toString() {
    return String.format("%s: %s", super.toString(), this.getPayload());
  }
}
