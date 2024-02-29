package ca.mcrobotics.commands.auton;

import ca.mcrobotics.Constants;
import ca.mcrobotics.Robot;
import ca.mcrobotics.commands.*;
import ca.mcrobotics.subsystems.Swerve;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand; //We do time based cus I'm too lazy to code PID based yay :D

public class NoAuton extends SequentialCommandGroup {
    public NoAuton(Robot robot, Swerve swerve) {
        addCommands(
            new CommandSwerve(swerve, 
                              50.0, 
                              0.0,
                              0.0)
        );
    }
}
