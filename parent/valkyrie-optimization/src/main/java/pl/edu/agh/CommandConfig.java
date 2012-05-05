package pl.edu.agh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.valkyriercp.command.config.AbstractCommandConfig;
import org.valkyriercp.command.support.AbstractCommand;
import org.valkyriercp.command.support.CommandGroup;
import org.valkyriercp.command.support.CommandGroupFactoryBean;
import org.valkyriercp.command.support.ShowViewCommand;

@Configuration
public class CommandConfig extends AbstractCommandConfig {
    @Autowired
    private ApplicationConfig appConfig;

    @Bean
    public AbstractCommand showPatientViewCommand() {
        return new ShowViewCommand("showPatientViewCommand", appConfig.patientView());
    }

    @Bean
    public CommandGroup fileMenu() {
        CommandGroupFactoryBean fileMenuFactory = new CommandGroupFactoryBean();
        fileMenuFactory.setGroupId("fileMenu");
        fileMenuFactory.setMembers(exitCommand());
        return fileMenuFactory.getCommandGroup();
    }

    @Bean
    public CommandGroup windowMenu() {
        CommandGroupFactoryBean windowMenuFactory = new CommandGroupFactoryBean();
        windowMenuFactory.setGroupId("windowMenu");
        windowMenuFactory.setMembers(newWindowCommand());
        return windowMenuFactory.getCommandGroup();
    }

    @Bean
    public CommandGroup helpMenu() {
        CommandGroupFactoryBean factory = new CommandGroupFactoryBean();
        factory.setGroupId("helpMenu");
        factory.setMembers(aboutCommand());
        return factory.getCommandGroup();
    }

    @Bean
    public CommandGroup viewMenu() {
        CommandGroupFactoryBean windowMenuFactory = new CommandGroupFactoryBean();
        windowMenuFactory.setGroupId("viewMenu");
//        windowMenuFactory.setMembers(itemDataEditorCommand(), supplierDataEditorCommand());
        windowMenuFactory.setMembers(showPatientViewCommand());
        return windowMenuFactory.getCommandGroup();
    }

    @Bean
    @Qualifier("navigation")
    public CommandGroup navigationCommandGroup() {
        CommandGroupFactoryBean menuFactory = new CommandGroupFactoryBean();
        menuFactory.setGroupId("navigation");
        menuFactory.setMembers(viewMenu());
        return menuFactory.getCommandGroup();
    }

    @Bean
    @Qualifier("menubar")
    public CommandGroup menuBarCommandGroup() {
        CommandGroupFactoryBean menuFactory = new CommandGroupFactoryBean();
        menuFactory.setGroupId("menu");
        menuFactory.setMembers(fileMenu(), windowMenu(), helpMenu());
        return menuFactory.getCommandGroup();
    }

//    @Bean
//    @Qualifier("toolbar")
//    public CommandGroup toolBarCommandGroup() {
//        CommandGroupFactoryBean toolbarFactory = new CommandGroupFactoryBean();
//        toolbarFactory.setGroupId("toolbar");
//        toolbarFactory.setMembers("newContactCommand", "propertiesCommand", "deleteCommand");
//        return toolbarFactory.getCommandGroup();
//    }
}
