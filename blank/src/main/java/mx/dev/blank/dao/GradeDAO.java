package mx.dev.blank.dao;


import mx.dev.blank.entity.Grade;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface GradeDAO {

    List<Grade> getNullGradeByStudentUuid(final String uuid);
}
