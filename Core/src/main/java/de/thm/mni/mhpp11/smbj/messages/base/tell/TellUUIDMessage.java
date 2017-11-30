package de.thm.mni.mhpp11.smbj.messages.base.tell;

import de.thm.mni.mhpp11.smbj.messages.base.UUIDMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Getter
public class TellUUIDMessage<T> extends UUIDMessage implements TellMessage<T> {
  T payload;
  
  public TellUUIDMessage(UUID source, T payload) {
    super(source);
    this.payload = payload;
  }
  
  public TellUUIDMessage(UUID source, UUID sink, T payload) {
    super(source, sink);
    this.payload = payload;
  }
  
  public TellUUIDMessage(UUID source, List<UUID> sinks, T payload) {
    super(source, sinks);
    this.payload = payload;
  }
  
  public String toString() {
    return String.format("%s: %s", super.toString(), this.getPayload());
  }
}
