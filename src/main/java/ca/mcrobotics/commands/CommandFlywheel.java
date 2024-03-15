package ca.mcrobotics.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import ca.mcrobotics.Robot;

public class CommandFlywheel extends CommandBase {
	private Robot robot;
	private double flywheelSpeed;
	private double transferSpeed;

	public CommandFlywheel(
			Robot robot,
			double flywheelSpeed,
			double transferSpeed) {
		this.robot = robot;
		this.flywheelSpeed = flywheelSpeed;
		this.transferSpeed = transferSpeed;
		execute();
	}

	public CommandFlywheel(
			Robot robot,
			double speed) {
		this.robot = robot;
		this.flywheelSpeed = speed;
		this.transferSpeed = speed*0.3 ;
		execute();
	}

	@Override
	public void execute() {
		robot.s_flywheel.moveFlywheel(flywheelSpeed);
		robot.s_flywheel.moveTransfer(transferSpeed);
	}
}
