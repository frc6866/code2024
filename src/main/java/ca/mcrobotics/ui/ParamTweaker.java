package ca.mcrobotics.ui;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.TreeMap;

import ca.mcrobotics.Util;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class ParamTweaker {
  private double lastDataSendTime;
  private TreeMap<String, Field> parameterHolders;
  private TreeMap<String, GenericEntry> shuffleboardEntries;
  private ShuffleboardTab tab;

  private static boolean fieldSet(Field field, GenericEntry entry) throws IllegalArgumentException, IllegalAccessException {
    if(field.getType() == Double.TYPE) {
      field.set(null, entry.getDouble((Double)field.get(null)));
    } else if(field.getType() == Integer.TYPE) {
      field.set(null, entry.getDouble((Integer)field.get(null)));
    } else if(field.getType() == String.class) {
      field.set(null, entry.getString((String)field.get(null)));
    } else {
      return false;
    }
    return true;
  }

  private static boolean fieldGet(Field field, GenericEntry entry) throws IllegalArgumentException, IllegalAccessException {
    Util.putEntry(entry, field.get(null));
    return true;
  }

  public ParamTweaker() {
    tab = Shuffleboard.getTab("ParamTweaker");
    parameterHolders = new TreeMap<>();
    shuffleboardEntries = new TreeMap<>();
    lastDataSendTime = Util.staggerUpdates();
  }

  public void addParameterHolder(Class holder) throws IllegalAccessException {
    for(Field field : holder.getDeclaredFields()) {
      if(!field.canAccess(null)) {
        field.setAccessible(true); 
      }
      if(Modifier.isStatic(field.getModifiers())) {
        String combinedName = holder.getSimpleName() + "." + field.getName();
        GenericEntry entry = tab.add(combinedName, field.get(null)).withSize(2, 1).getEntry();
        if(!fieldGet(field, entry)) {
          Util.logErr(this, "Unsupported field type: " + field.getType().getName());
        }
        parameterHolders.put(combinedName, field);
        shuffleboardEntries.put(combinedName, entry);
      }
    }
  }

  public void periodic() {
    try {
      if(Util.shouldUpdateShuffleboard(lastDataSendTime)) {
        lastDataSendTime = Util.getSeconds();
  
        for(String k : parameterHolders.keySet()) {
          Field field = parameterHolders.get(k);
          GenericEntry entry = shuffleboardEntries.get(k);
          if(!fieldSet(field, entry)) {
            Util.logErr(this, "Unsupported field type: " + field.getType().getName());
          }
        }
      }
    } catch(IllegalAccessException err) {
      Util.logErr(this, "Periodic failed");
    }
  }
}