package utec.cs.grade_calculator.service;

import utec.cs.grade_calculator.model.Evaluation;
import utec.cs.grade_calculator.model.Student;
import utec.cs.grade_calculator.policy.AttendancePolicy;
import utec.cs.grade_calculator.policy.ExtraPointsPolicy;

/**
 * Servicio principal para calcular la nota final del estudiante.
 * Aplica el promedio ponderado, políticas de asistencia y puntos extra.
 */
public class GradeCalculator {

    /**
     * Calcula la nota final del estudiante aplicando todas las políticas.
     * @param student El estudiante
     * @param attendance Política de asistencia
     * @param extra Política de puntos extra
     * @return La nota final calculada
     * @throws IllegalArgumentException si hay errores en los datos
     */
    public double calculate(Student student, AttendancePolicy attendance, ExtraPointsPolicy extra) {

        // Validación RNF01: Máximo 10 evaluaciones
        if (student.getEvaluations().size() > 10) {
            throw new IllegalArgumentException("Máximo 10 evaluaciones permitidas.");
        }

        // Validación: Al menos 1 evaluación
        if (student.getEvaluations().isEmpty()) {
            throw new IllegalArgumentException("El estudiante debe tener al menos una evaluación.");
        }

        // Paso 1: Calcular promedio ponderado
        double weightedSum = 0;
        double totalWeight = 0;

        for (Evaluation e : student.getEvaluations()) {
            weightedSum += e.getGrade() * e.getWeight();
            totalWeight += e.getWeight();
        }

        // Validación: Los pesos deben sumar un valor razonable
        if (totalWeight == 0) {
            throw new IllegalArgumentException("La suma de los pesos no puede ser 0.");
        }

        double partialGrade = weightedSum / totalWeight;

        // Paso 2: Aplicar política de asistencia
        partialGrade = attendance.applyAttendance(student, partialGrade);

        // Paso 3: Aplicar política de puntos extra
        partialGrade = extra.applyExtraPoints(student, partialGrade);

        return partialGrade;
    }

    /**
     * Muestra el detalle del cálculo (RF05).
     * @param student El estudiante
     * @param attendance Política de asistencia
     * @param extra Política de puntos extra
     */
    public void showCalculationDetail(Student student, AttendancePolicy attendance, ExtraPointsPolicy extra) {
        
        // Calcular promedio ponderado
        double weightedSum = 0;
        double totalWeight = 0;

        for (Evaluation e : student.getEvaluations()) {
            weightedSum += e.getGrade() * e.getWeight();
            totalWeight += e.getWeight();
        }

        double weightedAverage = weightedSum / totalWeight;
        System.out.println("Promedio ponderado: " + String.format("%.2f", weightedAverage));

        // Mostrar penalización por asistencia
        double penalty = attendance.getPenalty(student, weightedAverage);
        System.out.println("Penalización por inasistencia: -" + String.format("%.2f", penalty));

        // Mostrar puntos extra
        double partialAfterAttendance = attendance.applyAttendance(student, weightedAverage);
        double extraPoints = extra.getExtraPointsApplied(student, partialAfterAttendance);
        System.out.println("Puntos extra aplicados: +" + String.format("%.2f", extraPoints));
    }
}