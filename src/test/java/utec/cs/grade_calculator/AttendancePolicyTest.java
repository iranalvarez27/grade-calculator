package utec.cs.grade_calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import utec.cs.grade_calculator.model.Student;
import utec.cs.grade_calculator.policy.AttendancePolicy;

/**
 * Tests unitarios para AttendancePolicy
 */
class AttendancePolicyTest {

    @Test
    void shouldReturnSameGradeWhenAttendanceReached() {
        // Arrange
        Student student = new Student("202012345");
        student.setHasReachedMinimumClasses(true);
        AttendancePolicy policy = new AttendancePolicy();

        // Act
        double result = policy.applyAttendance(student, 15.5);

        // Assert
        assertEquals(15.5, result, 0.01);
    }

    @Test
    void shouldReturnZeroWhenAttendanceNotReached() {
        // Arrange
        Student student = new Student("202012345");
        student.setHasReachedMinimumClasses(false);
        AttendancePolicy policy = new AttendancePolicy();

        // Act
        double result = policy.applyAttendance(student, 18.0);

        // Assert
        assertEquals(0.0, result, 0.01);
    }

    @Test
    void shouldCalculateCorrectPenalty() {
        // Arrange
        Student student = new Student("202012345");
        student.setHasReachedMinimumClasses(false);
        AttendancePolicy policy = new AttendancePolicy();

        // Act
        double penalty = policy.getPenalty(student, 16.0);

        // Assert
        assertEquals(16.0, penalty, 0.01);
    }
}