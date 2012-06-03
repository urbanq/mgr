package pl.edu.agh.domain;

import org.valkyriercp.rules.Rules;
import org.valkyriercp.rules.constraint.Constraint;
import org.valkyriercp.rules.support.DefaultRulesSource;

/**
 * This class is a source for validation rules associated with the domain objects in this application. This class is
 * wired into application via the application context configuration andLike this:
 *
 * <pre>
 *    &lt;bean id=&quot;rulesSource&quot;
 *        class=&quot;pl.edu.agh.domain.ValidationRulesSource&quot;/&gt;
 * </pre>
 *
 * With this configuration, validating forms will interrogate the rules source for rules that apply to the class of a
 * form object (in this case, that's objects of type {@link Patient}.
 */
public class ValidationRulesSource extends DefaultRulesSource {
    /**
     * Basic name validator. Note that the "alphabeticConstraint" argument is a message key used to locate the message
     * to display when this validator fails.
     */
    private final Constraint NAME_CONSTRAINT = all(new Constraint[] { required(), minLength(2),
            regexp("[-'.a-zA-ZąĄęĘćĆńŃśŚóÓżŻźŻ ]*", "alphabeticConstraint") });

    /** Zipcode validator, allows NNNNN or NNNNN-NNNN */
    private final Constraint ZIPCODE_CONSTRAINT = all(new Constraint[] { required(), minLength(5), maxLength(10),
            regexp("[0-9]{5}(-[0-9]{4})?", "zipcodeConstraint") });

    /** Email validator, simply tests for x@y, wrap in ()? so it is optional */
    private final Constraint EMAIL_CONSTRAINT = all(new Constraint[] { regexp("([-a-zA-Z0-9.]+@[-a-zA-Z0-9.]+)?",
            "emailConstraint") });

    /** Phone number validator, must be 123-456-7890, wrap in ()? so it is optional */
    private final Constraint PHONE_CONSTRAINT = all(new Constraint[] { regexp("([0-9]{3}-[0-9]{3}-[0-9]{4})?",
            "phoneConstraint") });

    private final Constraint PESEL_CONSTRAINT = all(new Constraint[] { required(), not(eq("00000000000")),
            regexp("[0-9]{11}", "peselConstraint") });

    /**
     * Construct the rules source. Just add all the rules for each class that will be validated.
     */
    public ValidationRulesSource() {
        super();

        // Add the rules specific to the object types we manage
        addRules(createPatientRules());
        addRules(createStayRules());
        addRules(createICD9WrapperRules());
        addRules(createICD10WrapperRules());
        addRules(createHospitalizationRules());
    }

    private Rules createPatientRules() {
        return new Rules(Patient.class) {
            protected void initRules() {
                add("firstname", NAME_CONSTRAINT);
                add("lastname", NAME_CONSTRAINT);
                add(not(eqProperty("firstname", "lastname")));
                add("pesel", PESEL_CONSTRAINT);
            }
        };
    }

    private Rules createStayRules() {
        return new Rules(Stay.class) {
            protected void initRules() {
                add("department", required());
                add("service", required());
                add("incomeDate", required());
                add("outcomeDate", required());
                add(gtProperty("outcomeDate", "incomeDate"));
            }
        };
    }

    private Rules createICD9WrapperRules() {
        return new Rules(ICD9Wrapper.class) {
            protected void initRules() {
                add("icd9", required());
                add("procedureDate", required());
            }
        };
    }

    private Rules createICD10WrapperRules() {
        return new Rules(ICD10Wrapper.class) {
            protected void initRules() {
                add("icd10", required());
            }
        };
    }

    private Rules createHospitalizationRules() {
        return new Rules(Episode.class) {
            protected void initRules() {
                add("hospitalType", required());
                add("incomeDate", required());
                add("outcomeDate", required());
                add("stays", required());
            }
        };
    }
}
