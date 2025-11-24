package utec.cs.grade_calculator.policy;

import utec.cs.grade_calculator.model.Student;

/**
 * Política de asistencia mínima.
 * Si el estudiante no cumple con la asistencia mínima, su nota final es 0.
 */
public class AttendancePolicy {

    /**
     * Aplica la política de asistencia sobre la nota parcial.
     * @param student El estudiante
     * @param partialGrade La nota parcial calculada
     * @return 0 si no alcanzó asistencia mínima, caso contrario retorna la nota parcial
     */
    public double applyAttendance(Student student, double partialGrade) {
        if (!student.hasReachedMinimumClasses()) {
            return 0.0;
        }
        return partialGrade;
    }

    /**
     * Retorna la penalización aplicada por inasistencia.
     * @param student El estudiante
     * @param partialGrade La nota parcial
     * @return El valor de la penalización
     */
    public double getPenalty(Student student, double partialGrade) {
        if (!student.hasReachedMinimumClasses()) {
            return partialGrade; // Se penaliza con la nota completa
        }
        return 0.0;
    }
}