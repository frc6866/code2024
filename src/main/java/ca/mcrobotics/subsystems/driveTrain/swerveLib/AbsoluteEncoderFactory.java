package ca.mcrobotics.subsystems.driveTrain.swerveLib;

@FunctionalInterface
public interface AbsoluteEncoderFactory<Configuration> {
    AbsoluteEncoder create(Configuration configuration);
}
