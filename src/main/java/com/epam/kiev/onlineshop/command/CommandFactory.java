package com.epam.kiev.onlineshop.command;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by Администратор on 1/2/15.
 */
public class CommandFactory {
    private HashMap<String, ICommand> commands = null;

    private CommandFactory() {
        commands = new HashMap<String, ICommand>();
        initCommands();
    }

    private static class SingletonHolder {
        private static final CommandFactory INSTANCE = new CommandFactory();
    }

    public static CommandFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public ICommand getCommand(HttpServletRequest req) {
        HashMap<String, ICommand> commands = (HashMap<String, ICommand>) req.getSession().getAttribute("commands");
        ICommand command = commands.get(req.getParameter("command"));
        if (command == null) {
            command = new NoCommand();
        }
        return command;
    }

    //Init Commands Unneeded for my Security
    private void initCommands(){
        commands.put("category", new CategoryCommand());
        commands.put("login", new LoginCommand());
        commands.put("index", new IndexCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("register", new RegisterCommand());
        commands.put("cart", new CartCommand());
        commands.put("account", new AccountCommand());
        commands.put("language", new LanguageCommand());
    }

    public HashMap<String, ICommand> blockedUserCommands() {

        HashMap<String, ICommand> commands = new HashMap<String, ICommand>();

        commands.put("category", new CategoryCommand());
        commands.put("language", new LanguageCommand());
//        commands.put("login", new LoginCommand());
//        commands.put("index", new IndexCommand());
//        commands.put("register", new RegisterCommand());

//        commands.put("logout", new AccessDeniedCommand());
//        commands.put("account", new AccessDeniedCommand());
//        commands.put("cart", new CartCommand());
//        commands.put("cart", new AccessDeniedCommand());

        return commands;
    }

    public HashMap<String, ICommand> noneUserCommands() {

        HashMap<String, ICommand> commands = new HashMap<String, ICommand>();

        commands.put("category", new CategoryCommand());
        commands.put("login", new LoginCommand());
        commands.put("index", new IndexCommand());
        commands.put("register", new RegisterCommand());

        commands.put("logout", new AccessDeniedCommand());
        commands.put("account", new AccessDeniedCommand());
//        commands.put("cart", new CartCommand());
        commands.put("cart", new AccessDeniedCommand());
        commands.put("language", new LanguageCommand());

        return commands;
    }

    public HashMap<String, ICommand> userCommands() {

        HashMap<String, ICommand> commands = new HashMap<String, ICommand>();

        commands.put("category", new CategoryCommand());
        commands.put("login", new LoginCommand());
        commands.put("index", new IndexCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("register", new RegisterCommand());
        commands.put("cart", new CartCommand());
        commands.put("account", new AccountCommand());
        commands.put("language", new LanguageCommand());

        return commands;
    }

    public HashMap<String, ICommand> adminCommands() {

        HashMap<String, ICommand> commands = new HashMap<String, ICommand>();

        commands.put("category", new CategoryCommand());
        commands.put("login", new LoginCommand());
        commands.put("index", new IndexCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("register", new RegisterCommand());
        commands.put("cart", new CartCommand());
        commands.put("account", new AccountAdminCommand()); //Set Admin Account Command
        commands.put("language", new LanguageCommand());

        return commands;
    }
}
