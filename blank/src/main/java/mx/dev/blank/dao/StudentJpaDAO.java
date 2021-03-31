package mx.dev.blank.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import mx.dev.blank.entity.*;
import mx.dev.blank.model.CourseStudentDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StudentJpaDAO implements StudentDAO {

  @Setter(onMethod = @__(@PersistenceContext), value = AccessLevel.PACKAGE)
  private EntityManager em;

  /**
   * SELECT c.name
   * FROM course
   *   JOIN courseTeacher ct ON c.id = ct.course_id
   *   JOIN student s ON ct.id = ct.course_teacher
   *   GROUP BY c.name
   */

  @Override
  public List<CourseStudentDTO> getAssignedCourseByUuid(final String studentUuid) {
    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<CourseStudentDTO> query = builder.createQuery(CourseStudentDTO.class);
    final Root<CourseStudent> root = query.from(CourseStudent.class);
    final Root<CourseTeacher> join = query.from(CourseTeacher.class);

    final Join<CourseStudent, Student> joinStudent = root.join(CourseStudent_.student);
    final Join<CourseTeacher, Course> joinCourse = join.join(CourseTeacher_.course);


    query.multiselect(
            joinCourse.get(Course_.id),
            joinCourse.get(Course_.name));

    query.where(builder.equal(joinStudent.get(Student_.uuid), studentUuid));

    return em.createQuery(query).getResultList();
  }

  @Override
  public List<CourseStudentDTO> getCourseAndTeacherByUuid(final String uuid) {
    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<CourseStudentDTO> query = builder.createQuery(CourseStudentDTO.class);
    final Root<CourseStudent> root = query.from(CourseStudent.class);
    final Root<CourseTeacher> join = query.from(CourseTeacher.class);

    final Join<CourseStudent, Student> joinStudent = root.join(CourseStudent_.student);
    final Join<CourseTeacher, Teacher> joinTeacher = join.join(CourseTeacher_.teacher);
    final Join<CourseTeacher, Course> joinCourse = join.join(CourseTeacher_.course);

    query.multiselect(
            joinCourse.get(Course_.name),
            joinCourse.get(Course_.keycode),
            joinTeacher.get(Teacher_.name));

    query.where(builder.equal(joinStudent.get(Student_.uuid), uuid));

    return em.createQuery(query).getResultList();
  }


  @Override
  public List<Student> getStudentsBySearchCriteria(
      final String nameQuery,
      final String uuidQuery,
      final Date rangeStart,
      final Date rangeEnd) {

    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<Student> query = builder.createQuery(Student.class);
    final Root<Student> root = query.from(Student.class);

    query.select(root);

    final List<Predicate> predicates = new ArrayList<>();
    predicates.add(builder.between(root.get(Student_.birthday), rangeStart, rangeEnd));

    buildSearchCriteria(predicates, builder, root, nameQuery, uuidQuery);

    return em.createQuery(query).getResultList();
  }

  private void buildSearchCriteria(
      final List<Predicate> predicates,
      final CriteriaBuilder builder,
      final Root<Student> root,
      final String nameQuery,
      final String uuidQuery) {

    if (StringUtils.isNotBlank(nameQuery)) {
      final String queryFormat = "%" + nameQuery + "%";

      predicates.add(builder.like(root.get(Student_.name), queryFormat));
      predicates.add(builder.like(root.get(Student_.firstSurname), queryFormat));
      predicates.add(builder.like(root.get(Student_.secondSurname), queryFormat));
    }

    if (uuidQuery != null && uuidQuery.trim().isEmpty()) {
      final String queryFormat = "%" + uuidQuery + "%";

      predicates.add(builder.like(root.get(Student_.uuid), queryFormat));
    }
  }
}
