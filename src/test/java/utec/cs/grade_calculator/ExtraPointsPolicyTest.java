package utec.cs.grade_calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import utec.cs.grade_calculator.model.Student;
import utec.cs.grade_calculator.policy.ExtraPointsPolicy;

/**
 * Tests unitarios para ExtraPointsPolicy
 */
class ExtraPointsPolicyTest {

    @Test
    void shouldAddExtraPointsWhenEnabled() {
        // Arrange
        Student student = new Student("202012345");
        ExtraPointsPolicy policy = new ExtraPointsPolicy(true);

        // Act
        double result = policy.applyExtraPoints(student, 15.0);

        // Assert
        assertEquals(16.0, result, 0.01);
    }

    @Test
    void shouldNotAddExtraPointsWhenDisabled() {
        // Arrange
        Student student = new Student("202012345");
        ExtraPointsPolicy policy = new ExtraPointsPolicy(false);

        // Act
        double result = policy.applyExtraPoints(student, 15.0);

        // Assert
        assertEquals(15.0, result, 0.01);
    }

    @Test
    void shouldNotExceedMaxGrade() {
        // Arrange
        Student student = new Student("202012345");
        ExtraPointsPolicy policy = new ExtraPointsPolicy(true);

        // Act
        double result = policy.applyExtraPoints(student, 19.8);

        // Assert
        assertEquals(20.0, result, 0.01);
    }

    @Test
    void shouldCalculateCorrectExtraPoints() {
        // Arrange
        Student student = new Student("202012345");
        ExtraPointsPolicy policy = new ExtraPointsPolicy(true);

        // Act
        double extra = policy.getExtraPointsApplied(student, 18.0);

        // Assert
        assertEquals(1.0, extra, 0.01);
    }
}