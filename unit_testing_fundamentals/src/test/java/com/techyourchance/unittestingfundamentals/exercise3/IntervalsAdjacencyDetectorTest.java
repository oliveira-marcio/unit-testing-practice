package com.techyourchance.unittestingfundamentals.exercise3;

import com.techyourchance.unittestingfundamentals.example3.Interval;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IntervalsAdjacencyDetectorTest {

    IntervalsAdjacencyDetector SUT;

    @Before
    public void setup() throws Exception {
        SUT = new IntervalsAdjacencyDetector();
    }

    // interval1 is before interval2
    @Test
    public void isAdjacent_interval1BeforeInterval2_falseReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(8, 12);
        boolean result = SUT.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }

    // interval1 is before adjacent interval2
    @Test
    public void isAdjacent_interval1BeforeAdjacentInterval2_trueReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(5, 12);
        boolean result = SUT.isAdjacent(interval1, interval2);
        assertThat(result, is(true));
    }

    // interval1 overlaps interval2 on start
    @Test
    public void isAdjacent_interval1OverlapsInterval2OnStart_falseReturned() throws Exception {
        Interval interval1 = new Interval(-1, 8);
        Interval interval2 = new Interval(5, 12);
        boolean result = SUT.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }

    // interval1 contains interval2
    @Test
    public void isAdjacent_interval1ContainsInterval2_falseReturned() throws Exception {
        Interval interval1 = new Interval(-1, 8);
        Interval interval2 = new Interval(2, 6);
        boolean result = SUT.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }

    // interval1 is contained on interval2
    @Test
    public void isAdjacent_interval1IsContainedOnInterval2_falseReturned() throws Exception {
        Interval interval1 = new Interval(6, 11);
        Interval interval2 = new Interval(5, 12);
        boolean result = SUT.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }

    // interval1 is equal to interval2
    @Test
    public void isAdjacent_interval1IsEqualToInterval2_falseReturned() throws Exception {
        Interval interval1 = new Interval(5, 12);
        Interval interval2 = new Interval(5, 12);
        boolean result = SUT.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }

    // interval1 contains interval2 on start
    @Test
    public void isAdjacent_interval1ContainsInterval2OnStart_falseReturned() throws Exception {
        Interval interval1 = new Interval(-1, 8);
        Interval interval2 = new Interval(-1, 6);
        boolean result = SUT.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }

    // interval1 contains interval2 on end
    @Test
    public void isAdjacent_interval1ContainsInterval2OnEnd_falseReturned() throws Exception {
        Interval interval1 = new Interval(-1, 8);
        Interval interval2 = new Interval(3, 8);
        boolean result = SUT.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }

    @Test
    public void isAdjacent_interval1IsContainedOnInterval2Start_falseReturned() throws Exception {
        Interval interval1 = new Interval(-1, 6);
        Interval interval2 = new Interval(-1, 8);
        boolean result = SUT.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }

    // interval1 contains interval2 on end
    @Test
    public void isAdjacent_interval1IsContainedOnInterval2End_falseReturned() throws Exception {
        Interval interval1 = new Interval(3, 8);
        Interval interval2 = new Interval(-1, 8);
        boolean result = SUT.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }

    // interval1 overlaps interval2 on End
    @Test
    public void isAdjacent_interval1OverlapsInterval2OnEnd_falseReturned() throws Exception {
        Interval interval1 = new Interval(9, 15);
        Interval interval2 = new Interval(8, 12);
        boolean result = SUT.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }

    // interval1 is after adjacent interval2
    @Test
    public void isAdjacent_interval1IsAfterAdjacentInterval2_trueReturned() throws Exception {
        Interval interval1 = new Interval(12, 20);
        Interval interval2 = new Interval(5, 12);
        boolean result = SUT.isAdjacent(interval1, interval2);
        assertThat(result, is(true));
    }

    // interval1 is after interval2
    @Test
    public void isAdjacent_interval1IsAfterInterval2_falseReturned() throws Exception {
        Interval interval1 = new Interval(14, 20);
        Interval interval2 = new Interval(5, 12);
        boolean result = SUT.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }
}