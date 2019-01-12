package support.kajstech.kajbot.command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import support.kajstech.kajbot.handlers.ConfigHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CommandManager {

    private static final Map<String, Command> commands = new HashMap<>();


    public static void addCommand(Command command) {
        if (!commands.containsKey(command.name)) {
            commands.put(command.name, command);
        }
    }

    public void handleCommand(MessageReceivedEvent event) {
        final String[] split = event.getMessage().getContentRaw().replaceFirst("(?i)" + Pattern.quote(ConfigHandler.getProperty("Command prefix")), "").split("\\s+");
        final String invoke = split[0].toLowerCase();

        if (commands.containsKey(invoke)) {
            final List<String> argsSplit = Arrays.asList(split).subList(1, split.length);
            final String args = String.join(" ", argsSplit);

            commands.get(invoke).run(new CommandEvent(argsSplit, args, event));
        }
    }
}
