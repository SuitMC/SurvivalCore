package nahubar65.gmail.com.score.loader;

import nahubar65.gmail.com.score.command.Command;

public class CommandLoader implements Loadable {

    private Command[] commands;

    public CommandLoader(Command... commands){
        this.commands = commands;
    }

    @Override
    public void load() {
        for (Command command : commands) {
            command.register();
        }
    }
}