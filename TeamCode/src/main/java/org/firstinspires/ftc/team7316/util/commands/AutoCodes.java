package org.firstinspires.ftc.team7316.util.commands;

import org.firstinspires.ftc.team7316.commands.CameraUntilCheddar;
import org.firstinspires.ftc.team7316.commands.ClimbForPosition;
import org.firstinspires.ftc.team7316.commands.DriveAngle;
import org.firstinspires.ftc.team7316.commands.DriveDistance;
import org.firstinspires.ftc.team7316.commands.MovePlateServo;
import org.firstinspires.ftc.team7316.commands.ResetAngle;
import org.firstinspires.ftc.team7316.commands.StrafeForTime;
import org.firstinspires.ftc.team7316.commands.TurnGyroSimple;
import org.firstinspires.ftc.team7316.commands.TurnTowardsCheddar;
import org.firstinspires.ftc.team7316.commands.TurnTowardsCorridor;
import org.firstinspires.ftc.team7316.commands.UnturnCheddar;
import org.firstinspires.ftc.team7316.util.Constants;
import org.firstinspires.ftc.team7316.util.commands.flow.SequentialCommand;
import org.firstinspires.ftc.team7316.util.commands.flow.Wait;

/**
 * Contains all the sequential commands to run
 */
public class AutoCodes {

    /*
    public static SequentialCommand exampleAutoSequence() {
        SomeCommand cmd1 = new SomeCommand();
        SomeCommand cmd2 = new SomeCommand();
        Command[] cmds = {cmd1, cmd2};
        return new SequentialCommand(cmds);
    }
    */

