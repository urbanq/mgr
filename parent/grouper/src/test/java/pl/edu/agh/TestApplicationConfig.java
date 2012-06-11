package pl.edu.agh;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.valkyriercp.application.config.support.AbstractApplicationConfig;
import pl.edu.agh.dao.*;
import pl.edu.agh.dao.jdbc.*;
import pl.edu.agh.dao.jdbc.mapper.JGPHospitalMapper;
import pl.edu.agh.dao.jdbc.mapper.JGPParameterMapper;
import pl.edu.agh.dao.jdbc.mapper.JGPValueMapper;
import pl.edu.agh.service.ICD10Service;
import pl.edu.agh.service.ICD9Service;
import pl.edu.agh.service.JGPService;
import pl.edu.agh.service.PatientService;

import javax.sql.DataSource;

/**
 * @author mateusz
 * @date 11.06.12
 */
@Configuration
public class TestApplicationConfig extends AbstractApplicationConfig {
    // Data source
    @Bean
    public DataSource dataSource() {
        SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:database/test;AUTO_RECONNECT=TRUE");
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
    public IncomeModeDao incomeModeDao() {
        JdbcIncomeModeDao dao = new JdbcIncomeModeDao();
        dao.setDataSource(dataSource());
        return dao;
    }

    @Bean
    public OutcomeModeDao outcomeModeDao() {
        JdbcOutcomeModeDao dao = new JdbcOutcomeModeDao();
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
    public JGPHospitalMapper jgpHospitalMapper() {
        return new JGPHospitalMapper();
    }

    @Bean
    public JGPValueDao jgpValueDao() {
        JdbcJGPValueDao dao = new JdbcJGPValueDao();
        dao.setDataSource(dataSource());
        return dao;
    }

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
}
