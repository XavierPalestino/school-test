package mx.dev.blank.dao;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import lombok.Data;
import mx.dev.blank.DAOTestConfig;
import mx.dev.blank.DBTestConfig;
import mx.dev.blank.entity.Course;
import mx.dev.blank.entity.CourseTeacher;
import mx.dev.blank.entity.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Repository
@ContextConfiguration(classes = {DAOTestConfig.class, DBTestConfig.class})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class GradeJpaDAOTest extends AbstractTransactionalTestNGSpringContextTests {

    private static final String DBUNIT_XML = "classpath:dbunit/dao/teacher.xml";

    @Autowired
    private GradeJpaDAO gradeJpaDAO;

    @DataProvider
    public Object[][] getNullGradeByStudentUuid() {
        List<String> courseNames = Lists.newArrayList("HISTORIA");

        return new Object[][] {
                {"10:00:00", "11:30:00","MONDAY"}
        };
    }

    @Test(dataProvider = "getNullGradeByStudentUuid_dataProvider")
    @DatabaseSetup(DBUNIT_XML)
    public void getByNullGrade_should_returnCourse(final String uuid,
                                              final List<Integer> expectedNames) {
        final List<Grade> grade = gradeJpaDAO.getNullGradeByStudentUuid(uuid);

        assertThat(grade).extracting(Grade -> Grade.getId()).hasSameElementsAs(expectedNames);

    }
}