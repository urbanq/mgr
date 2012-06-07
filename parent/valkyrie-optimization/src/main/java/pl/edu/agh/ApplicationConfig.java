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
import org.valkyriercp.form.binding.BinderSelectionStrategy;
import org.valkyriercp.form.binding.swing.NumberBinder;
import org.valkyriercp.form.builder.ChainedInterceptorFactory;
import org.valkyriercp.form.builder.FormComponentInterceptorFactory;
import org.valkyriercp.form.builder.ToolTipInterceptorFactory;
import org.valkyriercp.rules.RulesSource;
import org.valkyriercp.taskpane.TaskPaneNavigatorApplicationWindowFactory;
import org.valkyriercp.text.SelectAllFormComponentInterceptorFactory;
import org.valkyriercp.text.TextCaretFormComponentInterceptorFactory;
import org.valkyriercp.widget.WidgetViewDescriptor;
import pl.edu.agh.dao.*;
import pl.edu.agh.dao.jdbc.*;
import pl.edu.agh.dao.jdbc.mapper.JGPParameterMapper;
import pl.edu.agh.dao.jdbc.mapper.JGPValueMapper;
import pl.edu.agh.domain.Department;
import pl.edu.agh.domain.IncomeModeLimit;
import pl.edu.agh.domain.OutcomeModeLimit;
import pl.edu.agh.domain.ValidationRulesSource;
import pl.edu.agh.service.ICD10Service;
import pl.edu.agh.service.ICD9Service;
import pl.edu.agh.service.JGPService;
import pl.edu.agh.service.PatientService;
import pl.edu.agh.ui.*;
import pl.edu.agh.ui.binder.ICD10Binder;
import pl.edu.agh.ui.binder.ICD9Binder;
import pl.edu.agh.ui.binder.NameableBinder;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Configuration
public class ApplicationConfig extends AbstractApplicationConfig {

