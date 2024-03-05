package ca.mcrobotics.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import ca.mcrobotics.*;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Flywheel extends SubsystemBase {
    TalonSRX flywheelMotor, flywheelMotor2;
    ShuffleboardTab tab;
    GenericEntry mtrOut;
    double lastDataSendTime;
    
    public Flywheel() {
        flywheelMotor = new TalonSRX(Constants.Flywheel.FLYWHEEL1_CAN);
        flywheelMotor2 = new TalonSRX(Constants.Flywheel.FLYWHEEL2_CAN);
        flywheelMotor.setNeutralMode(null);
        flywheelMotor2.setNeutralMode(null);

        lastDataSendTime = Util.staggerUpdates();
        tab = Shuffleboard.getTab("Flywheel");
        mtrOut = Util.makeEntry(tab, "Flywheel", 0, 0, 1, 1);
    }

    public void configure() {
        flywheelMotor.configNominalOutputForward(0, 30);
        flywheelMotor.configNominalOutputReverse(0, 30);
        flywheelMotor.configPeakOutputForward(Constants.Flywheel.PEAK_SPEED, 30);
        flywheelMotor.configPeakOutputReverse(-Constants.Flywheel.PEAK_SPEED, 30);
        flywheelMotor.setSensorPhase(true);

        flywheelMotor2.configNominalOutputForward(0, 30);
        flywheelMotor2.configNominalOutputReverse(0, 30);
        flywheelMotor2.configPeakOutputForward(Constants.Flywheel.PEAK_SPEED, 30);
        flywheelMotor2.configPeakOutputReverse(-Constants.Flywheel.PEAK_SPEED, 30);
        flywheelMotor2.setSensorPhase(true);
    }

    public void stop() {
        flywheelMotor.set(ControlMode.Disabled, 0);
        flywheelMotor2.set(ControlMode.Disabled, 0);
    }

    public void move(double value) {
        flywheelMotor.set(ControlMode.PercentOutput, value);
        flywheelMotor2.set(ControlMode.PercentOutput, value);
    }

    public void setReversed(boolean rev) {
        flywheelMotor.setInverted(rev);
        flywheelMotor2.setInverted(rev);
    }

    @Override
    public void periodic() {
        if(Util.shouldUpdateShuffleboard(lastDataSendTime)) {
            lastDataSendTime = Util.getSeconds();
            
            Util.putEntry(mtrOut, flywheelMotor.getMotorOutputPercent());
            Util.putEntry(mtrOut, flywheelMotor2.getMotorOutputPercent());
        }
    }
}
