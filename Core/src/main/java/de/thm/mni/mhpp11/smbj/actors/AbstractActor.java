package de.thm.mni.mhpp11.smbj.actors;

import de.thm.mni.mhpp11.smbj.messages.base.Message;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by hobbypunk on 12.01.17.
 */
@Data
@FieldDefaults(makeFinal = true)
public abstract class AbstractActor implements IActor {
  
  BlockingQueue<Message> inbox = new LinkedBlockingQueue<>();
  
  UUID ID;
  
  public AbstractActor(UUID id) {this.ID = id;}
}
