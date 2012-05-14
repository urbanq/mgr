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
    public AbstractCommand patientViewCommand() {
        return new ShowViewCommand("patientViewCommand", appConfig.patientView());
    }

    @Bean
    public AbstractCommand icd10ViewCommand() {
        return new ShowViewCommand("icd10ViewCommand", appConfig.icd10View());
    }

    @Bean
    public AbstractCommand icd9ViewCommand() {
        return new ShowViewCommand("icd9ViewCommand", appConfig.icd9View());
    }

    @Bean
    public AbstractCommand jgpViewCommand() {
        return new ShowViewCommand("jgpViewCommand", appConfig.jgpView());
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
        windowMenuFactory.setMembers(patientViewCommand(),
                                     icd10ViewCommand(),
                                     icd9ViewCommand(),
                                     jgpViewCommand());
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
