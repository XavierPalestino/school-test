package mx.dev.blank.dao;
import static org.assertj.core.api.Assertions.assertThat;
import com.beust.jcommander.internal.Lists;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import mx.dev.blank.DAOTestConfig;
import mx.dev.blank.DBTestConfig;
import mx.dev.blank.entity.CourseTeacher;
import mx.dev.blank.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

@ContextConfiguration(classes = {DAOTestConfig.class, DBTestConfig.class})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class StudentJpaDAOTest extends AbstractTransactionalTestNGSpringContextTests {

    private static final String DBUNIT_XML = "classpath:dbunit/dao/teacher.xml";

    @Autowired
    private StudentJpaDAO studentJpaDAO;

    @DataProvider
    public Object[][] getAssignedCourseByUuid_dataProvider() {
        List<Integer> studentsIDs = Lists.newArrayList(1);


        return new Object[][] {
                {"18011190", studentsIDs}
        };
    }

    @Test(dataProvider = "getAssignedCourseByUuid_dataProvider")
    @DatabaseSetup(DBUNIT_XML)
    public void getByUuid_should_returnCourse(final String uuidQuery,
                                             final List<Integer> expectedIDs) {
        final List<CourseTeacher> students = studentJpaDAO.getAssignedCourseByUuid(uuidQuery);

        assertThat(students).extracting(Student -> Student.getId()).hasSameElementsAs(expectedIDs);
    }

}