package ASS.covaxx.controller;

import ASS.covaxx.model.Patient;
import ASS.covaxx.repo.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@Controller
public class PatientController {

    @Autowired
    private PatientRepo PatientRepo;

    @GetMapping("/patient")
    public @ResponseBody Collection<Patient> getAll(){

        return this.PatientRepo.getAll();
    }

    @GetMapping("/pateint/{patientID}")
    public @ResponseBody
    Patient getOne(
            @PathVariable String patientID)
    {

        Patient patient = this.PatientRepo.getById(patientID);

        if (patient == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"There is no patient with this patientID");

        return patient;
    }

    @PostMapping("/patient")
    public @ResponseBody
    Patient createNew(@RequestBody Patient patient) {

        if (patient.patientID == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient must specify a patientID");

        Patient existingPatient = this.PatientRepo.getById(patient.patientID);
        if (existingPatient != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This practitionerId is already used");
        }

        this.PatientRepo.save(patient);

        return patient;
    }

    @PatchMapping("/patient/{patientID}")
    public @ResponseBody
    Patient updateExisting(@PathVariable String patientID, @RequestBody Patient changes) {

        Patient existingPatient = this.PatientRepo.getById(patientID);

        if(existingPatient == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This patientID does not exist");
        }

        if (changes.patientFname != null) {
            existingPatient.patientFname = changes.patientFname;
        }

        if (changes.patientLname != null) {
            existingPatient.patientLname = changes.patientLname;
        }

        this.PatientRepo.save(existingPatient);

        return existingPatient;


    }

}
