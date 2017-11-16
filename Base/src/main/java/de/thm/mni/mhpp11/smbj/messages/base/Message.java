package de.thm.mni.mhpp11.smbj.messages.base;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public abstract class Message {
  UUID source;
  
  @Override
  public String toString() {
    return String.format("%s", this.getSource());
  }
}
