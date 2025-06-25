package com.doanjava.gradehub.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class TeacherDashboardFakeData {
    // --- Data Model ---
    public static class Lesson {
        public final int lessonNumber;
        public final LocalDate date;
        public Lesson(int lessonNumber, LocalDate date) {
            this.lessonNumber = lessonNumber;
            this.date = date;
        }
    }
    public static class ClassInfo {
        public final String className;
        public final List<Lesson> lessons;
        public ClassInfo(String className, List<Lesson> lessons) {
            this.className = className;
            this.lessons = lessons;
        }
    }
    public static class SubjectInfo {
        public final String subjectName;
        public final String subjectCode;
        public final Map<String, List<ClassInfo>> semesterToClasses; // semester -> classes
        public SubjectInfo(String subjectName, String subjectCode, Map<String, List<ClassInfo>> semesterToClasses) {
            this.subjectName = subjectName;
            this.subjectCode = subjectCode;
            this.semesterToClasses = semesterToClasses;
        }
    }
    public static class YearInfo {
        public final String year;
        public final Map<String, SubjectInfo> subjects; // subjectCode -> SubjectInfo
        public YearInfo(String year, Map<String, SubjectInfo> subjects) {
            this.year = year;
            this.subjects = subjects;
        }
    }

    // --- Fake Data Generation ---
    private static final List<String> SUBJECTS = Arrays.asList("Toán rời rạc:MTH101", "Cấu trúc dữ liệu:CS202", "Lập trình Java:CS303");
    private static final List<String> CLASS_NAMES = Arrays.asList("CTK43A", "CTK43B", "CTK43C");
    private static final List<String> SEMESTERS = Arrays.asList("1", "2", "3");
    private static final List<String> YEARS = Arrays.asList("2022-2023", "2023-2024", "2024-2025");
    private static final Map<String, YearInfo> DATA = new LinkedHashMap<>();
    static {
        Random rand = new Random(42);
        // Academic year start/end mapping
        Map<String, LocalDate[]> yearRanges = new LinkedHashMap<>();
        yearRanges.put("2022-2023", new LocalDate[]{LocalDate.of(2022, 9, 1), LocalDate.of(2023, 6, 30)});
        yearRanges.put("2023-2024", new LocalDate[]{LocalDate.of(2023, 9, 1), LocalDate.of(2024, 6, 30)});
        yearRanges.put("2024-2025", new LocalDate[]{LocalDate.of(2024, 9, 1), LocalDate.of(2025, 6, 30)});
        for (String year : YEARS) {
            Map<String, SubjectInfo> subjectMap = new LinkedHashMap<>();
            LocalDate[] range = yearRanges.get(year);
            LocalDate yearStart = range[0];
            LocalDate yearEnd = range[1];
            for (String subj : SUBJECTS) {
                String[] parts = subj.split(":");
                String subjectName = parts[0];
                String subjectCode = parts[1];
                Map<String, List<ClassInfo>> semesterToClasses = new LinkedHashMap<>();
                for (int semIdx = 0; semIdx < SEMESTERS.size(); semIdx++) {
                    String semester = SEMESTERS.get(semIdx);
                    List<ClassInfo> classInfos = new ArrayList<>();
                    // Semester date range (split year into 3 parts)
                    long totalDays = yearEnd.toEpochDay() - yearStart.toEpochDay();
                    long semStartDay = yearStart.toEpochDay() + (totalDays * semIdx) / 3;
                    long semEndDay = yearStart.toEpochDay() + (totalDays * (semIdx + 1)) / 3 - 1;
                    LocalDate semStart = LocalDate.ofEpochDay(semStartDay);
                    LocalDate semEnd = LocalDate.ofEpochDay(semEndDay);
                    for (String className : CLASS_NAMES) {
                        int lessonCount = rand.nextInt(3) + 2; // 2-4 lessons
                        List<Lesson> lessons = new ArrayList<>();
                        long semDays = semEnd.toEpochDay() - semStart.toEpochDay();
                        for (int l = 1; l <= lessonCount; l++) {
                            LocalDate date = semStart.plusDays(rand.nextInt((int) semDays + 1));
                            lessons.add(new Lesson(l, date));
                        }
                        classInfos.add(new ClassInfo(className, lessons));
                    }
                    semesterToClasses.put(semester, classInfos);
                }
                subjectMap.put(subjectCode, new SubjectInfo(subjectName, subjectCode, semesterToClasses));
            }
            DATA.put(year, new YearInfo(year, subjectMap));
        }
    }

    // --- ComboBox Data ---
    public static ObservableList<String> getYears() {
        return FXCollections.observableArrayList(YEARS);
    }
    public static ObservableList<String> getSemesters() {
        return FXCollections.observableArrayList(SEMESTERS);
    }
    public static ObservableList<String> getSubjects(String year) {
        if (year == null || !DATA.containsKey(year)) return FXCollections.observableArrayList();
        return FXCollections.observableArrayList(DATA.get(year).subjects.values().stream().map(s -> s.subjectName + " (" + s.subjectCode + ")").collect(Collectors.toList()));
    }
    public static ObservableList<String> getClasses(String year, String subjectCode, String semester) {
        if (year == null || subjectCode == null || semester == null) return FXCollections.observableArrayList();
        YearInfo y = DATA.get(year);
        if (y == null) return FXCollections.observableArrayList();
        SubjectInfo s = y.subjects.get(subjectCode);
        if (s == null) return FXCollections.observableArrayList();
        List<ClassInfo> classes = s.semesterToClasses.get(semester);
        if (classes == null) return FXCollections.observableArrayList();
        return FXCollections.observableArrayList(classes.stream().map(c -> c.className).collect(Collectors.toList()));
    }
    public static List<Lesson> getLessons(String year, String subjectCode, String semester, String className) {
        if (year == null || subjectCode == null || semester == null || className == null) return Collections.emptyList();
        YearInfo y = DATA.get(year);
        if (y == null) return Collections.emptyList();
        SubjectInfo s = y.subjects.get(subjectCode);
        if (s == null) return Collections.emptyList();
        List<ClassInfo> classes = s.semesterToClasses.get(semester);
        if (classes == null) return Collections.emptyList();
        for (ClassInfo c : classes) {
            if (c.className.equals(className)) return c.lessons;
        }
        return Collections.emptyList();
    }
    // Helper to get subject code from display string
    public static String extractSubjectCode(String display) {
        if (display == null) return null;
        int idx = display.lastIndexOf('(');
        int idx2 = display.lastIndexOf(')');
        if (idx >= 0 && idx2 > idx) return display.substring(idx + 1, idx2);
        return display;
    }

    // --- Table Data for Dashboard ---
    public static void populateOverview(Label totalClassesLabel, Label totalSubjectsLabel, Label totalStudentsLabel) {
        totalClassesLabel.setText(String.valueOf(CLASS_NAMES.size()));
        totalSubjectsLabel.setText(String.valueOf(SUBJECTS.size()));
        totalStudentsLabel.setText("120");
    }
    public static void populateSubjectsTable(TableView<SubjectRow> tableView, String year, String semester) {
        ObservableList<SubjectRow> data = FXCollections.observableArrayList();
        if (year != null && semester != null && DATA.containsKey(year)) {
            YearInfo y = DATA.get(year);
            for (SubjectInfo s : y.subjects.values()) {
                List<ClassInfo> classes = s.semesterToClasses.get(semester);
                if (classes != null) {
                    for (ClassInfo c : classes) {
                        data.add(new SubjectRow(s.subjectName, s.subjectCode, year + " HK" + semester, c.className));
                    }
                }
            }
        }
        tableView.setItems(data);
    }
    public static void populateGradeManagementTable(TableView<GradeManagementRow> tableView, String year, String semester) {
        ObservableList<GradeManagementRow> data = FXCollections.observableArrayList();
        if (year != null && semester != null && DATA.containsKey(year)) {
            YearInfo y = DATA.get(year);
            for (SubjectInfo s : y.subjects.values()) {
                List<ClassInfo> classes = s.semesterToClasses.get(semester);
                if (classes != null) {
                    for (ClassInfo c : classes) {
                        data.add(new GradeManagementRow(s.subjectName, c.className, year + " HK" + semester, "Chưa nhập đủ điểm"));
                    }
                }
            }
        }
        tableView.setItems(data);
    }
    // --- Student Score Table ---
    public static ObservableList<StudentScoreRow> getStudentScores() {
        // For demo, return static students
        return FXCollections.observableArrayList(
            new StudentScoreRow("SV001", "Nguyễn Văn A", 8.5, 7.5, 9.0),
            new StudentScoreRow("SV002", "Trần Thị B", 7.0, 8.0, 8.5),
            new StudentScoreRow("SV003", "Lê Văn C", 9.2, 8.8, 9.5),
            new StudentScoreRow("SV004", "Phạm Minh D", 6.5, 7.0, 7.5),
            new StudentScoreRow("SV005", "Hoàng Thị E", 8.0, 8.5, 8.8)
        );
    }

    public static class SubjectRow {
        private final String subjectName;
        private final String subjectCode;
        private final String semester;
        private final String className;
        public SubjectRow(String subjectName, String subjectCode, String semester, String className) {
            this.subjectName = subjectName;
            this.subjectCode = subjectCode;
            this.semester = semester;
            this.className = className;
        }
        public String getSubjectName() { return subjectName; }
        public String getSubjectCode() { return subjectCode; }
        public String getSemester() { return semester; }
        public String getClassName() { return className; }
    }

    public static class GradeManagementRow {
        private final String subjectName;
        private final String className;
        private final String semester;
        private final String status;
        public GradeManagementRow(String subjectName, String className, String semester, String status) {
            this.subjectName = subjectName;
            this.className = className;
            this.semester = semester;
            this.status = status;
        }
        public String getSubjectName() { return subjectName; }
        public String getClassName() { return className; }
        public String getSemester() { return semester; }
        public String getStatus() { return status; }
    }

    public static class StudentScoreRow {
        private final String studentId;
        private final String studentName;
        private Double attendanceScore;
        private Double midtermScore;
        private Double finalScore;
        public StudentScoreRow(String studentId, String studentName, Double attendanceScore, Double midtermScore, Double finalScore) {
            this.studentId = studentId;
            this.studentName = studentName;
            this.attendanceScore = attendanceScore;
            this.midtermScore = midtermScore;
            this.finalScore = finalScore;
        }
        public String getStudentId() { return studentId; }
        public String getStudentName() { return studentName; }
        public Double getAttendanceScore() { return attendanceScore; }
        public void setAttendanceScore(Double score) { this.attendanceScore = score; }
        public Double getMidtermScore() { return midtermScore; }
        public void setMidtermScore(Double score) { this.midtermScore = score; }
        public Double getFinalScore() { return finalScore; }
        public void setFinalScore(Double score) { this.finalScore = score; }
    }
}