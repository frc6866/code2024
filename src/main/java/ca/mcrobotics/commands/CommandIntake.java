package ca.mcrobotics.commands;

import com.fasterxml.jackson.databind.ser.std.SqlTimeSerializer;

import ca.mcrobotics.Constants;
import ca.mcrobotics.Robot;
import ca.mcrobotics.subsystems.Amp;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class CommandIntake extends CommandBase {
	private Robot robot;
	private boolean stat;

	public CommandIntake(Robot robot, boolean stat) {
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
			robot.s_amp.startAmp(Constants.Amp.MAX_SPEED);			
		} else if (!stat) {
			robot.s_amp.startAmp(-Constants.Amp.MAX_SPEED);			
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
