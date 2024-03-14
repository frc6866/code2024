package ca.mcrobotics.commands.auton;

import ca.mcrobotics.Robot;
import ca.mcrobotics.commands.CommandSwerve;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand; //We do time based cus I'm too lazy to code PID based yay :D

public class AutonCenter extends SequentialCommandGroup {
    public AutonCenter(Robot robot) {
        addCommands(
            new ParallelRaceGroup(new CommandSwerve(robot, () -> 1.0, () -> 1.0, () -> 1.0, () -> false), new WaitCommand(2)), //Taxi
            new CommandSwerve(robot, () -> 0.0, () -> 0.0, () -> 0.0, () -> false)
        );
    }
}
