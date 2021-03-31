package mx.dev.blank.dao;

import mx.dev.blank.entity.CourseStudent;
import mx.dev.blank.entity.Student;
import mx.dev.blank.model.CourseStudentDTO;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Validated
public interface StudentDAO {


  List<Student> getStudentsBySearchCriteria(
      String nameQuery,
      String uuidQuery,
      @NotNull Date rangeStart,
      @NotNull Date rangeEnd);

  List<CourseStudentDTO> getAssignedCourseByUuid(final String studentUuid);

  List<CourseStudentDTO> getCourseAndTeacherByUuid(final String uuid);
}

