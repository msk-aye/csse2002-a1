package srg.game;

import srg.cli.given.*;
import srg.ship.RoomTier;
import srg.ship.Ship;
import srg.ports.SpacePort;

import java.util.List;

public class Game {

    //TODO MAKE PORTS PRIVATE
    public List<SpacePort> ports;
    // TODO MAKE SHIP PRIVATE
    public Ship ship;

    public Game() {

        MapGenerator generator = new MapGenerator();
        ports = generator.generateMap();
        ship = new Ship("Leaden Matter", "Jesko Thoch", "SCS1249", RoomTier.BASIC, RoomTier.BASIC,
                ports);
    }

    private boolean handleGameCommand(IO ioHandler, Command command) {
        switch (command.type) {
            case EXIT -> {
                return true;
            }
            case SHOW_STATUS -> {
                ioHandler.writeLn(ship.toString());
            }
        }
        return false;
    }

    public boolean update(IO ioHandler, Command command) {
        if (command instanceof ShipCommand shipCommand) {
            ship.performCommand(ioHandler, shipCommand);
        } else {
            return handleGameCommand(ioHandler, command);
        }

        return false;
    }

}
