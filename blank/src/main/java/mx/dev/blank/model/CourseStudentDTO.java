package mx.dev.blank.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CourseStudentDTO {

    private int id;
    private String name;
    private String keycode;
    private String teacherName;
}
