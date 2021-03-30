package mx.dev.blank.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "Grade")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Grade implements Serializable {

    private static final long serialVerionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "student_id", nullable = false)
    private Student studentId;

    @Column(name = "course_teacher_id", nullable = false)
    private CourseTeacher courseTeacherId;

    @Column(name = "student_grade", nullable = true)
    private int studentGrade;

    @Column(name = "register_date", nullable = true)
    private LocalDate date;

}