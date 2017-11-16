package de.thm.mni.mhpp11.smbj.messages.base;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public abstract class SinkedMessage<T> extends Message {
  List<T> sinks;
  
  public SinkedMessage(UUID source) {
    super(source);
    this.sinks = Collections.EMPTY_LIST;
  }
  
  public SinkedMessage(UUID source, T sink) {
    super(source);
    if(sink != null) this.sinks = Collections.singletonList(sink);
    else this.sinks = Collections.EMPTY_LIST;
  }
  
  public SinkedMessage(UUID source, List<T> sinks) {
    super(source);
    this.sinks = sinks;
  }
  
  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    for(T sink : this.getSinks()) {
      str.append((str.length() == 0) ? "[" : ", ").append((sink instanceof Class) ? ((Class) sink).getSimpleName() : sink.toString());
    }
    str.append("]");
    
    return String.format("%s -> %s", super.toString(), str.toString());
  }
}
