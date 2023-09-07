// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.ctre.lights.CANdleSubsystem;

public class ShirtCannon extends SubsystemBase {
  private final Solenoid solenoid;
  private final TalonSRX talonSRX;
  private final DigitalInput sensor0;
  private final DigitalInput sensor1;
  private final AnalogPotentiometer analogPotentiometer;
  private final Compressor compressor;
  private final CANdleSubsystem caNdleSubsystem;

  private boolean shirtCannonState;
  private boolean check0 = false;
  private boolean check1 = false;

  private boolean lightsOn = true;

  public ShirtCannon() {
    solenoid = new Solenoid(1, PneumaticsModuleType.CTREPCM, 1);
    compressor = new Compressor(1, PneumaticsModuleType.CTREPCM);
    caNdleSubsystem = new CANdleSubsystem(13, 400);

    talonSRX = new TalonSRX(22);
    talonSRX.set(ControlMode.PercentOutput, 0);
    talonSRX.setNeutralMode(NeutralMode.Brake);

    sensor0 = new DigitalInput(0);
    sensor1 = new DigitalInput(1);

    analogPotentiometer = new AnalogPotentiometer(3, 360, -180);

    shirtCannonState = false;
    solenoid.set(shirtCannonState);
  }

  public void Shirt() {
    if (!this.compressor.enabled()) {
      if (shirtCannonState) {
        shirtCannonState = false;
      } else {
        shirtCannonState = true;
      }
      solenoid.set(shirtCannonState);

      Timer.delay(3);
      shirtCannonState = false;
      solenoid.set(shirtCannonState);
    }
  }

  public void CloseSolenoid() {
    shirtCannonState = false;
    solenoid.set(shirtCannonState);

  }

  public void Tilt(double normalizedOutput) {
    this.check0 = !sensor0.get() && normalizedOutput < 0;
    this.check1 = !sensor1.get() && normalizedOutput > 0;

    if (check0 || check1) {
      talonSRX.set(ControlMode.PercentOutput, 0);
    } else {
      talonSRX.set(ControlMode.PercentOutput, normalizedOutput);
    }
  }

  public void lightsOff() {
    this.lightsOn = !this.lightsOn;
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Sensor 0", !sensor0.get());
    SmartDashboard.putBoolean("Sensor 1", !sensor1.get());
    SmartDashboard.putNumber("Potentiometer Value", analogPotentiometer.get() * 6.85);
    SmartDashboard.putBoolean("Solenoid Fired", this.shirtCannonState);
    SmartDashboard.putBoolean("Compressor enables", this.compressor.enabled());
    SmartDashboard.putBoolean("PressureSwitchState", this.compressor.getPressureSwitchValue());

    if (this.lightsOn) {
      if (this.compressor.enabled()) {
        this.caNdleSubsystem.setChargingAnimation();
      } else {
        this.caNdleSubsystem.setReadyToFireAnimation();
      }
    }
    else{
      this.caNdleSubsystem.Off();
    }
  }

}
