package com.laynester.lux.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.*;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboGender;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUserTalkComposer;

public class SuperPushCommand extends Command {

    public SuperPushCommand(String permission, String[] keys)
    {
        super(permission, keys);
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        if (params.length == 2) {
            Habbo habbo = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getHabbo(params[1]);
            Habbo curHabbo = gameClient.getHabbo();

            if (habbo == null) {
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.error.cmd_push.not_found").replace("%user%", params[1]), RoomChatMessageBubbles.ALERT);
                return true;
            } else if (habbo == gameClient.getHabbo()) {
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.error.cmd_push.push_self"), RoomChatMessageBubbles.ALERT);
                return true;
            } else {
                RoomTile tFront = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getLayout().getTileInFront(gameClient.getHabbo().getRoomUnit().getCurrentLocation(), gameClient.getHabbo().getRoomUnit().getBodyRotation().getValue());

                if (tFront != null && tFront.isWalkable()) {
                    if (tFront.x == habbo.getRoomUnit().getX() && tFront.y == habbo.getRoomUnit().getY()) {
                        RoomTile tFrontTarget = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getLayout().getTileInFront(habbo.getRoomUnit().getCurrentLocation(), gameClient.getHabbo().getRoomUnit().getBodyRotation().getValue());

                        if (tFrontTarget != null && tFrontTarget.isWalkable()) {
                            if (gameClient.getHabbo().getHabboInfo().getCurrentRoom().getLayout().getDoorTile() == tFrontTarget) {
                                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.error.cmd_push.invalid").replace("%username%", params[1]));
                                return true;
                            }
                            if(curHabbo.getRoomUnit().getBodyRotation().getValue() == 4) {
                                RoomTile tile = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getLayout().getTile(habbo.getRoomUnit().getX(), (short) (habbo.getRoomUnit().getY() + 3));
                                habbo.getRoomUnit().setGoalLocation(tile);
                            }
                            if(curHabbo.getRoomUnit().getBodyRotation().getValue() == 0) {
                                RoomTile tile = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getLayout().getTile(habbo.getRoomUnit().getX(),(short) (habbo.getRoomUnit().getY() - 3));
                                habbo.getRoomUnit().setGoalLocation(tile);
                            }
                            if(curHabbo.getRoomUnit().getBodyRotation().getValue() == 6) {
                                RoomTile tile = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getLayout().getTile((short) (habbo.getRoomUnit().getX() - 3),habbo.getRoomUnit().getY());
                                habbo.getRoomUnit().setGoalLocation(tile);
                            }
                            if(curHabbo.getRoomUnit().getBodyRotation().getValue() == 2) {
                                RoomTile tile = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getLayout().getTile((short) (habbo.getRoomUnit().getX() + 3),habbo.getRoomUnit().getY());
                                habbo.getRoomUnit().setGoalLocation(tile);
                            }
                            if(curHabbo.getRoomUnit().getBodyRotation().getValue() == 3) {
                                RoomTile tile = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getLayout().getTile((short) (habbo.getRoomUnit().getX() + 3),habbo.getRoomUnit().getY());
                                habbo.getRoomUnit().setGoalLocation(tile);
                                RoomTile tile2 = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getLayout().getTile(habbo.getRoomUnit().getX(), (short) (habbo.getRoomUnit().getY() + 3));
                                habbo.getRoomUnit().setGoalLocation(tile2);
                            }
                            if(curHabbo.getRoomUnit().getBodyRotation().getValue() == 1) {
                                RoomTile tile = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getLayout().getTile((short) (habbo.getRoomUnit().getX() + 3),habbo.getRoomUnit().getY());
                                habbo.getRoomUnit().setGoalLocation(tile);
                                RoomTile tile2 = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getLayout().getTile(habbo.getRoomUnit().getX(), (short) (habbo.getRoomUnit().getY() - 3));
                                habbo.getRoomUnit().setGoalLocation(tile2);
                            }
                            if(curHabbo.getRoomUnit().getBodyRotation().getValue() == 7) {
                                RoomTile tile = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getLayout().getTile((short) (habbo.getRoomUnit().getX() - 3),habbo.getRoomUnit().getY());
                                habbo.getRoomUnit().setGoalLocation(tile);
                                RoomTile tile2 = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getLayout().getTile(habbo.getRoomUnit().getX(), (short) (habbo.getRoomUnit().getY() - 3));
                                habbo.getRoomUnit().setGoalLocation(tile2);
                            }
                            if(curHabbo.getRoomUnit().getBodyRotation().getValue() == 5) {
                                RoomTile tile = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getLayout().getTile((short) (habbo.getRoomUnit().getX() - 3),habbo.getRoomUnit().getY());
                                habbo.getRoomUnit().setGoalLocation(tile);
                                RoomTile tile2 = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getLayout().getTile(habbo.getRoomUnit().getX(), (short) (habbo.getRoomUnit().getY() + 3));
                                habbo.getRoomUnit().setGoalLocation(tile2);
                            }
                            gameClient.getHabbo().getHabboInfo().getCurrentRoom().sendComposer(new RoomUserTalkComposer(new RoomChatMessage(Emulator.getTexts().getValue("commands.succes.cmd_push.push").replace("%user%", params[1]).replace("%gender_name%", (gameClient.getHabbo().getHabboInfo().getGender().equals(HabboGender.M) ? Emulator.getTexts().getValue("gender.him") : Emulator.getTexts().getValue("gender.her"))), gameClient.getHabbo(), gameClient.getHabbo(), RoomChatMessageBubbles.NORMAL)).compose());
                        }
                    } else {
                        gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.error.cmd_push.cant_reach").replace("%user%", params[1]), RoomChatMessageBubbles.ALERT);
                        return true;
                    }
                }
            }
            return true;
        }

        return true;
    }
}
