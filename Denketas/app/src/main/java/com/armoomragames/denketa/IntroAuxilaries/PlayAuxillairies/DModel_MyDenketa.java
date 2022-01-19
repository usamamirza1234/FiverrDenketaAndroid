package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies;

import android.os.Parcel;
import android.os.Parcelable;

public class DModel_MyDenketa implements Parcelable {
    String strName;
    String Answer;
    String AnswerImage;
    String hint;
    String learnmore;
    String StrId;
    String strImage;
    String Question;


    public DModel_MyDenketa() {
    }

    public DModel_MyDenketa(String strName, String strId, String strImage) {
        this.strName = strName;
        StrId = strId;
        this.strImage = strImage;
    }

    public DModel_MyDenketa(String strName, String strId, String strImage, String Question,String Answer, String AnswerImage, String hint, String learnmore) {
        this.strName = strName;
        StrId = strId;
        this.strImage = strImage;
        this.Answer = Answer;
        this.AnswerImage = AnswerImage;
        this.hint = hint;
        this.Question = Question;
        this.learnmore = learnmore;
    }

    public String getStrId() {
        return StrId;
    }

    public void setStrId(String strId) {
        StrId = strId;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public String getStrImage() {
        return strImage;
    }

    public void setStrImage(String strImage) {
        this.strImage = strImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getAnswerImage() {
        return AnswerImage;
    }

    public void setAnswerImage(String answerImage) {
        AnswerImage = answerImage;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getLearnmore() {
        return learnmore;
    }

    public void setLearnmore(String learnmore) {
        this.learnmore = learnmore;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }
}
