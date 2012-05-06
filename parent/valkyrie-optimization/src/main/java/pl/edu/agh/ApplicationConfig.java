package pl.edu.agh;

import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.valkyriercp.application.ApplicationWindowFactory;
import org.valkyriercp.application.config.ApplicationLifecycleAdvisor;
import org.valkyriercp.application.config.support.AbstractApplicationConfig;
import org.valkyriercp.application.config.support.UIManagerConfigurer;
import org.valkyriercp.application.support.SingleViewPageDescriptor;
import org.valkyriercp.form.binding.Binder;
import org.valkyriercp.form.binding.swing.NumberBinder;
import org.valkyriercp.form.builder.ChainedInterceptorFactory;
import org.valkyriercp.form.builder.FormComponentInterceptorFactory;
import org.valkyriercp.form.builder.ToolTipInterceptorFactory;
import org.valkyriercp.rules.RulesSource;
import org.valkyriercp.taskpane.TaskPaneNavigatorApplicationWindowFactory;
import org.valkyriercp.text.SelectAllFormComponentInterceptorFactory;
import org.valkyriercp.widget.WidgetViewDescriptor;
import pl.edu.agh.dao.PatientDao;
import pl.edu.agh.dao.jdbc.JdbcPatientDao;
import pl.edu.agh.domain.PatientService;
import pl.edu.agh.domain.ValidationRulesSource;
import pl.edu.agh.ui.PatientDataEditor;
import pl.edu.agh.ui.PatientDataProvider;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Configuration
public class ApplicationConfig extends AbstractApplicationConfig {

    @Override
    public ApplicationLifecycleAdvisor applicationLifecycleAdvisor() {
        ApplicationLifecycleAdvisor lifecycleAdvisor =  super.applicationLifecycleAdvisor();
        lifecycleAdvisor.setStartingPageDescriptor(new SingleViewPageDescriptor(patientView()));
        return lifecycleAdvisor;
    }

    @Override
    public List<String> getResourceBundleLocations() {
        List<String> list = super.getResourceBundleLocations();
        list.add("pl.edu.agh.messages");
        return list;
    }

    public Map<String, Resource> getImageSourceResources() {
        Map<String, Resource> resources = super.getImageSourceResources();
        resources.put("pl.agh.edu.images", applicationContext().getResource("classpath:/pl/edu/agh/images.properties"));
        return resources;
    }

    @Bean
    public UIManagerConfigurer uiManagerConfigurer() {
        UIManagerConfigurer configurer = new UIManagerConfigurer();
        configurer.setLookAndFeel(PlasticXPLookAndFeel.class);
        return configurer;
    }

//    @Override
//    public ApplicationPageFactory applicationPageFactory() {
//        return new TabbedApplicationPageFactory();
//    }

    @Override
    public ApplicationWindowFactory applicationWindowFactory() {
        return new TaskPaneNavigatorApplicationWindowFactory();
    }

    @Override
    public FormComponentInterceptorFactory formComponentInterceptorFactory() {
        ChainedInterceptorFactory formComponentInterceptorFactory = (ChainedInterceptorFactory) super.formComponentInterceptorFactory();
        formComponentInterceptorFactory.getInterceptorFactories().add(new SelectAllFormComponentInterceptorFactory());
        formComponentInterceptorFactory.getInterceptorFactories().add(new ToolTipInterceptorFactory());
        return formComponentInterceptorFactory;
    }

    // Widgets

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public PatientDataEditor patientDataEditor() {
        return new PatientDataEditor(patientDataProvider());
    }

    public PatientDataProvider patientDataProvider() {
        return new PatientDataProvider(patientService());
    }

    // Views

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public WidgetViewDescriptor patientView() {
        return patientDataEditor().createViewDescriptor("patientView");
    }

//    @Bean
//    public WidgetViewDescriptor startView() {
//        Widget htmlViewWidget = new HTMLViewWidget(new ClassPathResource("/org/valkyriercp/sample/showcase/html/start.html"));
//        WidgetViewDescriptor descriptor = new WidgetViewDescriptor("startView", htmlViewWidget);
//        return descriptor;
//    }
//
//    @Bean
//    public ViewDescriptor initialView() {
//        InitialView view = new InitialView();
//        view.setFirstMessage("firstMessage.text");
//        view.setDescriptionTextPath(new ClassPathResource("/pl/edu/agh/ui/initialViewText.html"));
//        return view.getDescriptor();
//    }

    // Services
    @Bean
    public PatientService patientService() {
        return new PatientService();
    }

    // Binders

    @Bean
    public Binder integerBinder() {
        return new NumberBinder(Integer.class);
    }

    @Bean
    public Binder euroBinder() {
        NumberBinder numberBinder = new NumberBinder(BigDecimal.class);
        numberBinder.setNrOfDecimals(2);
        numberBinder.setLeftDecoration("€");
        numberBinder.setFormat("#,##0.00");
        return numberBinder;
    }

    @Override
    public Class<?> getCommandConfigClass() {
        return CommandConfig.class;
    }

    @Override
    public RulesSource rulesSource() {
        return new ValidationRulesSource();
    }

    @Bean
    public DataSource dataSource() {
        SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:/home/mateusz/mgr-git/database;AUTO_RECONNECT=TRUE");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setAutoCommit(true);
        dataSource.setSuppressClose(true);
        return dataSource;
    }

    // DAO's

    @Bean
    public PatientDao patientDao() {
        JdbcPatientDao dao = new JdbcPatientDao();
        dao.setDataSource(dataSource());
        return dao;
    }
}
