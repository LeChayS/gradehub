package com.doanjava.gradehub.model;

public class StudentGradeModel {
    private String subjectCode;
    private String subjectName;
    private int credits;
    private double midterm;
    private double finalExam;
    private double total;

    public StudentGradeModel(String subjectCode, String subjectName, int credits, double midterm, double finalExam) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credits = credits;
        this.midterm = midterm;
        this.finalExam = finalExam;
    }

    // public StudentGradeModel(String subjectCode, String subjectName, int credits, double midterm, double finalExam,
    //         double total) {
    //     this.subjectCode = subjectCode;
    //     this.subjectName = subjectName;
    //     this.credits = credits;
    //     this.midterm = midterm;
    //     this.total = total;
    // }

    // Getters & Setters (JavaFX cần getter để binding vào TableView)

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

    public double getTotal() {
        double midtermExam = getMidterm();
        double finalExam = getFinalExam();

        return midtermExam * 0.3 + finalExam * 0.7;
    }
}
