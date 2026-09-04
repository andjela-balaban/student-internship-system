use internship_db;

-- =============================================
-- USERS
-- =============================================

insert into user (username, password, email) values
('etf_bl', '$2a$10$DCmAFxxIknKk25EVlaW7x.KxWpwL.XAT8rtEM9sSTovqYbENvuzES', 'etf@unibl.org'),
('lanasoft', '$2a$10$DCmAFxxIknKk25EVlaW7x.KxWpwL.XAT8rtEM9sSTovqYbENvuzES', 'contact@lanasoft.com'),
('codecraft', '$2a$10$DCmAFxxIknKk25EVlaW7x.KxWpwL.XAT8rtEM9sSTovqYbENvuzES', 'contact@codecraft.com'),
('andjela.b', '$2a$10$DCmAFxxIknKk25EVlaW7x.KxWpwL.XAT8rtEM9sSTovqYbENvuzES', 'andjela@student.com'),
('marija.p', '$2a$10$DCmAFxxIknKk25EVlaW7x.KxWpwL.XAT8rtEM9sSTovqYbENvuzES', 'marija@student.com'),
('nikola.j', '$2a$10$DCmAFxxIknKk25EVlaW7x.KxWpwL.XAT8rtEM9sSTovqYbENvuzES', 'nikola@student.com'),
('ana.m', '$2a$10$DCmAFxxIknKk25EVlaW7x.KxWpwL.XAT8rtEM9sSTovqYbENvuzES', 'ana@student.com'),
('marko', '$2a$10$DCmAFxxIknKk25EVlaW7x.KxWpwL.XAT8rtEM9sSTovqYbENvuzES', 'marko@student.com');


-- =============================================
-- FACULTY
-- userId = 1
-- =============================================

insert into faculty (facultyId, name)
values
(1, 'Elektrotehnicki fakultet');


-- =============================================
-- COMPANIES
-- userId = 2, 3
-- =============================================

insert into company (companyId, name, isActive)
values
(2, 'LanaSoft', true),
(3, 'CodeCraft', true);


-- =============================================
-- STUDENTS
-- userId = 4, 5, 6, 7, 8
-- =============================================

insert into student (
    studentId,
    facultyId,
    firstName,
    lastName,
    studentIndex,
    dateOfBirth,
    phoneNumber
)
values
(4, 1, 'Andjela', 'Balaban', '1234/20', '2001-05-15', '065123456'),
(5, 1, 'Marija', 'Petrovic', '1235/20', '2002-03-20', '065234567'),
(6, 1, 'Nikola', 'Jovanovic', '1236/20', '2001-11-10', '065345678'),
(7, 1, 'Ana', 'Markovic', '1237/20', '2002-07-08', '065456789'),
(8, 1, 'Marko', 'Kovacevic', '1238/20', '2001-09-25', '065567890');


-- =============================================
-- CV
-- =============================================

insert into cv (studentId, interests, address)
values
(4, 'Web development, UI/UX design, mobile applications', 'Banja Luka'),
(5, 'Backend development, databases, cloud computing', 'Banja Luka'),
(6, 'Artificial intelligence, machine learning, Python', 'Prijedor'),
(7, 'Frontend development, UI design, React', 'Banja Luka'),
(8, 'Cyber security, networking, Linux', 'Doboj');


-- =============================================
-- EDUCATION
-- Pretpostavljamo da su cvId: 1-5
-- =============================================

insert into education (
    cvId,
    institution,
    degree,
    startDate,
    endDate
)
values
(1, 'Elektrotehnicki fakultet', 'Bachelor of Computer Science', '2020-10-01', null),
(2, 'Elektrotehnicki fakultet', 'Bachelor of Computer Science', '2020-10-01', null),
(3, 'Elektrotehnicki fakultet', 'Bachelor of Computer Science', '2020-10-01', null),
(4, 'Elektrotehnicki fakultet', 'Bachelor of Computer Science', '2020-10-01', null),
(5, 'Elektrotehnicki fakultet', 'Bachelor of Computer Science', '2020-10-01', null);


-- =============================================
-- EXPERIENCE
-- =============================================

