package com.doanjava.gradehub.model;

public class StudentGradeModel {
    private String subjectCode;
    private String subjectName;
    private int credits;
    private double midterm;
    private double finalExam;
    private String academicYear;
    private String semester;

    public StudentGradeModel(String subjectCode, String subjectName, int credits, double midterm, double finalExam, String academicYear, String semester) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credits = credits;
        this.midterm = midterm;
        this.finalExam = finalExam;
        this.academicYear = academicYear;
        this.semester = semester;

    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getCredits() {
        return credits;
    }

    public double getMidterm() {
        return midterm;
    }

    public double getFinalExam() {
        return finalExam;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public String getSemester() {
        return semester;
    }

    public double getTotal() {
        return midterm * 0.3 + finalExam * 0.7;
    }
}
