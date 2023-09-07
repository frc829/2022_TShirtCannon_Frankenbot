// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MecanumDriveSubsystem extends SubsystemBase {
  
  private final MecanumDriveKinematics mecanumDriveKinematics;
  private final TalonSRX talonSRX0;
  private final TalonSRX talonSRX1;
  private final TalonSRX talonSRX2;
  private final TalonSRX talonSRX3;

  public MecanumDriveSubsystem() {
    
    talonSRX0 = new TalonSRX(10);
    talonSRX1 = new TalonSRX(11);
    talonSRX2 = new TalonSRX(12);
    talonSRX3 = new TalonSRX(13);
    talonSRX0.set(ControlMode.PercentOutput, 0);
    talonSRX1.set(ControlMode.PercentOutput, 0);
    talonSRX2.set(ControlMode.PercentOutput, 0);
    talonSRX3.set(ControlMode.PercentOutput, 0);

    talonSRX0.setNeutralMode(NeutralMode.Brake);
    talonSRX1.setNeutralMode(NeutralMode.Brake);

    talonSRX2.setNeutralMode(NeutralMode.Brake);

    talonSRX3.setNeutralMode(NeutralMode.Brake);


    
    Translation2d frontLeftWheelMeters = new Translation2d(24.5, 28.5);
    Translation2d frontRightWheelMeters = new Translation2d(24.5, -28.5);
    Translation2d rearLeftWheelMeters = new Translation2d(-24.5, 28.5);
    Translation2d rearRightWheelMeters = new Translation2d(-24.5, -28.5);

    mecanumDriveKinematics = new MecanumDriveKinematics(frontLeftWheelMeters, frontRightWheelMeters, rearLeftWheelMeters, rearRightWheelMeters);            
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public MecanumDriveKinematics getKinematics(){
    return this.mecanumDriveKinematics;
  }

  public void setWheels(MecanumDriveWheelSpeeds mecanumDriveWheelSpeeds){

    double speed0 = mecanumDriveWheelSpeeds.frontLeftMetersPerSecond;
    double speed1= mecanumDriveWheelSpeeds.frontRightMetersPerSecond;
    double speed2 = mecanumDriveWheelSpeeds.rearLeftMetersPerSecond;
    double speed3 = mecanumDriveWheelSpeeds.rearRightMetersPerSecond;

    


    this.talonSRX0.set(ControlMode.PercentOutput, speed0);
    this.talonSRX1.set(ControlMode.PercentOutput, -speed1);
    this.talonSRX2.set(ControlMode.PercentOutput, speed2);
    this.talonSRX3.set(ControlMode.PercentOutput, -speed3);

    
  }

}
