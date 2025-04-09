// -Gestion des étudiants

const studentsList = document.getElementById('students-list');
const form = document.getElementById('student-form');
const nameInput = document.getElementById('student-name');

window.onload = () => {
    loadStudents();
  };
  
function loadStudents() {
  fetch('/students')
    .then(res => res.json())
    .then(students => {
      displayStudents(students);
    });
}

function displayStudents(students) {
  const listHtml = students.map(s => `
    <div class="student-item">
      ${s.name}
      <button onclick="deleteStudent(${s.id})">Supprimer</button>
    </div>
  `).join('');
  studentsList.innerHTML = `<h2>Liste des étudiants</h2>${listHtml}`;
}

form.addEventListener('submit', e => {
  e.preventDefault();
  const name = nameInput.value.trim();
  if (!name) return;

  fetch('/students', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ name })
  })
    .then(() => {
      nameInput.value = '';
      loadStudents();
    });
});

function deleteStudent(id) {
  if (!confirm("Supprimer cet étudiant ?")) return;

  fetch(`/students/${id}`, {
    method: 'DELETE'
  }).then(() => {
    loadStudents();
  });
}

// -Gestion des notes

const studentSelect = document.getElementById('student-select');
const courseSelect = document.getElementById('course-select');
const gradeForm = document.getElementById('grade-form');
const gradeValueInput = document.getElementById('grade-value');
const gradesList = document.getElementById('grades-list');

function populateDropdowns(students, courses) {
  studentSelect.innerHTML = students.map(s => `<option value="${s.id}">${s.name}</option>`).join('');
  courseSelect.innerHTML = courses.map(c => `<option value="${c.id}">${c.name}</option>`).join('');
}

gradeForm.addEventListener('submit', e => {
  e.preventDefault();
  const studentId = studentSelect.value;
  const courseId = courseSelect.value;
  const value = parseFloat(gradeValueInput.value);

  fetch('/grades', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ studentId, courseId, value })
  })
    .then(() => {
      gradeValueInput.value = '';
      loadGradesByStudent(studentId);
    });
});

function loadGradesByStudent(studentId) {
  fetch(`/grades/student/${studentId}`)
    .then(res => res.json())
    .then(data => {
      displayGrades(data);
    });
}

function displayGrades(grades) {
  if (!grades || grades.length === 0) {
    gradesList.innerHTML = "<p>Aucune note disponible.</p>";
    return;
  }

  const html = grades.map(g => `
    <div>
      ${g.course.name || 'Cours inconnu'} : ${g.value}
      <button onclick="deleteGrade(${g.id}, ${g.studentId})">Supprimer</button>
    </div>
  `).join('');
  gradesList.innerHTML = `<h2>Notes de l'étudiant sélectionné</h2>${html}`;
}

function deleteGrade(gradeId, studentId) {
  if (!confirm("Supprimer cette note ?")) return;

  fetch(`/grades/${gradeId}`, {
    method: 'DELETE'
  }).then(() => {
    loadGradesByStudent(studentId);
  });
}

// -Gestion des cours

const courseForm = document.getElementById('course-form');
const courseNameInput = document.getElementById('course-name');
const coursesListDiv = document.getElementById('courses-list');

courseForm.addEventListener('submit', e => {
  e.preventDefault();
  const name = courseNameInput.value.trim();
  if (!name) return;

  fetch('/courses', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ name })
  })
    .then(() => {
      courseNameInput.value = '';
      loadCourses();
    });
});

function loadCourses() {
  fetch('/courses')
    .then(res => res.json())
    .then(data => {
      displayCourses(data);
      window.allCourses = data;
      if (window.allStudents) {
        populateDropdowns(window.allStudents, data);
      }
    });
}

function displayCourses(courses) {
  const html = courses.map(c => `
    <div>
      ${c.name}
      <button onclick="deleteCourse(${c.id})">Supprimer</button>
    </div>
  `).join('');
  coursesListDiv.innerHTML = `<h2>Liste des cours</h2>${html}`;
}

function deleteCourse(id) {
  if (!confirm("Supprimer ce cours ?")) return;

  fetch(`/courses/${id}`, {
    method: 'DELETE'
  }).then(() => {
    loadCourses();
  });
}
