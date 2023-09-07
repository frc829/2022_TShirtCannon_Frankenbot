// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.MecanumDriveSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends CommandBase {
  
  private final MecanumDriveSubsystem mecanumDriveSubsystem;
  private final DoubleSupplier x;
  private final DoubleSupplier y;
  private final DoubleSupplier theta;
  private ChassisSpeeds chassisSpeeds;
  

  public Drive(MecanumDriveSubsystem mecanumDriveSubsystem, DoubleSupplier x, DoubleSupplier y, DoubleSupplier theta) {
    this.mecanumDriveSubsystem = mecanumDriveSubsystem;
    this.x = x;
    this.y = y;
    this.theta = theta;
    chassisSpeeds = new ChassisSpeeds();
    addRequirements(mecanumDriveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {


    double xSpeed = -x.getAsDouble();
    double ySpeed = -y.getAsDouble();
    double rotSpeed = -theta.getAsDouble();

    if(Math.abs(xSpeed) >= 0.1){
      xSpeed = Math.signum(xSpeed) * (Math.abs(xSpeed) - 0.1)/0.9;
    }
    else{
      xSpeed = 0;
    }

    if(Math.abs(ySpeed) >= 0.1){
      ySpeed = Math.signum(ySpeed) * (Math.abs(ySpeed) - 0.1)/0.9;
    }
    else{
      ySpeed = 0;
    }

    if(Math.abs(rotSpeed) >= 0.1){
      rotSpeed = Math.signum(rotSpeed) * (Math.abs(rotSpeed) - 0.1)/0.9;
    }
    else{
      rotSpeed = 0;
    }

    xSpeed *= 0.5;
    ySpeed *= 0.5;
    rotSpeed *= 0.01;

    SmartDashboard.putNumber("xSpeed", xSpeed);
    SmartDashboard.putNumber("ySpeed", ySpeed);
    SmartDashboard.putNumber("rotSpeed", rotSpeed);



    chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rotSpeed, new Rotation2d(0)); 
    MecanumDriveWheelSpeeds mecanumDriveWheelSpeeds = mecanumDriveSubsystem.getKinematics().toWheelSpeeds(chassisSpeeds);
    mecanumDriveSubsystem.setWheels(mecanumDriveWheelSpeeds);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
