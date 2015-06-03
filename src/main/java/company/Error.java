package company;

/**
 * Created by Necros on 03.06.2015.
 */
public class Error {
    private Human human;
    private Treatment treatment;

    public Error(Human human, Treatment treatment, String error) {
        this.human = human;
        this.treatment = treatment;
        this.error = error;
    }

    public Human getHuman() {

        return human;
    }

    public void setHuman(Human human) {
        this.human = human;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    private String error;
}
