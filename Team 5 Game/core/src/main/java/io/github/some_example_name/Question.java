package io.github.some_example_name;

/* This class represents a single exam question with 4 answer options.
 * 
 * To add a question:
 * 1. Set the question text in 'questionText'
 * 2. Set the 4 answer options in 'option1', 'option2', 'option3', 'option4'
 * 3. Set 'correctAnswerIndex' to 0, 1, 2, or 3 (0 = option1, 1 = option2, etc.)
 */
public class Question {
    // EDIT HERE: Question text
    public String questionText;
    
    // EDIT HERE: Four answer options
    public String option1;
    public String option2;
    public String option3;
    public String option4;
    
    // EDIT HERE: Index of correct answer (0 = option1, 1 = option2, 2 = option3, 3 = option4)
    public int correctAnswerIndex;
    
    public Question(String questionText, String option1, String option2, String option3, String option4, int correctAnswerIndex) {
        this.questionText = questionText;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctAnswerIndex = correctAnswerIndex;
    }
    
    public String[] getOptions() {
        return new String[]{option1, option2, option3, option4};
    }
    
    public boolean isCorrect(int selectedIndex) {
        return selectedIndex == correctAnswerIndex;
    }
}

