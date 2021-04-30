package ASS.covaxx.repo;

import ASS.covaxx.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import java.util.Collection;

@Repository
public class PatientRepo {

    @Autowired
    private MongoTemplate mongo;

    public void save(Patient patient) { mongo.save(patient); }

    public Patient getById(String patientID) { return mongo.findById(patientID, Patient.class); }

    public Collection<Patient> getAll() { return mongo.findAll(Patient.class); }
}
