package de.thm.mni.mhpp11.jActor.actors.ui.splash.messages;

import de.thm.mni.mhpp11.jActor.messages.interfaces.Message;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * Created by hobbypunk on 23.01.17.
 */

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SplashInitMessage extends Message {
  String title;
  Color titleColor;
  String subTitle;
  Color subTitleColor;
  
  String version;
  Color versionColor;
  
  Boolean progressInfinity;
  Color progressColor;
  
  Image background;
  
  @Builder
  public SplashInitMessage(@NonNull UUID group, @NonNull Integer to, String title, Color titleColor, String subTitle, Color subTitleColor, String version, Color versionColor, Boolean progressInfinity, Color progressColor, Image background) {
    super(group, to);
    this.title = title;
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