insert into experience (
    cvId,
    company,
    position,
    startDate,
    endDate
)
values
(1, 'Freelance', 'Junior Web Developer', '2024-06-01', null),
(2, 'Tech Solutions', 'Junior Backend Developer', '2023-07-01', '2024-02-01'),
(3, 'AI Labs', 'Machine Learning Intern', '2024-06-01', '2024-09-01'),
(4, 'Design Studio', 'Frontend Intern', '2024-07-01', '2024-10-01');


-- =============================================
-- TECHNOLOGIES
-- =============================================

insert into technology (name)
values
('Java'),
('Spring Boot'),
('JavaScript'),
('React'),
('MySQL'),
('Python'),
('Docker'),
('Git'),
('HTML'),
('CSS');


-- =============================================
-- KNOWN TECHNOLOGIES
-- technologyId: 1-10
-- cvId: 1-5
-- =============================================

insert into known_technology (technologyId, cvId)
values
-- Andjela
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(8, 1),
(9, 1),
(10, 1),

-- Marija
(1, 2),
(2, 2),
(5, 2),
(7, 2),
(8, 2),

-- Nikola
(5, 3),
(6, 3),
(7, 3),
(8, 3),

-- Ana
(3, 4),
(4, 4),
(8, 4),
(9, 4),
(10, 4),

-- Marko
(1, 5),
(5, 5),
(7, 5),
(8, 5);


-- =============================================
-- INTERNSHIPS
-- companyId: 2 = LanaSoft
-- companyId: 3 = CodeCraft
-- =============================================

insert into internship (
    companyId,
    name,
    description,
    startDate,
    endDate,
    termsAndConditions
)
values
(
    2,
    'Java Developer Internship',
    'Internship focused on Java, Spring Boot and backend development.',
    '2026-07-01',
    '2026-09-30',
    'Basic knowledge of Java and SQL is required.'
),
(
    2,
    'Frontend Developer Internship',
    'Internship focused on modern frontend technologies and React.',
    '2026-07-01',
    '2026-09-30',
    'Knowledge of HTML, CSS and JavaScript is required.'
),
(
    3,
    'Full Stack Developer Internship',
    'Internship for students interested in both frontend and backend development.',
    '2026-06-15',
    '2026-09-15',
    'Knowledge of JavaScript and databases is preferred.'
),
(
    3,
    'DevOps Internship',
    'Internship focused on Docker, deployment and cloud technologies.',
    '2026-07-15',
    '2026-10-15',
    'Basic Linux and networking knowledge is required.'
);


-- =============================================
-- TECHNOLOGIES USED IN INTERNSHIPS
-- internshipId: 1-4
-- =============================================

insert into use_technology (technologyId, internshipId)
values
-- Java Developer Internship
(1, 1),
(2, 1),
(5, 1),
(8, 1),

-- Frontend Developer Internship
(3, 2),
(4, 2),
(9, 2),
(10, 2),

-- Full Stack Developer Internship
(1, 3),
(3, 3),
(4, 3),
(5, 3),
(8, 3),

-- DevOps Internship
(5, 4),
(7, 4),
(8, 4);


-- =============================================
-- APPLICATIONS
-- =============================================

insert into application (
    studentId,
    internshipId,
    status
)
values
(4, 1, 'ACCEPTED'),
(4, 3, 'PENDING'),
(5, 1, 'PENDING'),
(5, 4, 'REJECTED'),
(6, 3, 'ACCEPTED'),
(7, 2, 'ACCEPTED'),
(8, 4, 'PENDING');


-- =============================================
-- INTERNSHIP LOGS
-- =============================================

insert into internship_log (
    studentId,
    internshipId,
    facultyId,
    content,
    companyComment,
    facultyComment,
    lastLogEntry,
    grade
)
values
(
    4,
    1,
    1,
    'Student worked on implementing REST API endpoints using Spring Boot and MySQL.',
    'Good progress and active participation.',
    'Regularly submits internship reports.',
    '2026-07-15 12:00:00',
    null
),
(
    6,
    3,
    1,
    'Student worked on developing frontend and backend features for the application.',
    'Demonstrates good understanding of the assigned tasks.',
    null,
    '2026-07-20 14:30:00',
    null
),
(
    7,
    2,
    1,
    'Student implemented responsive UI components using React.',
    'Excellent work on the user interface.',
    'Progress is satisfactory.',
    '2026-07-22 10:15:00',
    null
);

select * from user;