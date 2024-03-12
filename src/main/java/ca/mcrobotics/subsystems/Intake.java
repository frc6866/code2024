package ca.mcrobotics.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import ca.mcrobotics.*;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  private static TalonSRX intake;
  private static double INTAKE_SPEED = 0.5;
  ShuffleboardTab tab;
  GenericEntry mtrOut;
  double lastDataSendTime;

  public Intake() {
    intake = new TalonSRX(Constants.Intake.INTAKE1_CAN);

    intake.setNeutralMode(NeutralMode.Brake);

    // lastDataSendTime = Util.staggerUpdates();
    // tab = Shuffleboard.getTab("Drivetrain");
    // mtrOut = Util.makeEntry(tab, "Motor out", 0, 0, 1, 1);
  }

  public void configure() {
    intake.configNominalOutputForward(0, 30);
    intake.configNominalOutputReverse(0, 30);
    intake.configPeakOutputForward(Constants.Intake.MAX_SPEED, 30);
    intake.configPeakOutputReverse(-Constants.Intake.MAX_SPEED, 30);

  }

  public void stopIntake() {
    intake.set(ControlMode.Disabled, 0);
  }

  public void startIntake() {
    intake.set(ControlMode.PercentOutput, INTAKE_SPEED);
  }

  @Override
  public void periodic() {
    /*if (Util.shouldUpdateShuffleboard(lastDataSendTime)) {
      lastDataSendTime = Util.getSeconds();
      Util.putEntry(mtrOut, intake.getMotorOutputPercent());
    }*/
  }
}
