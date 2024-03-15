package ca.mcrobotics.commands.auton;

import ca.mcrobotics.Constants;
import ca.mcrobotics.Robot;
import ca.mcrobotics.commands.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand; //We do time based cus I'm too lazy to code PID based yay :D

public class TestAuton extends SequentialCommandGroup {
    public TestAuton(Robot robot) {
    }
}
