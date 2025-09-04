package com.example.alumni.entity;
import com.example.alumni.entity.enums.RecordStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "academic_record")
@Data
public class AcademicRecord {

    @Id
    @Column(name = "recordid", nullable = false, length = 36)
    private String recordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumniUserld", nullable = false)
    private AlumniProfile alumniProfile;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collegeld", nullable = false)
    private College college;


    @Column(name = "degreeName", length = 255)
    private String degreeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RecordStatus status;
}