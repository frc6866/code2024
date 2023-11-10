package ca.mcrobotics.subsystems.Archive;

import ca.mcrobotics.Constants.CLAMP;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Clamp extends SubsystemBase {

  // pneumatics
  Compressor compressor;
  DoubleSolenoid solenoid;
  double lastDataSendTime;

  public Clamp() {
    compressor = new Compressor(1, CLAMP.PCM_TYPE);
    solenoid = new DoubleSolenoid(CLAMP.PCM_TYPE, CLAMP.SOLENOID_FORW, CLAMP.SOLENOID_REV);
    solenoid.set(DoubleSolenoid.Value.kForward);
    startCompressor();
  }

  public void startCompressor() {
    compressor.enableDigital();
  }

  public void stopCompressor() {
    compressor.disable();
  }

  public void toggleClamp() {
    solenoid.toggle();
  }

  @Override
  public void periodic() {
  }

  public void stop() {
    solenoid.set(DoubleSolenoid.Value.kOff);
  }
}
