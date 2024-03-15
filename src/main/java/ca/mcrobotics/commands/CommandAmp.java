package ca.mcrobotics.commands;

import ca.mcrobotics.Constants;
import ca.mcrobotics.Robot;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class CommandAmp extends CommandBase {
	private Robot robot;
	private boolean stat;

	public CommandAmp(Robot robot, boolean stat) {
		this.robot = robot;
		this.stat = stat;
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		if (stat) {
			robot.s_amp.startAmp(Constants.Amp.MAX_SPEED_OUT);			
		} else if (!stat) {
			robot.s_amp.startAmp(-Constants.Amp.MAX_SPEED_IN);			
		}
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		robot.s_amp.stopAmp();

	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return false;
	}
}