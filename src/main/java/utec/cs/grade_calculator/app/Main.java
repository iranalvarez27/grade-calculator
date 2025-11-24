package utec.cs.grade_calculator.app;

import java.util.Scanner;
import utec.cs.grade_calculator.model.Evaluation;
import utec.cs.grade_calculator.model.Student;
import utec.cs.grade_calculator.policy.AttendancePolicy;
import utec.cs.grade_calculator.policy.ExtraPointsPolicy;
import utec.cs.grade_calculator.service.GradeCalculator;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== CS-GradeCalculator ===");

        // 1. Crear estudiante
        System.out.print("Código del estudiante: ");
        String id = sc.nextLine();
        Student student = new Student(id);

        // 2. Registrar evaluaciones
        System.out.print("Número de evaluaciones (máx 10): ");
        int n = sc.nextInt();

        if (n > 10 || n < 0) {
            System.out.println("Error: Máximo 10 evaluaciones permitidas.");
            sc.close();
            return;
        }

        for (int i = 0; i < n; i++) {
            System.out.print("Nota #" + (i + 1) + " (0-20): ");
            double grade = sc.nextDouble();
            System.out.print("Peso #" + (i + 1) + " (entre 0 y 1): ");
            double weight = sc.nextDouble();

            try {
                student.addEvaluation(new Evaluation(grade, weight));
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                i--; // Reintentar esta evaluación
            }
        }

        // 3. Registrar asistencia
        System.out.print("¿Alcanzó asistencia mínima? (1 = Sí, 0 = No): ");
        boolean attendance = sc.nextInt() == 1;
        student.setHasReachedMinimumClasses(attendance);

        // 4. Política de puntos extra
        System.out.print("¿Se permiten puntos extra este año? (1 = Sí, 0 = No): ");
        boolean allowExtra = sc.nextInt() == 1;

        AttendancePolicy attendancePolicy = new AttendancePolicy();
        ExtraPointsPolicy extraPolicy = new ExtraPointsPolicy(allowExtra);
        GradeCalculator calculator = new GradeCalculator();

        // 5. Calcular nota final
        try {
            double finalGrade = calculator.calculate(student, attendancePolicy, extraPolicy);

            System.out.println("\n=== RESULTADO FINAL ===");
            System.out.println("Estudiante: " + student.getId());
            System.out.println("Nota final: " + String.format("%.2f", finalGrade));
            
            // Mostrar detalle del cálculo (RF05)
            System.out.println("\n=== DETALLE DEL CÁLCULO ===");
            calculator.showCalculationDetail(student, attendancePolicy, extraPolicy);
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error en el cálculo: " + e.getMessage());
        }

        sc.close();
    }
}