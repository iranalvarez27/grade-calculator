package utec.cs.grade_calculator.model;

/**
 * Representa una evaluación individual del estudiante.
 * Contiene la nota obtenida y su peso en la nota final.
 */
public class Evaluation {

    private double grade;
    private double weight;

    /**
     * Constructor con validaciones.
     * @param grade Nota obtenida (debe estar entre 0 y 20)
     * @param weight Peso de la evaluación (debe estar entre 0 y 1)
     * @throws IllegalArgumentException si los valores están fuera de rango
     */
    public Evaluation(double grade, double weight) {
        if (grade < 0 || grade > 20) {
            throw new IllegalArgumentException("La nota debe estar entre 0 y 20.");
        }
        if (weight < 0 || weight > 1) {
            throw new IllegalArgumentException("El peso debe estar entre 0 y 1.");
        }
        this.grade = grade;
        this.weight = weight;
    }

    public double getGrade() {
        return grade;
    }

    public double getWeight() {
        return weight;
    }
}