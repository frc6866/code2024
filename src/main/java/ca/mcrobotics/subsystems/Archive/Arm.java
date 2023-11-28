/*package ca.mcrobotics.subsystems.Archive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import ca.mcrobotics.Constants.Arm;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {
  VictorSPX armMotorLeft;
  VictorSPX armMotorRight;
  Encoder encoder;

  public Arm() {
    armMotorLeft = new VictorSPX(Arm.MOTOR_LEFT_CAN);
    armMotorRight = new VictorSPX(Arm.MOTOR_RIGHT_CAN);
    encoder = new Encoder(new DigitalInput(Arm.ENCODER_A), new DigitalInput(Arm.ENCODER_B));
    encoder.reset();
  }

  public void configure() {
    armMotorLeft.configFactoryDefault();
    armMotorRight.configFactoryDefault();
  }

  public void setPosition(double newPosition) {

    double currentPosition = readEncoder();

    armMotorLeft.setNeutralMode(NeutralMode.Brake);
    armMotorRight.setNeutralMode(NeutralMode.Brake);

    double err = newPosition - currentPosition/Arm.HIGH_LIMIT;

    // error is the amount we want to be higher than the current position
    if (err > 0) {
      armMotorLeft.set(ControlMode.PercentOutput, -err * Arm.PID_K);
      armMotorRight.set(ControlMode.PercentOutput, -err * Arm.PID_K);
    } else {
      armMotorLeft.set(ControlMode.PercentOutput, 0);
      armMotorRight.set(ControlMode.PercentOutput, 0);
    }
  }

  @Override
  public void periodic() {
    double currentPosition = readEncoder();
  }

  public void encoderReset() {
    encoder.reset();
  }

  public void stop() {
    armMotorLeft.set(ControlMode.Disabled, 0);
    armMotorRight.set(ControlMode.Disabled, 0);
  }

  private double readEncoder() {
    return -encoder.getRaw();
  }
}
*/