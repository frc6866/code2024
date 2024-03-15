package ca.mcrobotics.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import ca.mcrobotics.*;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Amp extends SubsystemBase {
  private static VictorSPX ampMtr;
  private double speed;
  ShuffleboardTab tab;
  GenericEntry mtrOut;
  double lastDataSendTime;

  public Amp() {
    ampMtr = new VictorSPX(Constants.Amp.AMP_CAN);

    ampMtr.setNeutralMode(NeutralMode.Brake);

    // lastDataSendTime = Util.staggerUpdates();
    // tab = Shuffleboard.getTab("Drivetrain");
    // mtrOut = Util.makeEntry(tab, "Motor out", 0, 0, 1, 1);
  }

  public void configure() {
    if (Constants.Amp.MAX_SPEED_IN > Constants.Amp.MAX_SPEED_OUT) {
      speed = Constants.Amp.MAX_SPEED_IN;
    } else {
      speed = Constants.Amp.MAX_SPEED_OUT;      
    }
    ampMtr.configNominalOutputForward(0, 30);
    ampMtr.configNominalOutputReverse(0, 30);
    ampMtr.configPeakOutputForward(speed, 30);
    ampMtr.configPeakOutputReverse(-speed, 30);

  }

  public void stopAmp() {
    ampMtr.set(ControlMode.Disabled, 0);
  }

  public void startAmp(double speed) {
    ampMtr.set(ControlMode.PercentOutput, speed);
  }

  @Override
  public void periodic() {
    /*if (Util.shouldUpdateShuffleboard(lastDataSendTime)) {
      lastDataSendTime = Util.getSeconds();
      Util.putEntry(mtrOut, intake.getMotorOutputPercent());
    }*/
  }
}
