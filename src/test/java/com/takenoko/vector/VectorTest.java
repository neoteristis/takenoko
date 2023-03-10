package com.takenoko.vector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/** Unit test for Vector class. */
class VectorTest {

    @Nested
    @DisplayName("Method add")
    class TestAdd {
        @Test
        @DisplayName("should add the two vectors")
        void add_shouldAddTheTwoVectors() {
            Vector vector1 = new Vector(1, -2, 1);
            Vector vector2 = new Vector(1, 0, -1);
            Vector expected = new Vector(2, -2, 0);
            assertThat(vector1.add(vector2)).isEqualTo(expected);
            vector1 = new Vector(-1, 2, -1);
            vector2 = new Vector(1, 1, -2);
            expected = new Vector(0, 3, -3);
            assertThat(vector1.add(vector2)).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("Method sub")
    class TestSub {
        @Test
        @DisplayName("should subtract the two vectors")
        void sub_shouldSubtractTheTwoVectors() {
            Vector vector1 = new Vector(1, -2, 1);
            Vector vector2 = new Vector(1, 0, -1);
            Vector expected = new Vector(0, -2, 2);
            assertThat(vector1.sub(vector2)).isEqualTo(expected);
            vector1 = new Vector(-1, 2, -1);
            vector2 = new Vector(1, 1, -2);
            expected = new Vector(-2, 1, 1);
            assertThat(vector1.sub(vector2)).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("Method rotate60")
    class TestRotate60 {
        @Test
        @DisplayName("should rotate the vector by 60 degrees")
        void rotate60_shouldRotateTheVectorBy60Degrees() {
            Vector vector = new Vector(1, -2, 1);
            Vector expected = new Vector(2, -1, -1);
            assertThat(vector.rotate60()).isEqualTo(expected);
            vector = new Vector(-1, 2, -1);
            expected = new Vector(-2, 1, 1);
            assertThat(vector.rotate60()).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("Method length")
    class TestLength {
        @Test
        @DisplayName("should return the length of the vector")
        void length_shouldReturnTheLengthOfTheVector() {
            Vector vector = new Vector(1, -2, 1);
            assertEquals(2, vector.length());
            vector = new Vector(-1, 2, -1);
            assertEquals(2, vector.length());
        }
    }

    @Nested
    @DisplayName("Method distance")
    class TestDistance {
        @Test
        @DisplayName("should return the distance between the two vectors")
        void distance_shouldReturnTheDistanceBetweenTheTwoVectors() {
            Vector vector1 = new Vector(1, -2, 1);
            Vector vector2 = new Vector(1, 0, -1);
            assertEquals(2, vector1.distance(vector2));
            vector1 = new Vector(-1, 2, -1);
            vector2 = new Vector(1, 1, -2);
            assertEquals(2, vector1.distance(vector2));
        }
    }

    @Nested
    @DisplayName("Constructor")
    class TestConstructor {
        @Test
        @DisplayName("should create a vector with the given coordinates")
        void constructor_WhenGivenCorrectCoordinates_CreatesVector() {
            Vector vector = new Vector(1, 2, -3);
            assertEquals(1, vector.q());
            assertEquals(2, vector.r());
            assertEquals(-3, vector.s());
        }

        @Test
        @DisplayName("should throw an exception if the coordinates are not valid")
        void constructor_WhenCoordinatesAreNotValid_ThrowsException() {
            assertThatThrownBy(() -> new Vector(1, 2, 3))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("q + r + s must be 0");
        }
    }

    @Nested
    @DisplayName("Method equals")
    class TestEquals {
        @Test
        @SuppressWarnings("EqualsWithItself")
        @DisplayName("should return true if called on self")
        void equals_WhenCalledOnSelf_ReturnsTrue() {
            Vector vector = new Vector(1, 2, -3);
            assertThat(vector.equals(vector)).isTrue();
        }

        @Test
        @DisplayName("should return false if called on another class")
        void equals_WhenCalledOnAnotherClass_ReturnsFalse() {
            Vector vector = new Vector(1, 2, -3);
            assertThat(vector.equals(new Object())).isFalse();
        }

        @Test
        @DisplayName("should return true if the vectors are equal")
        void equals_WhenVectorsAreEqual_ReturnsTrue() {
            Vector vector1 = new Vector(1, 2, -3);
            Vector vector2 = new Vector(1, 2, -3);
            assertThat(vector1.equals(vector2)).isTrue();
        }

        @Test
        @DisplayName("should return false if the vectors are not equal")
        void equals_WhenVectorsAreNotEqual_ReturnsFalse() {
            Vector vector1 = new Vector(1, 2, -3);
            Vector vector2 = new Vector(1, 3, -4);
            assertThat(vector1.equals(vector2)).isFalse();
        }
    }

    @Nested
    @DisplayName("Hashcode")
    class TestHashcode {
        @Test
        @DisplayName("should return the same hashcode for equal vectors")
        void hashcode_shouldReturnTheSameHashcodeForEqualVectors() {
            Vector vector1 = new Vector(1, 2, -3);
            Vector vector2 = new Vector(1, 2, -3);
            assertThat(vector1).hasSameHashCodeAs(vector2);
        }

        @Test
        @DisplayName("should return a different hashcode for different vectors")
        void hashcode_shouldReturnADifferentHashcodeForDifferentVectors() {
            Vector vector1 = new Vector(1, 2, -3);
            Vector vector2 = new Vector(1, 3, -4);
            assertThat(vector1).doesNotHaveSameHashCodeAs(vector2);
        }
    }

    @Nested
    @DisplayName("Method Multiply")
    class TestMultiply {
        @Test
        @DisplayName("should multiply the vector by the given factor")
        void multiply_shouldMultiplyTheVectorByTheGivenFactor() {
            Vector vector = new Vector(1, 2, -3);
            Vector expected = new Vector(3, 6, -9);
            assertThat(vector.multiply(3)).isEqualTo(expected);
        }

        @Test
        @DisplayName("should return the same vector if the factor is 1")
        void multiply_shouldReturnTheSameVectorIfTheFactorIs1() {
            Vector vector = new Vector(1, 2, -3);
            assertThat(vector.multiply(1)).isEqualTo(vector);
        }

        @Test
        @DisplayName("should return the zero vector if the factor is 0")
        void multiply_shouldReturnTheZeroVectorIfTheFactorIs0() {
            Vector vector = new Vector(1, 2, -3);
            assertThat(vector.multiply(0)).isEqualTo(new Vector(0, 0, 0));
        }
    }

    @Nested
    @DisplayName("Method normalize")
    class TestNormalize {
        @Test
        @DisplayName("should return the normalized vector")
        void normalize_shouldReturnTheNormalizedVector() {
            Vector vector = new Vector(1, 2, -3);
            Vector expected = new Vector(1.0 / 3, 2.0 / 3, -1.0);
            assertThat(vector.normalize()).isEqualTo(expected);
        }
    }
}
