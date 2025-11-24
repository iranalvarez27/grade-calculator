package utec.cs.grade_calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import utec.cs.grade_calculator.model.Evaluation;
import utec.cs.grade_calculator.model.Student;
import utec.cs.grade_calculator.policy.AttendancePolicy;
import utec.cs.grade_calculator.policy.ExtraPointsPolicy;
import utec.cs.grade_calculator.service.GradeCalculator;

/**
 * Tests unitarios para GradeCalculator
 */
class GradeCalculatorTest {

    @Test
    void shouldCalculateNormalGrade() {
        // Arrange
        Student student = new Student("202012345");
        student.addEvaluation(new Evaluation(15.0, 0.3));
        student.addEvaluation(new Evaluation(18.0, 0.7));
        student.setHasReachedMinimumClasses(true);

        AttendancePolicy attendance = new AttendancePolicy();
        ExtraPointsPolicy extra = new ExtraPointsPolicy(false);
        GradeCalculator calculator = new GradeCalculator();

        // Act
        double result = calculator.calculate(student, attendance, extra);

        // Assert
        assertEquals(17.1, result, 0.01);
    }

    @Test
    void shouldReturnZeroWhenNoAttendance() {
        // Arrange
        Student student = new Student("202012345");
        student.addEvaluation(new Evaluation(18.0, 1.0));
        student.setHasReachedMinimumClasses(false); // No cumple asistencia

        AttendancePolicy attendance = new AttendancePolicy();
        ExtraPointsPolicy extra = new ExtraPointsPolicy(false);
        GradeCalculator calculator = new GradeCalculator();

        // Act
        double result = calculator.calculate(student, attendance, extra);

        // Assert
        assertEquals(0.0, result, 0.01);
    }

    @Test
    void shouldApplyExtraPointsWhenPolicyEnabled() {
        // Arrange
        Student student = new Student("202012345");
        student.addEvaluation(new Evaluation(18.0, 1.0));
        student.setHasReachedMinimumClasses(true);

        AttendancePolicy attendance = new AttendancePolicy();
        ExtraPointsPolicy extra = new ExtraPointsPolicy(true); // Permite extra
        GradeCalculator calculator = new GradeCalculator();

        // Act
        double result = calculator.calculate(student, attendance, extra);

        // Assert
        assertEquals(19.0, result, 0.01);
    }

    @Test
    void shouldNotExceedMaxGrade() {
        // Arrange
        Student student = new Student("202012345");
        student.addEvaluation(new Evaluation(20.0, 1.0));
        student.setHasReachedMinimumClasses(true);

        AttendancePolicy attendance = new AttendancePolicy();
        ExtraPointsPolicy extra = new ExtraPointsPolicy(true);
        GradeCalculator calculator = new GradeCalculator();

        // Act
        double result = calculator.calculate(student, attendance, extra);

        // Assert
        assertEquals(20.0, result, 0.01); // No debe pasar de 20
    }

    @Test
    void shouldThrowWhenMoreThan10Evaluations() {
        // Arrange
        Student student = new Student("202012345");
        
        // Agregar 11 evaluaciones
        for (int i = 0; i < 10; i++) {
            student.addEvaluation(new Evaluation(15.0, 0.1));
        }

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            student.addEvaluation(new Evaluation(15.0, 0.1));
        });
    }

    @Test
    void shouldThrowWhenNoEvaluations() {
        // Arrange
        Student student = new Student("202012345");
        student.setHasReachedMinimumClasses(true);

        AttendancePolicy attendance = new AttendancePolicy();
        ExtraPointsPolicy extra = new ExtraPointsPolicy(false);
        GradeCalculator calculator = new GradeCalculator();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculate(student, attendance, extra);
        });
    }

    @Test
    void shouldThrowWhenWeightSumIsZero() {
        // Arrange
        Student student = new Student("202012345");
        student.addEvaluation(new Evaluation(15.0, 0.0));
        student.setHasReachedMinimumClasses(true);

        AttendancePolicy attendance = new AttendancePolicy();
        ExtraPointsPolicy extra = new ExtraPointsPolicy(false);
        GradeCalculator calculator = new GradeCalculator();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculate(student, attendance, extra);
        });
    }
}