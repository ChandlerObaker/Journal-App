package com.chandlerobaker.alcchallenge.android.journalapp.database;


import java.util.Date;

public class DiaryEntry {
    private String diaryId;
    private String content;
    private int mood;
    private Date dateEnreg;
    private  Date dateLastModification;


    public DiaryEntry(String diaryId, String content, int mood, Date dateEnreg, Date dareLastModification) {
        this.diaryId = diaryId;
        this.content = content;
        this.mood = mood;
        this.dateEnreg = dateEnreg;
        this.dateLastModification = dareLastModification;
    }

    public DiaryEntry(String content, int mood, Date dateEnreg, Date dareLastModification) {
        this.content = content;
        this.mood = mood;
        this.dateEnreg = dateEnreg;
        this.dateLastModification = dareLastModification;
    }

    public DiaryEntry() {
    }

    public String getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(String diaryId) {
        this.diaryId = diaryId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public Date getDateEnreg() {
        return dateEnreg;
    }

    public void setDateEnreg(Date dateEnreg) {
        this.dateEnreg = dateEnreg;
    }

    public Date getDateLastModification() {
        return dateLastModification;
    }

    public void setDateLastModification(Date dateLastModification) {
        this.dateLastModification = dateLastModification;
    }
}