    @Override
    public ApplicationLifecycleAdvisor applicationLifecycleAdvisor() {
        ApplicationLifecycleAdvisor lifecycleAdvisor =  super.applicationLifecycleAdvisor();
        lifecycleAdvisor.setStartingPageDescriptor(new SingleViewPageDescriptor(grouperView()));
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
        formComponentInterceptorFactory.getInterceptorFactories().add(new TextCaretFormComponentInterceptorFactory());
//        formComponentInterceptorFactory.getInterceptorFactories().add(new ComboBoxAutoCompletionInterceptorFactory());
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

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ICD10DataEditor icd10DataEditor() {
        return new ICD10DataEditor(icd10DataProvider());
    }

    public ICD10DataProvider icd10DataProvider() {
        return new ICD10DataProvider(icd10Service());
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ICD9DataEditor icd9DataEditor() {
        return new ICD9DataEditor(icd9DataProvider());
    }

    public ICD9DataProvider icd9DataProvider() {
        return new ICD9DataProvider(icd9Service());
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public JGPDataEditor jgpDataEditor() {
        return new JGPDataEditor(jgpDataProvider());
    }

    public JGPDataProvider jgpDataProvider() {
        return new JGPDataProvider(jgpService());
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public GrouperWidget grouperWidget() {
        return new GrouperWidget();
    }

    // Views

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public WidgetViewDescriptor patientView() {
        return patientDataEditor().createViewDescriptor("patientView");
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public WidgetViewDescriptor icd10View() {
        return icd10DataEditor().createViewDescriptor("icd10View");
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public WidgetViewDescriptor icd9View() {
        return icd9DataEditor().createViewDescriptor("icd9View");
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public WidgetViewDescriptor jgpView() {
        return jgpDataEditor().createViewDescriptor("jgpView");
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public WidgetViewDescriptor grouperView() {
        return grouperWidget().createViewDescriptor("grouperView");
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

    @Bean
    public ICD10Service icd10Service() {
        return new ICD10Service();
    }

    @Bean
    public ICD9Service icd9Service() {
        return new ICD9Service();
    }

    @Bean
    public JGPService jgpService() {
        return new JGPService();
    }

    // Binders

    @Bean
    public Binder integerBinder() {
        NumberBinder integerBinder = new NumberBinder(Integer.class);
        integerBinder.setNegativeSign(false);
        return integerBinder;
    }

    @Bean
    public Binder departmentBinder() {
        NameableBinder<Department> binder = new NameableBinder<Department>(Department.class);
        binder.setDao(departmentDao());
        return binder;
    }

    @Bean
    public Binder incomeModeLimitBinder() {
        NameableBinder<IncomeModeLimit> binder = new NameableBinder<IncomeModeLimit>(IncomeModeLimit.class);
        binder.setDao(incomeModeLimitDao());
        return binder;
    }

    @Bean
    public Binder outcomeModeLimitBinder() {
        NameableBinder<OutcomeModeLimit> binder = new NameableBinder<OutcomeModeLimit>(OutcomeModeLimit.class);
        binder.setDao(outcomeModeLimitDao());
        return binder;
    }

    @Bean
    public Binder icd10Binder() {
        return new ICD10Binder();
    }

    @Bean
    public Binder icd9Binder() {
        return new ICD9Binder();
    }

    @Override
    protected void registerBinders(BinderSelectionStrategy binderSelectionStrategy) {
        super.registerBinders(binderSelectionStrategy);
        binderSelectionStrategy.registerBinderForPropertyType(Department.class, departmentBinder());
        binderSelectionStrategy.registerBinderForPropertyType(IncomeModeLimit.class, incomeModeLimitBinder());
        binderSelectionStrategy.registerBinderForPropertyType(OutcomeModeLimit.class, outcomeModeLimitBinder());
        binderSelectionStrategy.registerBinderForPropertyType(Integer.class, integerBinder());
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
        dataSource.setUrl("jdbc:h2:database;AUTO_RECONNECT=TRUE");
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

    @Bean
    public ICD10Dao icd10Dao() {
        JdbcICD10Dao dao = new JdbcICD10Dao();
        dao.setDataSource(dataSource());
        return dao;
    }

    @Bean
    public ICD9Dao icd9Dao() {
        JdbcICD9Dao dao = new JdbcICD9Dao();
        dao.setDataSource(dataSource());
        return dao;
    }

    @Bean
    public JGPDao jgpDao() {
        JdbcJGPDao dao = new JdbcJGPDao();
        dao.setDataSource(dataSource());
        return dao;
    }

    @Bean
    public DepartmentDao departmentDao() {
        JdbcDepartmentDao dao = new JdbcDepartmentDao();
        dao.setDataSource(dataSource());
        return dao;
    }

    @Bean
    public IncomeModeLimitDao incomeModeLimitDao() {
        JdbcIncomeModeLimitDao dao = new JdbcIncomeModeLimitDao();
        dao.setDataSource(dataSource());
        return dao;
    }

    @Bean
    public OutcomeModeLimitDao outcomeModeLimitDao() {
        JdbcOutcomeModeLimitDao dao = new JdbcOutcomeModeLimitDao();
        dao.setDataSource(dataSource());
        return dao;
    }

    @Bean
    public AgeLimitDao ageLimitDao() {
        JdbcAgeLimitDao dao = new JdbcAgeLimitDao();
        dao.setDataSource(dataSource());
        return dao;
    }

    @Bean
    public HospitalLimitDao hospitalLimitDao() {
        JdbcHospitalLimitDao dao = new JdbcHospitalLimitDao();
        dao.setDataSource(dataSource());
        return dao;
    }

    @Bean
    public JGPParameterMapper jgpParameterMapper() {
        return new JGPParameterMapper();
    }

    @Bean
    public JGPParameterDao jgpParameterDao() {
        JdbcJGPParameterDao dao = new JdbcJGPParameterDao();
        dao.setDataSource(dataSource());
        return dao;
    }

    @Bean
    public JGPValueMapper jgpValueMapper() {
        return new JGPValueMapper();
    }

    @Bean
    public JGPValueDao jgpValueDao() {
        JdbcJGPValueDao dao = new JdbcJGPValueDao();
        dao.setDataSource(dataSource());
        return dao;
    }

//    @Bean
//    public ICD9ListMapper icd9ListMapper() {
//        return new ICD9ListMapper();
//    }

    @Bean
    public ICD9ListDao icd9ListDao() {
        JdbcICD9ListDao dao = new JdbcICD9ListDao();
        dao.setDataSource(dataSource());
        return dao;
    }

    @Bean
    public ICD10ListDao icd10ListDao() {
        JdbcICD10ListDao dao = new JdbcICD10ListDao();
        dao.setDataSource(dataSource());
        return dao;
    }
}
