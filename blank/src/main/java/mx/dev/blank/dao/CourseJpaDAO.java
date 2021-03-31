package mx.dev.blank.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import mx.dev.blank.entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourseJpaDAO implements CourseDAO {

  @Setter(onMethod = @__(@PersistenceContext), value = AccessLevel.PACKAGE)
  private EntityManager em;

  /**
   * INSERT INTO course (name, keycode) VALUES (${name}, ${keycode})
   */
  @Override
  public void create(final Course course) {
    em.persist(course);
  }

  /**
   * UPDATE course SET name=${name},keycode=${keycode} WHERE id = ${id}
   */
  @Override
  public void update(final Course course) {
    em.merge(course);
  }

  /**
   * DELETE FROM course WHERE id = ${id}
   */
  @Override
  public void delete(final Course course) {
    em.remove(course);
  }

  /**
   * DELETE FROM course WHERE keycode LIKE '${prefix}%'
   */
  @Override
  public void deleteByKeycodePrefix(final String prefix) {
    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaDelete<Course> delete = builder.createCriteriaDelete(Course.class);
    final Root<Course> root = delete.from(Course.class);

    delete.where(builder.like(root.get(Course_.keycode), prefix + "%"));

    em.createQuery(delete).executeUpdate();
  }

  /**
   * SELECT c.id, c.name FROM course
   *    FROM
   */

  @Override
  public List<CourseTeacher> getCourseByDateAndDay(final String startTime, final String endTime, final String weekDay) {
    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<CourseTeacher> query = builder.createQuery(CourseTeacher.class);
    final Root<CourseTeacher> root = query.from(CourseTeacher.class);

    final Join<CourseTeacher, Course> joinCourse = root.join(CourseTeacher_.course);

    query.multiselect(
            joinCourse.get(Course_.id),
            joinCourse.get(Course_.name));

    query.where(builder.or(builder.between(joinCourse.get(Course_.name), startTime, endTime)), builder.equal(root.get(CourseTeacher_.day), weekDay));

    return  em.createQuery(query).getResultList();
  }
}
