package de.thm.mni.mhpp11.jActor.actors.utilities;

import lombok.Setter;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * Created by hobbypunk on 18.01.17.
 */

public class Constants {

  @Setter private static Class<? extends Constants> constants = Constants.class;
  
  public final static UUID GLOBALGROUP = UUID.fromString("00000000-0000-0000-0000-000000000001");
  public final static UUID ALLGROUPS = UUID.fromString("00000000-0000-0000-0000-000000000000");
  
  public final static Integer NONE = 0;
  public final static Integer BUS = 1;
  public final static Integer RETRYER = 1 << 1;
  
  public final static Integer LOGGER = 1 << 2;
  public final static Integer UICREATOR = 1 << 3;
  public final static Integer UI = 1 << 4;
  
  public final static Integer ALL = LOGGER | UICREATOR | UI;
  
  public Constants() {
    throw new UnsupportedOperationException();
  }
  
  public static UUID getGroup(String name) throws NoSuchFieldException, IllegalAccessException {
    Field f = constants.getField(name);
    if( f.getType() == UUID.class)
      return (UUID)f.get(null);
    else throw new NoSuchFieldException();
  }
  
  public static Integer getId(String name) {
    try {
      Field f = constants.getField(name);
      if (f.getType() == Integer.class)
        return (Integer) f.get(null);
      else throw new NoSuchFieldException();
    } catch (Exception e) {
      e.printStackTrace();
      return Integer.MAX_VALUE;
    }
  }
}
