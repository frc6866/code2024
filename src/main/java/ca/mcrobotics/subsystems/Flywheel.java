package ca.mcrobotics.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import ca.mcrobotics.*;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Flywheel extends SubsystemBase {
    TalonSRX transferMotor;
    TalonSRX flywheelMotor1;
    TalonSRX flywheelMotor2;
    ShuffleboardTab tab;
    GenericEntry mtrOut1;
    GenericEntry mtrOut2;
    GenericEntry mtrOut3;
    double lastDataSendTime;
    
    public Flywheel() {
        transferMotor = new TalonSRX(Constants.Flywheel.TRANSFER_CAN);
        flywheelMotor1 = new TalonSRX(Constants.Flywheel.FLYWHEEL1_CAN);
        flywheelMotor2 = new TalonSRX(Constants.Flywheel.FLYWHEEL2_CAN);
        transferMotor.setNeutralMode(NeutralMode.Coast);
        flywheelMotor1.setNeutralMode(NeutralMode.Coast);
        flywheelMotor2.setNeutralMode(NeutralMode.Coast);

        lastDataSendTime = Util.staggerUpdates();
        tab = Shuffleboard.getTab("Flywheel");
        mtrOut1 = Util.makeEntry(tab, "Flywheel 1", 0, 0, 1, 1);
        mtrOut2 = Util.makeEntry(tab, "Flywheel 2", 1, 0, 1, 1);
        mtrOut3 = Util.makeEntry(tab, "Flywheel 3", 2, 0, 1, 1);
    }

    public void configure() {
        transferMotor.configNominalOutputForward(0, 30);
        transferMotor.configNominalOutputReverse(0, 30);
        transferMotor.configPeakOutputForward(Constants.Flywheel.PEAK_SPEED_TRANSFER, 30);
        transferMotor.configPeakOutputReverse(-Constants.Flywheel.PEAK_SPEED_TRANSFER, 30);

        flywheelMotor1.setSensorPhase(true);
        flywheelMotor1.configNominalOutputForward(0, 30);
        flywheelMotor1.configNominalOutputReverse(0, 30);
        flywheelMotor1.configPeakOutputForward(Constants.Flywheel.PEAK_SPEED_FLYWHEEL, 30);
        flywheelMotor1.configPeakOutputReverse(-Constants.Flywheel.PEAK_SPEED_FLYWHEEL, 30);

        flywheelMotor2.setSensorPhase(true);
        flywheelMotor2.configNominalOutputForward(0, 30);
        flywheelMotor2.configNominalOutputReverse(0, 30);
        flywheelMotor2.configPeakOutputForward(Constants.Flywheel.PEAK_SPEED_FLYWHEEL, 30);
        flywheelMotor2.configPeakOutputReverse(-Constants.Flywheel.PEAK_SPEED_FLYWHEEL, 30);
    }

    public void stopAll() {
        transferMotor.set(ControlMode.Disabled, 0);
        flywheelMotor1.set(ControlMode.Disabled, 0);
        flywheelMotor2.set(ControlMode.Disabled, 0);
    }

    public void stopTransfer() {
        transferMotor.set(ControlMode.Disabled, 0);
    }

    public void stopFlywheel() {
        flywheelMotor1.set(ControlMode.Disabled, 0);
        flywheelMotor2.set(ControlMode.Disabled, 0);
    }

    public void moveFlywheel(double value) {
       flywheelMotor1.set(ControlMode.PercentOutput, value);
       flywheelMotor2.set(ControlMode.PercentOutput, value*-1);
    }

    public void moveTransfer(double value) {
        transferMotor.set(ControlMode.PercentOutput, value);
    }

    public void setReversed(boolean rev) {
        transferMotor.setInverted(rev);
        flywheelMotor1.setInverted(rev);
        flywheelMotor2.setInverted(!rev);
    }

    @Override
    public void periodic() {
        if(Util.shouldUpdateShuffleboard(lastDataSendTime)) {
            lastDataSendTime = Util.getSeconds();
            
            Util.putEntry(mtrOut1, transferMotor.getMotorOutputPercent());
            Util.putEntry(mtrOut2, flywheelMotor1.getMotorOutputPercent());
            Util.putEntry(mtrOut2, flywheelMotor2.getMotorOutputPercent());
        }
    }
}
