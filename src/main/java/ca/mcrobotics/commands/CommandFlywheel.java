package ca.mcrobotics.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import ca.mcrobotics.Constants;
import ca.mcrobotics.Robot;
import ca.mcrobotics.subsystems.Swerve;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class CommandFlywheel extends CommandBase {
	private Robot robot;
	private double speed;

	public CommandFlywheel(
			Robot robot,
			double speed) {
		this.robot = robot;
		speed = speed;
		execute();
	}

	@Override
	public void execute() {
		/* Drive */
		robot.s_flywheel.move(speed);
		/**/
	}
}
