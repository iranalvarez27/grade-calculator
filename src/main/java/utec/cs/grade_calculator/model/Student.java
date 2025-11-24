package utec.cs.grade_calculator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un estudiante con sus evaluaciones y estado de asistencia.
 */
public class Student {

    private String id;
    private boolean hasReachedMinimumClasses;
    private List<Evaluation> evaluations = new ArrayList<>();

    /**
     * Máximo número de evaluaciones permitidas (RNF01)
     */
    private static final int MAX_EVALUATIONS = 10;

    public Student(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del estudiante no puede estar vacío.");
        }
        this.id = id;
    }

    /**
     * Agrega una evaluación al estudiante.
     * @param eval La evaluación a agregar
     * @throws IllegalArgumentException si se excede el límite de 10 evaluaciones (RNF01)
     */
    public void addEvaluation(Evaluation eval) {
        if (evaluations.size() >= MAX_EVALUATIONS) {
            throw new IllegalArgumentException(
                "Un estudiante no puede tener más de " + MAX_EVALUATIONS + " evaluaciones."
            );
        }
        evaluations.add(eval);
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public boolean hasReachedMinimumClasses() {
        return hasReachedMinimumClasses;
    }

    public void setHasReachedMinimumClasses(boolean value) {
        this.hasReachedMinimumClasses = value;
    }

    public String getId() {
        return id;
    }
}