create table student
(
   id integer not null,
   name varchar(255) not null,
   authHash varchar(255) not null,
   primary key(id)
);

create table schoolclass
(
   id integer not null,
   schoolClassName varchar(255) not null,
   schoolClassDescription varchar(255) not null,
   primary key(id)
);

create table classenrolments
(
  id integer not null auto_increment,
  schoolClassId integer not null,
  studentId integer not null,
  foreign key(schoolClassId) references schoolclass(id),
  foreign key(studentId) references student(id),
  primary key(id)
);