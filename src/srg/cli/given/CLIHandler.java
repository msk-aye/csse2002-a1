package srg.cli.given;

import java.util.*;
import java.util.function.Function;



class CommandAction {
    List<String> command;
    Function<List<String>, Command> action;
    public CommandAction(List<String> command,
                         Function<List<String>, Command> action) {
        this.command = command;
        this.action= action;
    }

    @Override
    public String toString() {
        List<String> newCmd = new ArrayList<>(command);
        newCmd.replaceAll(token -> {
            if (token.equals(CLIHandler.NUMBER)) {
                return "NUMBER";
            }
            if (token.equals(CLIHandler.IDENTIFIER)) {
                return "NAME";
            }
            return token;
        });

        return String.join(" ", newCmd);

    }

}



public class CLIHandler {
    public static final String NUMBER = "[0-9]+";
    public static final String IDENTIFIER = "[a-zA-Z 0-9'-''_']+";
    List<CommandAction> commands;

    public CLIHandler() {
        commands = new ArrayList<>(new ArrayList<>());

        commands.add(new CommandAction(List.of(
                "show", "port"
        ), cmd -> new ShipCommand(CommandType.SHOW_PORT, "")));

        commands.add(new CommandAction(List.of(
                "help"
        ), cmd -> new Command(CommandType.SHOW_HELP)));
        commands.add(new CommandAction(List.of(
                "show", "help"
        ), cmd -> new Command(CommandType.SHOW_HELP)));

        commands.add(new CommandAction(List.of(
                "show", "actions"
        ), cmd -> new ShipCommand(CommandType.SHOW_ACTIONS, "")));

        commands.add(new CommandAction(List.of(
                "show", "status"
        ), cmd -> new Command(CommandType.SHOW_STATUS)));


        commands.add(new CommandAction(List.of(
                "show", "room", IDENTIFIER
        ), cmd -> new ShipCommand(CommandType.SHOW_ROOM, cmd.get(2))));

        commands.add(new CommandAction(List.of(
                "exit"
        ), cmd -> new Command(CommandType.EXIT)));


        commands.add(new CommandAction(List.of(
                "fly", "to", IDENTIFIER),
                cmd -> new ShipCommand(CommandType.FLY_TO, cmd.get(2))));

        commands.add(new CommandAction(List.of(
                "jump", "to", IDENTIFIER),
                cmd -> new ShipCommand(CommandType.JUMP_TO, cmd.get(2))
        ));


        commands.add(new CommandAction(List.of(
                "repair", IDENTIFIER),
                cmd -> new ShipCommand(CommandType.REPAIR_ROOM, cmd.get(1))));

        commands.add(new CommandAction(List.of(
                "upgrade", IDENTIFIER),
                cmd -> new ShipCommand(CommandType.UPGRADE_ROOM, cmd.get(1))));

        commands.add(new CommandAction(List.of(
                "purchase", IDENTIFIER, NUMBER),
        cmd -> new PurchaseCommand(cmd.get(1), Integer.parseInt(cmd.get(2)))));
        commands.add(new CommandAction(List.of(
                "buy", IDENTIFIER, NUMBER),
                cmd -> new PurchaseCommand(cmd.get(1), Integer.parseInt(cmd.get(2)))));


    }

    public String getHelp() {
        StringBuilder helpString = new StringBuilder();
        for (CommandAction cmd : commands) {
            helpString.append(System.lineSeparator()).append(cmd.toString());
        }
        return helpString.toString();

    }


    public Command parseOne(IO ioHandler) {
        ioHandler.write("~> ");
        String line = ioHandler.readLine();

        boolean quoted = false;
        List<String> quote_tokens = List.of(line.split("\""));
        List<String> tokens = new ArrayList<>();

        for (String quote_token: quote_tokens) {
            if (quoted) {
                tokens.add(quote_token);
            } else {
                tokens.addAll(List.of(quote_token.split(" ")));
            }
            quoted = !quoted;
        }



        List<String> match = new LinkedList<>();

        List<CommandAction> commands = new LinkedList<>(this.commands);

        commands.removeIf(command -> tokens.size() > command.command.size());

        for (int i = 0; i < tokens.size(); i++) {
            int finalI = i;
            commands.removeIf(command ->
                    !tokens.get(finalI).matches(command.command.get(finalI)));
        }

        if (commands.size() > 1) {
            ioHandler.writeLn("Ambiguous command. could be: ");
            commands.forEach(command -> ioHandler.writeLn(command.toString()));
            return null;
        }
        if (commands.size() != 1) {
            ioHandler.writeLn("Invalid command.");
            return null;
        }
        if (tokens.size() != commands.get(0).command.size()) {
            ioHandler.writeLn("Missing argument.");
            commands.forEach(command -> ioHandler.writeLn(command.toString()));
            return null;
        }

        return commands.get(0).action.apply(tokens);
    }


    public Command parse(IO ioHandler) {
        Command command;
        do {
            command = parseOne(ioHandler);
            if (command == null) {
                continue;
            }
            if (command.type == CommandType.SHOW_HELP) {
                ioHandler.writeLn(getHelp());
                command = null;
            }
        } while (command == null);

        return command;
    }
}

