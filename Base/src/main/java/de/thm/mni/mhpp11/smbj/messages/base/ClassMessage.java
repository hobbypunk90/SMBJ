package de.thm.mni.mhpp11.smbj.messages.base;


import de.thm.mni.mhpp11.smbj.actors.IActor;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public abstract class ClassMessage extends SinkedMessage<Class<? extends IActor>> {
  public ClassMessage(UUID source) {
    super(source);
  }
  
  public ClassMessage(UUID source, Class<? extends IActor> sink) {
    super(source, sink);
  }
  
  public ClassMessage(UUID source, List<Class<? extends IActor>> sinks) {
    super(source, sinks);
  }
  
}
