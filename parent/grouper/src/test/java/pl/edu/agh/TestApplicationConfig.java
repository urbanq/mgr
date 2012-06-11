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
import pl.edu.agh.domain.Condition;
import pl.edu.agh.service.ICD10Service;
import pl.edu.agh.service.ICD9Service;
import pl.edu.agh.service.JGPService;
import pl.edu.agh.service.PatientService;
import pl.edu.agh.service.condition.*;

import javax.sql.DataSource;
import java.util.EnumMap;

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

    @Bean A a() {return new A();}
    @Bean B b() {return new B();}
    @Bean C c() {return new C();}
    @Bean D d() {return new D();}
    @Bean E e() {return new E();}
    @Bean F f() {return new F();}
    @Bean G g() {return new G();}
    @Bean H h() {return new H();}
    @Bean I i() {return new I();}
    @Bean J j() {return new J();}
    @Bean K k() {return new K();}
    @Bean L l() {return new L();}
    @Bean M m() {return new M();}
    @Bean N n() {return new N();}
    @Bean O o() {return new O();}
    @Bean P p() {return new P();}
    @Bean Q q() {return new Q();}
    @Bean R r() {return new R();}
    @Bean S s() {return new S();}
    @Bean T t() {return new T();}
    @Bean U u() {return new U();}
    @Bean V v() {return new V();}
    @Bean W w() {return new W();}
    @Bean X x() {return new X();}
    @Bean Y y() {return new Y();}
    @Bean Z z() {return new Z();}

    @Bean
    public EnumMap<Condition, AbstractChecker> conditionsMap() {
        EnumMap<Condition, AbstractChecker> conditionsMap = new EnumMap<Condition, AbstractChecker>(Condition.class);
        conditionsMap.put(a().condition(), a());
        conditionsMap.put(b().condition(), b());
        conditionsMap.put(c().condition(), c());
        conditionsMap.put(d().condition(), d());
        conditionsMap.put(e().condition(), e());
        conditionsMap.put(f().condition(), f());
        conditionsMap.put(g().condition(), g());
        conditionsMap.put(h().condition(), h());
        conditionsMap.put(i().condition(), i());
        conditionsMap.put(j().condition(), j());
        conditionsMap.put(k().condition(), k());
        conditionsMap.put(l().condition(), l());
        conditionsMap.put(m().condition(), m());
        conditionsMap.put(n().condition(), n());
        conditionsMap.put(o().condition(), o());
        conditionsMap.put(p().condition(), p());
        conditionsMap.put(q().condition(), q());
        conditionsMap.put(r().condition(), r());
        conditionsMap.put(s().condition(), s());
        conditionsMap.put(t().condition(), t());
        conditionsMap.put(u().condition(), u());
        conditionsMap.put(v().condition(), v());
        conditionsMap.put(w().condition(), w());
        conditionsMap.put(x().condition(), x());
        conditionsMap.put(y().condition(), y());
        conditionsMap.put(z().condition(), z());
        return conditionsMap;
    }
}
