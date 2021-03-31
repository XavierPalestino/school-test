package mx.dev.blank.dao;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import lombok.Data;
import mx.dev.blank.DAOTestConfig;
import mx.dev.blank.DBTestConfig;
import mx.dev.blank.entity.Course;
import mx.dev.blank.entity.CourseTeacher;
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
public class CourseJpaDAOTest extends AbstractTransactionalTestNGSpringContextTests {

    private static final String DBUNIT_XML = "classpath:dbunit/dao/teacher.xml";

    @Autowired
    private CourseJpaDAO courseJpaDAO;

    @DataProvider
    public Object[][] getCourseByDateAndDay_dataProvider() {
        List<String> courseNames = Lists.newArrayList("HISTORIA");

        return new Object[][] {
                {"10:00:00", "11:30:00","MONDAY"}
        };
    }

    /*@Test(dataProvider = "getCourseByDateAndDay_dataProvider")
    @DatabaseSetup(DBUNIT_XML)
    public void getByDate_should_returnCourse(final String startTime,
                                              final String endTime,
                                              final String weekDay,
                                              final List<String> expectedNames) {
        final List<CourseTeacher> course = courseJpaDAO.getCourseByDateAndDay(startTime, endTime, weekDay);

        assertThat(course).extracting(Course -> Course.getId()).hasSameElementsAs(expectedNames);

    }*/
}