package de.thm.mni.mhpp11.smbj.ui.splashscreen.messages;

import de.thm.mni.mhpp11.smbj.ui.messages.ShowStageMessage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * Created by hobbypunk on 23.01.17.
 */

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HideSplashMessage extends ShowStageMessage {
  public HideSplashMessage(UUID source) {
    super(source, null);
  }
}
