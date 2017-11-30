package de.thm.mni.mhpp11.smbj.ui.splashscreen.messages;

import de.thm.mni.mhpp11.smbj.messages.base.tell.TellUUIDMessage;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * Created by hobbypunk on 23.01.17.
 */

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InitSplashMessage extends TellUUIDMessage<String> {
  Color titleColor;
  String subTitle;
  Color subTitleColor;
  
  String version;
  Color versionColor;
  
  Boolean progressInfinity;
  Color progressColor;
  
  Image background;
  
  @Builder
  public InitSplashMessage(UUID source, UUID sink, String title, Color titleColor, String subTitle, Color subTitleColor, String version, Color versionColor, Boolean progressInfinity, Color progressColor, Image background) {
    super(source, sink, title);
    this.titleColor = titleColor;
    this.subTitle = subTitle;
    this.subTitleColor = subTitleColor;
    this.version = version;
    this.versionColor = versionColor;
    this.progressInfinity = progressInfinity;
    this.progressColor = progressColor;
    this.background = background;
  }
}
