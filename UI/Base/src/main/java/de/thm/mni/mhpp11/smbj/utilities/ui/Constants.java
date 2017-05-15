package de.thm.mni.mhpp11.smbj.utilities.ui;

/**
 * Created by hobbypunk on 18.01.17.
 */

public class Constants extends de.thm.mni.mhpp11.smbj.utilities.Constants {
  
  static {
    Constants.setConstants(Constants.class);
  }
  
  public final static Integer UICREATOR = 1 << 10;
  public final static Integer UISPLASH = 1 << 11;
  public final static Integer UI = 1 << 12;
  
  public final static Integer ALL = de.thm.mni.mhpp11.smbj.utilities.Constants.ALL | UICREATOR | UISPLASH | UI;
  
  public Constants() {
    throw new UnsupportedOperationException();
  }
}
