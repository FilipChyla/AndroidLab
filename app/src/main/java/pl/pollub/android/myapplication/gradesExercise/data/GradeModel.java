package pl.pollub.android.myapplication.gradesExercise.data;

public class GradeModel {
    private String description = "Niedostateczny";
    private int grade = 2;

    public GradeModel() {
    }

    public GradeModel(String description, int grade) {
        this.description = description;
        this.grade = grade;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "GradeModel{" +
                "description='" + description + '\'' +
                ", grade=" + grade +
                '}';
    }
}
