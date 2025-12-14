package io.github.some_example_name;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.List;

/* A hidden event that triggers an exam popup when the player enters the zone.
 * 
 * The exam consists of 5 questions. If the player gets 3 or more correct (>=50%),
 * they receive bonus points. If they get less than 3 correct, they lose points.
 * 
 * This is a hidden event (invisible trigger zone, like TripwireEvent).
 */
public class ExamEvent implements Event {
    private String name;
    private boolean isTriggered;
    private Rectangle examZone;
    private EventCounter eventCounter;
    private List<Question> questions;
    private boolean examActive = false;
    
    /* Constructor
     * @param name - Name of the event
     * @param zone - Position of the event (invisible trigger zone)
     * @param eventCounter - Event counter to track triggered events
     */
    public ExamEvent(String name, Rectangle zone, EventCounter eventCounter) {
        this.name = name;
        this.examZone = zone;
        this.isTriggered = false;
        this.eventCounter = eventCounter;
        this.questions = new ArrayList<>();
        initializeQuestions();
    }
    
    /* EDIT HERE: Initialize your 5 questions
     * Format: new Question("Question text?", "Option 1", "Option 2", "Option 3", "Option 4", correctIndex)
     * correctIndex: 0 = Option 1, 1 = Option 2, 2 = Option 3, 3 = Option 4
     */
    private void initializeQuestions() {
        // Question 1
        questions.add(new Question(
            "How many modules are in Semester 1?",  // EDIT: Question text
            "4",                // EDIT: Option 1
            "3",                // EDIT: Option 2
            "5",                // EDIT: Option 3
            "2",                // EDIT: Option 4
            1                   // EDIT: Correct answer index (0-3)
        ));
        
        // Question 2
        questions.add(new Question(
            "What is the correct way to declare a pointer to an int in C?",  // EDIT: Question text
            "int *ptr;",                          // EDIT: Option 1
            "pointer int ptr;",                   // EDIT: Option 2
            "int ptr;",                           // EDIT: Option 3
            "int &ptr;",                          // EDIT: Option 4
            2                                  // EDIT: Correct answer index (0-3)
        ));
        
        // Question 3
        questions.add(new Question(
            "Which collection does NOT allow duplicate elements?",  // EDIT: Question text
            "ArrayList",              // EDIT: Option 1
            "HashSet",              // EDIT: Option 2
            "Vector",              // EDIT: Option 3
            "LinkedList",              // EDIT: Option 4
            1                  // EDIT: Correct answer index (0-3)
        ));
        
        // Question 4
        questions.add(new Question(
            "What does static mean for a method in Java?",  // EDIT: Question text
            "It is a keyword for creating static methods",                     // EDIT: Option 1
            "It can be accessed without creating an instance",                   // EDIT: Option 2
            "It is a keyword for creating static variables",                    // EDIT: Option 3
            "It belongs to the class, not instances",                  // EDIT: Option 4
            3                          // EDIT: Correct answer index (0-3)
        ));
        
        // Question 5
        questions.add(new Question(
            "How many days in a week?",  // EDIT: Question text
            "5",                         // EDIT: Option 1
            "6",                         // EDIT: Option 2
            "7",                         // EDIT: Option 3
            "8",                         // EDIT: Option 4
            2                            // EDIT: Correct answer index (0-3)
        ));
    }
    
    public boolean checkTrigger(Rectangle playerCollision) {
        if (!isTriggered && examZone.overlaps(playerCollision)) {
            trigger();
            examActive = true;
            return true;
        }
        return false;
    }
    
    @Override
    public void trigger() {
        if (!isTriggered) {
            eventCounter.incrementHidCounter();
            isTriggered = true;
        }
    }
    
    public List<Question> getQuestions() {
        return questions;
    }
    
    public boolean isExamActive() {
        return examActive;
    }
    
    public void setExamActive(boolean active) {
        this.examActive = active;
    }
    
    public Rectangle getExamZone() {
        return examZone;
    }
    
    // Interface methods
    @Override public String getName() { return name; }
    @Override public void setName(String name) { this.name = name; }
    @Override public boolean isTriggered() { return isTriggered; }
    @Override public void setTriggered(boolean triggered) { this.isTriggered = triggered; }
}

