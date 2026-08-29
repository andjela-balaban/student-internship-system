drop schema if exists internship_db;
create schema if not exists internship_db;
use internship_db;

create table user (
	userId int primary key auto_increment,
    username varchar(40) not null unique,
    password varchar(256) not null,
    email varchar(50) not null unique
);

create table faculty (
	facultyId int primary key,
    name varchar(50) not null,
    constraint FK_faculty_user
    foreign key (facultyId)
    references user(userId)
);

create table student (
	studentId int primary key,
    facultyId int not null,
    firstName varchar(40) not null,
    lastName varchar(40) not null,
    studentIndex varchar(15) not null,
    dateOfBirth date not null,
    phoneNumber varchar(15),
    constraint FK_student_user
    foreign key (studentId)
    references user(userId),
    constraint FK_student_faculty
    foreign key (facultyId)
    references faculty(facultyId)
);

create table company (
	companyId int primary key,
    name varchar(50) not null,
    isActive boolean not null,
    constraint FK_company_user
    foreign key (companyId)
    references user(userId)
);

create table cv (
	cvId int primary key auto_increment,
    studentId int not null unique,
    image mediumblob,
    interests varchar(150),
    address varchar(80), 
	constraint FK_cv_student
    foreign key (studentId)
    references student(studentId)
);

create table education (
	educationId int primary key auto_increment,
    cvId int not null,
    institution varchar(50) not null,
    degree varchar(100),
    startDate date,
    endDate date,
    constraint FK_education_cv
    foreign key (cvId)
    references cv(cvId)
);

create table experience (
	experienceId int primary key auto_increment,
    cvId int not null,
    company varchar(50) not null,
    position varchar(100),
    startDate date,
    endDate date,
    constraint FK_experience_cv
    foreign key (cvId)
    references cv(cvId)
);

create table technology (
	technologyId int primary key auto_increment,
    name varchar(30) not null
);

create table known_technology (
	technologyId int not null,
    cvId int not null,
    constraint PK_known_technology
    primary key (technologyId, cvId),
    constraint FK_known_technology_tehnology
    foreign key (technologyId)
    references technology(technologyId),
    constraint FK_known_tehnology_cv
    foreign key (cvId)
    references cv(cvId)
);

create table internship (
	internshipId int primary key auto_increment,
    companyId int not null,
    name varchar(50) not null,
    description varchar(500) not null,
    startDate date,
    endDate date,
    termsAndConditions varchar(500),
    constraint FK_internship_company
    foreign key (companyId)
    references company(companyId)
);

create table use_technology (
	technologyId int not null,
    internshipId int not null,
    constraint PK_use_technology
    primary key (technologyId, internshipId),
    constraint FK_use_technology_technology
    foreign key (technologyId)
    references technology(technologyId),
    constraint FK_use_technology_internship
    foreign key (internshipId)
    references internship(internshipId)
);

create table application (
	applicationId int primary key auto_increment,
    studentId int not null,
    internshipId int not null,
    status varchar(15) not null default "PENDING",
    constraint UNIQUE_student_internship
    unique (studentId, internshipId),
    constraint FK_application_internship
    foreign key (internshipId)
    references internship(internshipId),
    constraint FK_application_student
    foreign key (studentId)
    references student(studentId),
    constraint CHK_application_status
	check (status in ("PENDING", "ACCEPTED", "REJECTED"))
);

create table internship_log (
	logId int primary key auto_increment,
    studentId int not null,
    internshipId int not null,
    facultyId int not null,
    content text not null,
    companyComment text,
    facultyComment text,
    lastLogEntry timestamp,
    grade int,
    constraint UNIQUE_student_internship_faculty
	unique (studentId, internshipId, facultyId),
	constraint FK_internship_log_student
	foreign key (studentId)
	references student(studentId),
    constraint FK_internship_log_internship
	foreign key (internshipId)
	references internship(internshipId),
	constraint FK_internship_log_faculty
	foreign key (facultyId)
	references faculty(facultyId)
);
