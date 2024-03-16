package ca.mcrobotics.commands.auton;

import ca.mcrobotics.Constants;
import ca.mcrobotics.Robot;
import ca.mcrobotics.commands.*;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand; //We do time based cus I'm too lazy to code PID based yay :D

public class AutonCenter extends SequentialCommandGroup {
    public AutonCenter(Robot robot) {
        addCommands(
            new ParallelRaceGroup(new CommandDrive(robot, 2, 2), new WaitCommand(1)),
            // Go forward for 1 second
            new CommandDrive(robot, 0, 0)
            // Speed 0
        );
    }
}
