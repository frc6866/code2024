package ca.mcrobotics.commands;

import ca.mcrobotics.Robot;
import ca.mcrobotics.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class CommandIntake extends CommandBase {
	private Robot robot;

	public CommandIntake(Robot robot) {
		this.robot = robot;
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		robot.s_intake.startIntake();
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		robot.s_intake.stopIntake();

	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return false;
	}
}
