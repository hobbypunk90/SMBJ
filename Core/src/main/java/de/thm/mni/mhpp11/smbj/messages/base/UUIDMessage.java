package de.thm.mni.mhpp11.smbj.messages.base;


import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public abstract class UUIDMessage extends SinkedMessage<UUID> {
  public UUIDMessage(UUID source) {
    super(source);
  }
  
  public UUIDMessage(UUID source, UUID sink) {
    super(source, sink);
  }
  
  public UUIDMessage(UUID source, List<UUID> sinks) {
    super(source, sinks);
  }
}
