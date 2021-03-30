package mx.dev.blank.dao;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import mx.dev.blank.entity.Course;
import mx.dev.blank.entity.CourseTeacher;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface CourseDAO {

  void create(@NotNull Course course);

  void update(@NotNull Course course);

  void delete(@NotNull Course course);

  void deleteByKeycodePrefix(@NotBlank String prefix);

  /*
  The new method
   */

  List<CourseTeacher> getCourseByDateAndDay(final String startTime, final String endTime, final String weekDay);
}
