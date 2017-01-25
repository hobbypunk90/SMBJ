package de.thm.mni.mhpp11.jActor.actors.ui.splash.messages;

import de.thm.mni.mhpp11.jActor.actors.utilities.Constants;
import de.thm.mni.mhpp11.jActor.messages.interfaces.Message;
import lombok.Getter;

/**
 * Created by hobbypunk on 18.01.17.
 */
@Getter
public class SplashShowMessage extends Message {
  
  private final Boolean showSplash;
  
  public SplashShowMessage(Boolean showSplash) {
    super(Constants.GLOBALGROUP, Constants.UI);
    this.showSplash = showSplash;
  }
}
