package ca.mcrobotics.ui;

import java.util.Arrays;
import java.util.HashMap;

import ca.mcrobotics.Util;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;

public class AutonManager {
  private HashMap<String, Command> commands;

  public AutonManager() {
    commands = new HashMap<>();
  }

  public void register(String name, Command cmd) {
    commands.put(name, cmd);
  }

  public void register(Command cmd) {
    register(cmd.getClass().getSimpleName(), cmd);
  }

  public SendableChooser<String> createChooser(String def) {
    SendableChooser<String> chooser = new SendableChooser<>();
    for(String k : commands.keySet()) {
      chooser.addOption(k, k);
    }
    if(!commands.containsKey(def)) {
      throw new RuntimeException("Default auton option does not exist");
    }
    chooser.setDefaultOption(def, def);
    return chooser;
  }

  public Command getCommand(String name) {
    return commands.get(name);
  }
}
