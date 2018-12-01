package social.webifyme.varun.socialapp.models;

public class Posts {
String questions,answers;
int images,id;

    public Posts() {
    }

    public Posts(String questions, String answers,int id, int images) {
        this.id = id;
        this.questions = questions;
        this.answers = answers;
        this.images=images;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImages(int images) {
        this.images = images;
    }

    public String getAnswers() {
        return answers;
    }

    public String getQuestions() {
        return questions;
    }

    public int getId() {
        return id;
    }

    public int getImages() {
        return images;
    }
}
