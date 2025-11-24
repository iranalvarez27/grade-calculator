package utec.cs.grade_calculator.policy;

import utec.cs.grade_calculator.model.Student;

/**
 * Política de puntos extra por año académico.
 * Define si se permiten puntos adicionales y cuántos.
 */
public class ExtraPointsPolicy {

    private boolean allowExtraPoints;
    private static final double EXTRA_POINTS_VALUE = 1.0;
    private static final double MAX_GRADE = 20.0;

    /**
     * Constructor de la política.
     * @param allowExtraPoints Si se permiten puntos extra en este año académico
     */
    public ExtraPointsPolicy(boolean allowExtraPoints) {
        this.allowExtraPoints = allowExtraPoints;
    }

    /**
     * Aplica puntos extra si la política lo permite.
     * @param student El estudiante
     * @param partialGrade La nota parcial calculada
     * @return La nota con puntos extra aplicados (máximo 20)
     */
    public double applyExtraPoints(Student student, double partialGrade) {
        if (!allowExtraPoints) {
            return partialGrade;
        }
        double result = partialGrade + EXTRA_POINTS_VALUE;
        return Math.min(MAX_GRADE, result);
    }

    /**
     * Retorna cuántos puntos extra se aplicaron.
     * @param student El estudiante
     * @param partialGrade La nota antes de aplicar extra
     * @return Cantidad de puntos aplicados
     */
    public double getExtraPointsApplied(Student student, double partialGrade) {
        if (!allowExtraPoints || partialGrade >= MAX_GRADE) {
            return 0.0;
        }
        double possibleExtra = partialGrade + EXTRA_POINTS_VALUE;
        if (possibleExtra > MAX_GRADE) {
            return MAX_GRADE - partialGrade;
        }
        return EXTRA_POINTS_VALUE;
    }
}