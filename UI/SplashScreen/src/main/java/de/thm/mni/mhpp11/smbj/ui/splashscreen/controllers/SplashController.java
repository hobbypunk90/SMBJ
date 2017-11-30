package de.thm.mni.mhpp11.smbj.ui.splashscreen.controllers;

/**
 * Created by hobbypunk on 16.01.17.
 */

import de.thm.mni.mhpp11.smbj.ui.controllers.ActorController;
import de.thm.mni.mhpp11.smbj.ui.splashscreen.messages.InitSplashMessage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import lombok.Getter;

@Getter
public class SplashController extends ActorController {

  @FXML private ImageView background;
  
  @FXML private Label title;
  @FXML private Label subtitle;
  @FXML private Label version;
  @FXML private ProgressBar progress;
  
  
  public void initSplash() {
    InitSplashMessage.InitSplashMessageBuilder builder = InitSplashMessage.builder();
    builder.title("<title>").titleColor(Color.rgb(217, 209, 106));
    builder.subTitle("<subtitle>").subTitleColor(Color.rgb(216, 11, 44));
    builder.version("<version>").versionColor(Color.rgb(221, 195, 87));
    builder.progressColor(Color.rgb(216,11,44)).progressInfinity(true);
    builder.background(new Image(SplashController.class.getResourceAsStream("/de/thm/mni/mhpp11/smbj/ui/splashscreen/images/loading.png")));
  
    initSplash(builder.build());
  }
  
  public void initSplash(InitSplashMessage msg) {
    Platform.runLater(() -> {
      if (msg.getPayload() != null) title.setText(msg.getPayload());
      if (msg.getTitleColor() != null) title.setTextFill(msg.getTitleColor());
      if (msg.getSubTitle() != null) subtitle.setText(msg.getSubTitle());
      if (msg.getSubTitleColor() != null) subtitle.setTextFill(msg.getSubTitleColor());
      if (msg.getVersion() != null) version.setText(msg.getVersion());
      if (msg.getVersionColor() != null) version.setTextFill(msg.getVersionColor());
    
      if (msg.getProgressInfinity() != null) {
        progress.setProgress((msg.getProgressInfinity()) ? ProgressIndicator.INDETERMINATE_PROGRESS : 0.0);
      }
      if (msg.getProgressColor() != null) {
        progress.setStyle("-fx-accent: " + msg.getProgressColor().toString().replaceAll("^0x", "#"));
      }
    
      if (msg.getBackground() != null) {
        background.setImage(msg.getBackground());
      }
    });
  }
}
