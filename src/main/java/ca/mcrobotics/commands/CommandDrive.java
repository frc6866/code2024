package ca.mcrobotics.commands;

import java.util.function.Supplier;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.CommandBase;
import ca.mcrobotics.Robot;
import ca.mcrobotics.Constants;
import ca.mcrobotics.Constants.*;

public class CommandDrive extends CommandBase {

    private Robot robot;
    private final double speedL;
    private final double speedR;

    public CommandDrive(Robot robot, double speedL, double speedR) {
        this.robot = robot;
        this.speedL = speedL;
        this.speedR = speedR;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        robot.s_swerve.drive(speedL, speedR, 0, 0);
        double pos[] = robot.s_swerve.getPos();
        Constants.Drive.flAng = pos[0];
        Constants.Drive.frAng = pos[1];
        Constants.Drive.blAng = pos[2];
        Constants.Drive.brAng = pos[3];    
    }

    @Override
    public void end(boolean interrupted) {
        robot.s_swerve.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
