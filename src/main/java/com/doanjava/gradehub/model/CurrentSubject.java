package com.doanjava.gradehub.model;

public class CurrentSubject {
    private String subjectCode;
    private String subjectName;
    private String subjectClass;
    private String teacherName;

    public CurrentSubject(String subjectCode, String subjectName, String subjectClass, String teacherName) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.subjectClass = subjectClass;
        this.teacherName = teacherName;
    }
    public String getSubjectCode() {
        return subjectCode;
    }
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
    public String getSubjectName() {
        return subjectName;
    }
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    public String getSubjectClass() {
        return subjectClass;
    }
    public void setSubjectClass(String subjectClass) {
        this.subjectClass = subjectClass;
    }
    public String getTeacherName() {
        return teacherName;
    }
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}