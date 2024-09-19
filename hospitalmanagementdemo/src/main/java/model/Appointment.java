package model;



import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "doctor_name", nullable = false)
    private String doctorName;

    @Column(name = "appointment_date", nullable = false)
//    @Temporal(TemporalType.DATE)
    private String date;

    @Column(name = "appointment_time", nullable = false)
    private String time;

    // Constructors
    public Appointment() {
    }

    public Appointment(Patient patient, String doctorName, String date, String time) {
        this.patient = patient;
        this.doctorName = doctorName;
        this.date = date;
        this.time = time;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", patient=" + patient.getName() +
                ", doctorName='" + doctorName + '\'' +
                ", date=" + date +
                ", time='" + time + '\'' +
                '}';
    }
}
