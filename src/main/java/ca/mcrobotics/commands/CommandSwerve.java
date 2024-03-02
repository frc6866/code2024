package ca.mcrobotics.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import ca.mcrobotics.Constants;
import ca.mcrobotics.subsystems.Swerve;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class CommandSwerve extends CommandBase {
    //private Swerve s_Swerve;
    private DoubleSupplier translationSup;
    private DoubleSupplier strafeSup;
    private DoubleSupplier rotationSup;
    //private BooleanSupplier robotCentricSup;

    private SlewRateLimiter translationLimiter = new SlewRateLimiter(3.0);
    private SlewRateLimiter strafeLimiter = new SlewRateLimiter(3.0);
    private SlewRateLimiter rotationLimiter = new SlewRateLimiter(3.0);

    public CommandSwerve(
            Swerve s_Swerve,
            DoubleSupplier translationSup,
            DoubleSupplier strafeSup,
            DoubleSupplier rotationSup) {
        //this.s_Swerve = s_Swerve;
        addRequirements(s_Swerve);

        this.translationSup = translationSup;
        this.strafeSup = strafeSup;
        this.rotationSup = rotationSup;
        //this.robotCentricSup = robotCentricSup;
    }

    @Override
    public void execute() {
        /* Get Values, Deadband */
        double translationVal = translationLimiter.calculate(
                MathUtil.applyDeadband(translationSup.getAsDouble(), Constants.Drive.stickDeadband));
        double strafeVal = strafeLimiter.calculate(
                MathUtil.applyDeadband(strafeSup.getAsDouble(), Constants.Drive.stickDeadband));
        double rotationVal = rotationLimiter.calculate(
                MathUtil.applyDeadband(rotationSup.getAsDouble(), Constants.Drive.stickDeadband));

        /* Drive */
        /**/
    }
}
