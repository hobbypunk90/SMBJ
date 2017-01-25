package de.thm.mni.mhpp11.jActor.messages.interfaces;

import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

/**
 * Created by hobbypunk on 12.01.17.
 */
@Getter
public abstract class Message {
  
  private final UUID group;
  private final Integer to;
  private Integer tried = 0;
  
  public Message(@NonNull UUID group, @NonNull Integer to) {
    this.group = group;
    this.to = to;
  }
  
  @Override
  public String toString() {
    return String.format("%s - %3d: try %2d %s", this.getGroup(), this.getTo(), this.getTried(), this.getClass().getSimpleName());
  }
  
  public Integer nextTry() {
    this.tried++;
    return this.tried;
  }
}
