package de.thm.mni.mhpp11.jActor.actors.ui.splash;

/**
 * Created by hobbypunk on 16.01.17.
 */

import de.thm.mni.mhpp11.jActor.actors.messagebus.messages.RegisterMessage;
import de.thm.mni.mhpp11.jActor.actors.ui.interfaces.AbstractUIActor;
import de.thm.mni.mhpp11.jActor.actors.ui.splash.messages.SplashInitMessage;
import de.thm.mni.mhpp11.jActor.actors.utilities.Constants;
import de.thm.mni.mhpp11.jActor.actors.interfaces.IActor;
import de.thm.mni.mhpp11.jActor.actors.ui.interfaces.IActorController;
import de.thm.mni.mhpp11.jActor.actors.ui.splash.messages.SplashShowMessage;
import de.thm.mni.mhpp11.jActor.messages.interfaces.Message;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;

import java.io.IOException;
import java.util.UUID;

@Getter
public class SplashController extends Application implements IActorController {
  private Stage stage;
  private IActor actor;
  
  @FXML private ImageView background;
  
  @FXML private Label title;
  @FXML private Label subtitle;
  @FXML private Label version;
  @FXML private ProgressBar progress;
  
  
  @Override
  public UUID getGroup() {
    return Constants.GLOBALGROUP;
  }
  
  @Override
  public void onClose() {}
  
  @Override
  public void start(Stage primaryStage) throws IOException {
    this.stage = primaryStage;
    primaryStage.initStyle(StageStyle.UNDECORATED);
    
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(SplashController.class.getResource("/de/thm/mni/mhpp11/jActor/view/SplashScreen.fxml"));
    loader.setController(this);
    Pane root = loader.load();
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.setAlwaysOnTop(true);
    primaryStage.centerOnScreen();
    primaryStage.setResizable(false);
    
    actor = new SplashActor(this).onStart();
    initSplash();
    actor.getBus().send(new RegisterMessage(getGroup(), actor));
  }
  
  private void initSplash() {
    SplashInitMessage.SplashInitMessageBuilder builder = SplashInitMessage.builder();
    builder.group(Constants.GLOBALGROUP).to(Constants.UI);
    builder.title("<title>").titleColor(Color.rgb(217, 209, 106));
    builder.subTitle("<subtitle>").subTitleColor(Color.rgb(216, 11, 44));
    builder.version("<version>").versionColor(Color.rgb(221, 195, 87));
    builder.progressColor(Color.rgb(216,11,44)).progressInfinity(true);
    builder.background(new Image(SplashController.class.getResourceAsStream("/de/thm/mni/mhpp11/jActor/images/loading.png")));
    
    initSplash(builder.build());
  }
  
  private void initSplash(SplashInitMessage msg) {
    Platform.runLater(() -> {
      if (msg.getTitle() != null) title.setText(msg.getTitle());
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
  
  private static class SplashActor extends AbstractUIActor<SplashController, Message> {
    
    @Override
    public Integer getID() {
      return Constants.UI;
    }
  
    @Override
    public UUID getGroup() {
      return getController().getGroup();
    }
  
    private SplashActor(SplashController controller) {
      super(controller);
    }
    
    private void updateValue(SplashShowMessage value) {
      super.updateValue(value);
      if (value != null) {
        Platform.runLater(() -> {
          if (value.getShowSplash()) {
            this.getStage().show();
          } else {
            this.getStage().hide();
          }
        });
      }
    }
    
    @Override
    public void executeUI(Message msg) {
      if(msg instanceof SplashInitMessage) {
        (getController()).initSplash((SplashInitMessage) msg);
      } else if (msg instanceof SplashShowMessage) {
        updateValue((SplashShowMessage) msg);
      }
    }
  }
}
