
ALTER TABLE student (
  DROP COLUMN   course_teacher_id int(20) UNSIGNED NOT NULL,
  DROP CONSTRAINT student_course_teacher_id_fk
) ENGINE=InnoDB;

CREATE TABLE course_student (
  id int(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  student_id int(20) UNSIGNED NOT NULL,
  course_teacher_id int(20) UNSIGNED NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT course_student_student_id_fk FOREIGN KEY (student_id) REFERENCES student (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT course_student_course_teacher_id_fk FOREIGN KEY (course_teacher_id) REFERENCES course_teacher (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB


ALTER TABLE room (
  ADD COLUMN laboratory varchar(1) NOT NULL
) ENGINE=InnoDB;

ALTER TABLE course_teacher (
  ADD INDEX course_teacher_day (day),
) ENGINE=InnoDB;

ALTER TABLE course (
  ADD UNIQUE course_keycode_uk (keycode)
) ENGINE=InnoDB;

ALTER TABLE teacher (
  ALTER COLUMN second_surname text NOT NULL
) ENGINE=InnoDB;

ADD TABLE grade (
  id  int(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  student_id int(20) UNSIGNED NOT NULL,
  course_teacher_id int(20) UNSIGNED NOT NULL,
  student_grade  tinyint(3),
  register_date date,
  PRIMARY KEY (id),
  CONSTRAINT grade_course_teacher_id_fk FOREIGN KEY (course_teacher_id) REFERENCES course_teacher (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT grade_student_id_fk FOREIGN KEY (student_id) REFERENCES student (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;