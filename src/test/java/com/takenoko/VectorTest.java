package com.takenoko;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.takenoko.vector.Vector;
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
}
