package ch.wiss.wiss_quiz.model;

import java.util.List;

public class FrontendQuestion {
  private Integer id;
  private String question;
  private List<String> answers;
  private String correct_answer;

  public FrontendQuestion(Question question) {
    this.id = question.getId();
    this.question = question.getQuestion();
    this.answers = question.getAnswers().stream().map(Answer::getAnswer).toList();
    this.correct_answer = question.getAnswers().stream().filter(Answer::getCorrect).findFirst().get().getAnswer();
  }

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getQuestion() {
    return question;
  }
  public void setQuestion(String question) {
    this.question = question;
  }
  public List<String> getAnswers() {
    return answers;
  }
  public void setAnswers(List<String> answers) {
    this.answers = answers;
  }
  public String getCorrect_answer() {
    return correct_answer;
  }
  public void setCorrect_answer(String correct_answer) {
    this.correct_answer = correct_answer;
  }


}
