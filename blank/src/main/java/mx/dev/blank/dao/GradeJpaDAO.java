package mx.dev.blank.dao;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import mx.dev.blank.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

@RequiredArgsConstructor
public class GradeJpaDAO implements GradeDAO {

    @Setter(onMethod = @__(@PersistenceContext), value = AccessLevel.PACKAGE)
    private EntityManager em;


   @Override
    public List<Grade> getNullGradeByStudentUuid(final String uuid) {
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<Grade> query = builder.createQuery(Grade.class);
        final Root<Grade> root = query.from(Grade.class);

        final Join<Grade, Student> joinStudent = root.join(Grade_.studentId);
        final Join<Grade, CourseTeacher> joinCourse = root.join(Grade_.courseTeacherId);

        query.multiselect(
                joinCourse.get(CourseTeacher_.course),
                root.get(Grade_.studentGrade));

        query.where(builder.and(builder.equal(joinStudent.get(Student_.uuid), uuid), builder.equal(root.get(Grade_.studentGrade), 0)));

        return em.createQuery(query).getResultList();
    }

   /* @Override
    public Grade getHighAvgByDay(final String day) {
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<Grade> query = builder.createQuery(Grade.class);
        final Root<Grade> root = query.from(Grade.class);

        final Join<Grade, CourseTeacher> joinCourse = root.join(Grade_.courseTeacherId);

       // query.select(root).where(builder.and(builder.equal(joinCourse.get(CourseTeacher_.day), day), builder.(joinCourse.get(Grade_.studentGrade))));

    return em.createQuery(query).getSingleResult();
    }*/
}