    public static SequentialCommand blueDoubleCheddarSequence() {
        ClimbForPosition lowerClimber = new ClimbForPosition(Constants.CLIMB_MOTOR_EXTENDED);
        DriveDistance driveAwayFromLander = new DriveDistance(Constants.inchesToTicks(3));
//        ClimbForPosition compactClimber = new ClimbForPosition(Constants.CLIMB_MOTOR_COMPACTED);

        ResetAngle reset1 = new ResetAngle();
        CameraUntilCheddar findCheddar1 = new CameraUntilCheddar();
        TurnTowardsCheddar turnCheddar1 = new TurnTowardsCheddar();
//        CameraUntilCheddar findCheddar2 = new CameraUntilCheddar();
//        TurnTowardsCheddar turnCheddar2 = new TurnTowardsCheddar();

        DriveDistance driveToCheddar1 = new DriveDistance(Constants.inchesToTicks(Constants.CHEDDAR_DISTANCE1));
        DriveDistance driveBackFromCheddar1 = new DriveDistance(-Constants.inchesToTicks(Constants.CHEDDAR_DISTANCE1 - Constants.RETURN_SPACING));
        TurnTowardsCorridor turnTowardsCorridor = new TurnTowardsCorridor();
        DriveDistance driveToCorridor = new DriveDistance(new AutoDecision() {
            @Override
            public double findNumber() {
                return Constants.inchesToTicks(Constants.CORRIDOR_DISTANCE + Constants.RETURN_SPACING * Math.sin(TurnTowardsCheddar.ANGLE_TURNED / 180 * Math.PI));
            }
        });

        TurnGyroSimple turnAlongCorridor = new TurnGyroSimple(-42);
        DriveDistance driveToBox = new DriveDistance(Constants.inchesToTicks(Constants.BOX_DISTANCE));
        MovePlateServo dropMarker = new MovePlateServo(true);
        Wait waitReturnPlateServo = new Wait(0.3);
        MovePlateServo pullBackServo = new MovePlateServo(false);
        DriveDistance goBackBox = new DriveDistance(-Constants.inchesToTicks(10));
        StrafeForTime strafeAwayBox = new StrafeForTime(1, false);
        TurnGyroSimple turnTowardsCheddar = new TurnGyroSimple(-135);

        ResetAngle reset2 = new ResetAngle();
        CameraUntilCheddar findCheddar3 = new CameraUntilCheddar();
        TurnTowardsCheddar turnCheddar3 = new TurnTowardsCheddar();
//        CameraUntilCheddar findCheddar4 = new CameraUntilCheddar();
//        TurnTowardsCheddar turnCheddar4 = new TurnTowardsCheddar();

        DriveDistance driveToCheddar2 = new DriveDistance(Constants.inchesToTicks(Constants.CHEDDAR_DISTANCE2));
        DriveDistance driveBackFromCheddar2 = new DriveDistance(-Constants.inchesToTicks(Constants.CHEDDAR_DISTANCE2_RETURN));
        UnturnCheddar unturn = new UnturnCheddar();

        DriveDistance driveToCrater = new DriveDistance(Constants.inchesToTicks(Constants.CRATER_DISTANCE));

        MovePlateServo breakBarrier = new MovePlateServo(true);

        return new SequentialCommand(
                lowerClimber,
                driveAwayFromLander,
//                compactClimber,
                reset1,
                findCheddar1,
                turnCheddar1,
//                findCheddar2,
//                turnCheddar2,
                driveToCheddar1,
                driveBackFromCheddar1,
                turnTowardsCorridor,
                driveToCorridor,
                turnAlongCorridor,
                driveToBox,
                dropMarker,
                waitReturnPlateServo,
                pullBackServo,
                goBackBox,
                strafeAwayBox,
                turnTowardsCheddar,
                reset2,
                findCheddar3,
                turnCheddar3,
//                findCheddar4,
//                turnCheddar4,
                driveToCheddar2,
                driveBackFromCheddar2,
                unturn,
                driveToCrater,
                breakBarrier);

    }
    public static SequentialCommand SingleCheddarLandingZoneSide() {
        ClimbForPosition lowerClimber = new ClimbForPosition(Constants.CLIMB_MOTOR_EXTENDED);
        DriveDistance driveAwayFromLander = new DriveDistance(Constants.inchesToTicks(5));
        ClimbForPosition compactClimber = new ClimbForPosition(Constants.CLIMB_MOTOR_COMPACTED);
        ResetAngle reset1 = new ResetAngle();
        CameraUntilCheddar findCheddar1 = new CameraUntilCheddar();
        TurnTowardsCheddar turnCheddar1 = new TurnTowardsCheddar();
        DriveDistance driveToCheddar1 = new DriveDistance(Constants.inchesToTicks(Constants.CHEDDAR_DISTANCE1));
        TurnGyroSimple turnToZone = new TurnGyroSimple(new AutoDecision() {
            @Override
            public double findNumber() {
                if(TurnTowardsCheddar.ANGLE_TURNED>10){
                    return Constants.TURN_TOWARDS_ZONE_2;
                }
                else if(TurnTowardsCheddar.ANGLE_TURNED<-10){
                    return -Constants.TURN_TOWARDS_ZONE_2;
                }
                else{
                    return 0;
                }
            }
        });

        DriveDistance driveToZone = new DriveDistance(new AutoDecision() {
            @Override
            public double findNumber() {
                if(TurnTowardsCheddar.ANGLE_TURNED <= 10 && TurnTowardsCheddar.ANGLE_TURNED >= -10){
                    return Constants.inchesToTicks(Constants.DRIVE_TOWARDS_ZONE_2_CENTER);
                }
                else{
                    return Constants.inchesToTicks(Constants.DRIVE_TOWARDS_ZONE_2_SIDES);
                }
            }
        });

        TurnGyroSimple turnToCrater = new TurnGyroSimple(new AutoDecision() {
            @Override
            public double findNumber() {
                if(TurnTowardsCheddar.ANGLE_TURNED>10){
                    return Constants.TURN_TOWARDS_CRATER_2_RIGHT;
                }
                else if(TurnTowardsCheddar.ANGLE_TURNED<-10){
                    return Constants.TURN_TOWARDS_CRATER_2_LEFT;
                }
                else {
                    return  Constants.TURN_TOWARDS_CRATER_2_CENTER;
                }
            }
        });

        DriveDistance driveToCrater = new DriveDistance(Constants.inchesToTicks(Constants.CRATER_DISTANCE));
        return new SequentialCommand(
               lowerClimber,
               driveAwayFromLander,
               compactClimber,
               reset1,
               findCheddar1,
               turnCheddar1,
               driveToCheddar1,
               turnToZone,
               driveToZone,
               turnToCrater,
               driveToCrater);

    }
    public static SequentialCommand turnAndHitCheddar() {
        ResetAngle reset1 = new ResetAngle();
        CameraUntilCheddar findCheddar1 = new CameraUntilCheddar();
        TurnTowardsCheddar turnCheddar1 = new TurnTowardsCheddar();
//        CameraUntilCheddar findCheddar2 = new CameraUntilCheddar();
//        TurnTowardsCheddar turnCheddar2 = new TurnTowardsCheddar();

        DriveDistance driveToCheddar1 = new DriveDistance(Constants.inchesToTicks(Constants.CHEDDAR_DISTANCE1));


        return new SequentialCommand(
                reset1,
                findCheddar1,
                turnCheddar1,
//                findCheddar2,
//                turnCheddar2,
                driveToCheddar1
                );

    }

}
