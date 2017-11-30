package de.thm.mni.mhpp11.smbj.ui.messages;

import de.thm.mni.mhpp11.smbj.actors.IActor;
import de.thm.mni.mhpp11.smbj.manager.messages.InitStartMessage;
import javafx.stage.Stage;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Value
@EqualsAndHashCode(callSuper = true)
public class StartUIMessage extends InitStartMessage {
  
  Stage stage;
  List<?> params;
  
  public StartUIMessage(Class<? extends IActor> actorClass, Stage stage) {
    this(null, actorClass, stage);
  }
  
  public StartUIMessage(Class<? extends IActor> actorClass, Stage stage, List<?> params) {
    this(null, actorClass, stage, params);
  }
  
  
  public StartUIMessage(UUID source, Class<? extends IActor> actorClass, Stage stage) {
    this(source, actorClass, stage, Collections.EMPTY_LIST);
  }
  
  public StartUIMessage(UUID source, Class<? extends IActor> actorClass, Stage stage, List<?> params) {
    super(source, actorClass);
    this.stage = stage;
    this.params = params;
  }
  
  @Override
  public Void init(UUID id, IActor actor) {
    return null;
  }
  
  @Override
  public Void answer(UUID id, IActor actor) {
    return null;
  }
  
  public String toString() {
    return String.format("StartUIMessage(payload=%s, stage=%s)", getPayload().getSimpleName(), stage);
  }
}
