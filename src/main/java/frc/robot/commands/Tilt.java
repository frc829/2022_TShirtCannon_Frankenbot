// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShirtCannon;

public class Tilt extends CommandBase {
  
  private final ShirtCannon shirtCannon;
  private final DoubleSupplier leftTrigger;
  private final DoubleSupplier rightTrigger;

  public Tilt(ShirtCannon shirtCannon, DoubleSupplier leftBumper, DoubleSupplier rightBumper) {

    this.shirtCannon = shirtCannon;
    this.leftTrigger = leftBumper;
    this.rightTrigger = rightBumper;
    addRequirements(shirtCannon);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double up = leftTrigger.getAsDouble();
    double down = rightTrigger.getAsDouble();

    double normalizedOutput = up - down;
    normalizedOutput *= 0.5;

    shirtCannon.Tilt(normalizedOutput);


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
