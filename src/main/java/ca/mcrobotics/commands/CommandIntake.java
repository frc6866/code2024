package ca.mcrobotics.commands;

import ca.mcrobotics.Robot;
import ca.mcrobotics.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class CommandIntake extends CommandBase {
	private Intake intake;

	public CommandIntake(Intake intake) {
		this.intake = intake;
		addRequirements(intake);

	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		intake.Suck();
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		intake.StopSuck();

	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return false;
	}
}
