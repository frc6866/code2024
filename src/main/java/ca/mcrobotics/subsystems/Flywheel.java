package ca.mcrobotics.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import ca.mcrobotics.*;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Flywheel extends SubsystemBase {
    TalonSRX flywheelMotor;
    TalonSRX transferMtr1;
    TalonSRX transferMtr2;
    ShuffleboardTab tab;
    GenericEntry mtrOut1;
    GenericEntry mtrOut2;
    GenericEntry mtrOut3;
    double lastDataSendTime;
    
    public Flywheel() {
        flywheelMotor = new TalonSRX(Constants.Flywheel.FLYWHEEL1_CAN);
        transferMtr1 = new TalonSRX(Constants.Flywheel.TRANSFER1_CAN);
        transferMtr2 = new TalonSRX(Constants.Flywheel.TRANSFER2_CAN);
        flywheelMotor.setNeutralMode(NeutralMode.Coast);
        transferMtr1.setNeutralMode(NeutralMode.Coast);
        transferMtr2.setNeutralMode(NeutralMode.Coast);

        lastDataSendTime = Util.staggerUpdates();
        tab = Shuffleboard.getTab("Flywheel");
        mtrOut1 = Util.makeEntry(tab, "Flywheel 1", 0, 0, 1, 1);
        mtrOut2 = Util.makeEntry(tab, "Flywheel 2", 1, 0, 1, 1);
        mtrOut3 = Util.makeEntry(tab, "Flywheel 3", 2, 0, 1, 1);
    }

    public void configure() {
        flywheelMotor.configNominalOutputForward(0, 30);
        flywheelMotor.configNominalOutputReverse(0, 30);
        flywheelMotor.configPeakOutputForward(Constants.Flywheel.PEAK_SPEED_FLYWHEEL, 30);
        flywheelMotor.configPeakOutputReverse(-Constants.Flywheel.PEAK_SPEED_FLYWHEEL, 30);
        flywheelMotor.setSensorPhase(true);

        transferMtr1.configNominalOutputForward(0, 30);
        transferMtr1.configNominalOutputReverse(0, 30);
        transferMtr1.configPeakOutputForward(Constants.Flywheel.PEAK_SPEED_TRANSFER, 30);
        transferMtr1.configPeakOutputReverse(-Constants.Flywheel.PEAK_SPEED_TRANSFER, 30);

        transferMtr2.setSensorPhase(true);
        transferMtr2.configNominalOutputForward(0, 30);
        transferMtr2.configNominalOutputReverse(0, 30);
        transferMtr2.configPeakOutputForward(Constants.Flywheel.PEAK_SPEED_TRANSFER, 30);
        transferMtr2.configPeakOutputReverse(-Constants.Flywheel.PEAK_SPEED_TRANSFER, 30);
        transferMtr2.setSensorPhase(true);
    }

    public void stopAll() {
        flywheelMotor.set(ControlMode.Disabled, 0);
        transferMtr1.set(ControlMode.Disabled, 0);
        transferMtr2.set(ControlMode.Disabled, 0);
    }

    public void stopFlywheel() {
        flywheelMotor.set(ControlMode.Disabled, 0);
    }

    public void stopTransfer() {
        transferMtr1.set(ControlMode.Disabled, 0);
        transferMtr2.set(ControlMode.Disabled, 0);
    }

    public void moveFlywheel(double value) {
        flywheelMotor.set(ControlMode.PercentOutput, value);
    }

    public void moveTransfer(double value) {
        transferMtr1.set(ControlMode.PercentOutput, value);
        transferMtr2.set(ControlMode.PercentOutput, value);
    }

    public void setReversed(boolean rev) {
        flywheelMotor.setInverted(rev);
        transferMtr1.setInverted(rev);
        transferMtr2.setInverted(rev);
    }

    @Override
    public void periodic() {
        if(Util.shouldUpdateShuffleboard(lastDataSendTime)) {
            lastDataSendTime = Util.getSeconds();
            
            Util.putEntry(mtrOut1, flywheelMotor.getMotorOutputPercent());
            Util.putEntry(mtrOut2, transferMtr1.getMotorOutputPercent());
            Util.putEntry(mtrOut2, transferMtr2.getMotorOutputPercent());
        }
    }
}
