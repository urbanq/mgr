package pl.edu.agh.domain;

import org.valkyriercp.rules.Rules;
import org.valkyriercp.rules.constraint.Constraint;
import org.valkyriercp.rules.support.DefaultRulesSource;

/**
 * This class is a source for validation rules associated with the domain objects in this application. This class is
 * wired into application via the application context configuration like this:
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
            regexp("[-'.a-zA-ZąĄęĘćĆśŚóÓżŻźŻ ]*", "alphabeticConstraint") });

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
    }

    private Rules createPatientRules() {
        return new Rules(Patient.class) {
            protected void initRules() {
                add("firstName", NAME_CONSTRAINT);
                add("lastName", NAME_CONSTRAINT);
                add(not(eqProperty("firstName", "lastName")));
                add("pesel", PESEL_CONSTRAINT);
            }
        };
    }

    /**
     * Construct the rules that are used to validate a Contact domain object.
     * @return validation rules
     * @see Rules
     */
//    private Rules createContactRules() {
//        // Construct a Rules object that contains all the constraints we need to apply
//        // to our domain object. The Rules class offers a lot of convenience methods
//        // for creating constraints on named properties.
//
//        return new Rules(Contact.class) {
//            protected void initRules() {
//                add("firstName", NAME_CONSTRAINT);
//                add("lastName", NAME_CONSTRAINT);
//                add(not(eqProperty("firstName", "lastName")));
//
//                // If a DOB is specified, it must be in the past
//                add("dateOfBirth", lt(new Date()));
//
//                add("emailAddress", EMAIL_CONSTRAINT);
//                add("homePhone", PHONE_CONSTRAINT);
//                add("workPhone", PHONE_CONSTRAINT);
//
//                add("contactType", required());
//
//                // Note that you can define constraints on nested properties.
//                // This is useful when the nested object is not displayed in
//                // a form of its own.
//                add("address.address1", required());
//                add("address.city", required());
//                add("address.state", required());
//                add("address.zip", ZIPCODE_CONSTRAINT);
//
//                add("memo", required());
//            }
//        };
//    }
}
