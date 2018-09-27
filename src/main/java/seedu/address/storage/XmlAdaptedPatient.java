package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.NRIC;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.MedicalRecord;
import seedu.address.model.person.Person;

/**
 * JAXB-friendly version of the Patient.
 */
public class XmlAdaptedPatient extends XmlAdaptedPerson {

    /**
     * Constructs an XmlAdaptedPatient.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPatient() {}

    /**
     * Constructs an {@code XmlAdaptedPatient} with the given patient details.
     */
    public XmlAdaptedPatient(Patient source) {
        super(source);
        nric = source.getNric().code;
        medicalRecord = source.getMedicalRecord().value;
    }

    /**
     * Overwritten method to convert patient into this class for JAXB use.
     *
     * @param source Patient object.
     * @return Xml Adapted version of the given Patient object.
     */
    public static XmlAdaptedPatient adaptToXml(Patient source){
        return new XmlAdaptedPatient(source);
    }

    /**
     * Converts this jaxb-friendly adapted patient object into the model's Patient object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public static Patient convertToPatientModelType(Person source, String nric, String medicalRecords) throws IllegalValueException {
        Person person = source;

        if (medicalRecords == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, MedicalRecord.class.getSimpleName()));
        }
        if (!MedicalRecord.isValidMedicalRecord(medicalRecords)) {
            throw new IllegalValueException(MedicalRecord.MESSAGE_MEDICAL_RECORD_CONSTRAINTS);
        }

        if(nric == null) {
            throw new IllegalValueException((String.format(MISSING_FIELD_MESSAGE_FORMAT, NRIC.class.getSimpleName())));
        }
        if(!NRIC.isValidNric((nric))) {
            throw new IllegalValueException(NRIC.MESSAGE_NRIC_CONSTRAINTS);
        }

        final MedicalRecord modelMedicalRecords = new MedicalRecord(medicalRecords);
        final NRIC modelNric = new NRIC(nric);

        return new Patient(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                person.getTags(), person.getAppointment(), modelNric, modelMedicalRecords);
    }
}
