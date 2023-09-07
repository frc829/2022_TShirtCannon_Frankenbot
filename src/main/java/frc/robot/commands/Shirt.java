// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShirtCannon;

public class Shirt extends CommandBase {
  
  private final ShirtCannon shirtCannon;
  
  public Shirt(ShirtCannon shirtCannon) {
    this.shirtCannon = shirtCannon;
    addRequirements(shirtCannon);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    shirtCannon.Shirt();

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shirtCannon.CloseSolenoid();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
