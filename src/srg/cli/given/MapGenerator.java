package srg.cli.given;

import srg.ports.*;

import java.util.*;

public class MapGenerator {

    List<String> names;
    Random rand;

    public MapGenerator() {
        rand = new Random();
        rand.setSeed(4);

        names = new ArrayList(List.of(
                "Las Cyamis e1",
                "Mercury",
                "Omega Tagon",
                "Oxlok Harandia 4",
                "Dunelialia X",
                "Pluto",
                "Coldreus 21",
                "Tavix",
                "Ichomi",
                "Sand Planet",
                "Tacnar",
                "Ter Threpon",
                "Trelfalls World",
                "Majestudros",
                "Woncury 3",
                "Tapury XI",
                "Camporox",
                "Noerias Refuge",
                "Solaris",
                "Lunar",
                "Betelgeuse",
                "Dagobah",
                "Wonrax",
                "Vogsphere",
                "Ladnar Aerox",
                "Sopids Stronghold",
                "Abburto",
                "Looda 99",
                "Broeko",
                "Fonolok 47",
                "Talol",
                "Intbudram",
                "Avaleko",
                "Zeus",
                "Phoebus",
                "Ares",
                "Minerva",
                "Ceres",
                "Demeter",
                "Haarangalia IX",
                "Saphos",
                "Leng",
                "Rotfront",
                "Buyan",
                "Vineta",
                "Kitezh",
                "Heimat"
        ));
    }

    String getName() {
        int index = rand.nextInt(names.size());
        return names.remove(index);
    }

    Position randomPosition() {
        int multiplier = 1000;

        return new Position(
                rand.nextInt(multiplier), rand.nextInt(multiplier),
                rand.nextInt(multiplier));

    }


    Position randomPosition(Position at, int radius) {
        return new Position(
                rand.nextInt(-radius, radius) + at.x,
                rand.nextInt(-radius, radius) + at.y,
                rand.nextInt(-radius, radius) + at.z);

    }

    public List<SpacePort> generateMap() {
        List<SpacePort> ports = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Position pos = randomPosition();
            int radius = 15;
            SpacePort port = new SpacePort(getName(), randomPosition(pos, radius));

            List<String> upgradeable;

            if (rand.nextBoolean()) {
                upgradeable = List.of("CargoHold");
            } else {
                upgradeable = List.of("NavigationRoom");
            }

            ShipYard shipYard = new ShipYard(getName(), randomPosition(pos, radius),
                    upgradeable);
            Store store = new Store(getName(), randomPosition(pos, radius));



            ports.add(store);
            ports.add(shipYard);
            ports.add(port);
        }

        return ports;

    }

}
