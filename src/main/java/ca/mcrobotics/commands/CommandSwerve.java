package ca.mcrobotics.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;
import ca.mcrobotics.Constants;
import ca.mcrobotics.subsystems.Swerve;
import ca.mcrobotics.ui.Controls;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class CommandSwerve extends CommandBase {
    private Swerve s_Swerve;
    private Double translationSup;
    private Double strafeSup;
    private Double rotationSup;
    //private BooleanSupplier robotCentricSup;

    private SlewRateLimiter translationLimiter = new SlewRateLimiter(3.0);
    private SlewRateLimiter strafeLimiter = new SlewRateLimiter(3.0);
    private SlewRateLimiter rotationLimiter = new SlewRateLimiter(3.0);

    public CommandSwerve(
            Swerve s_Swerve,
            Double translationSup,
            Double strafeSup,
            Double rotationSup) {
        this.s_Swerve = s_Swerve;
        addRequirements(s_Swerve);

        this.translationSup = translationSup;
        this.strafeSup = strafeSup;
        this.rotationSup = rotationSup;

    }

    @Override
    public void execute() {
        new RunCommand(
        () -> s_Swerve.drive(
          translationSup,
          strafeSup,
          rotationSup   ,
          true, true),
          s_Swerve);
    }
}
