package com.samsung.buffer;

import com.samsung.ssd.CmdData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.samsung.ssd.CommandType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BufferProcessorTest {
    @Test
    @DisplayName("Test 1: Write is ignored by following erase")
    void test_1() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(WRITE, 5, "0xAAAAAAAA"));
        processor.process(new CmdData(ERASE, 5, "1"));
        String result = processor.process(new CmdData(READ, 5, null));
        assertEquals("0x00000000", result);
    }

    @Test
    @DisplayName("Test 2: Previous erase is ignored by overlapping erase")
    void test_2() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(ERASE, 8, "2"));
        processor.process(new CmdData(ERASE, 7, "4"));
        String result = processor.process(new CmdData(READ, 8, null));
        assertEquals("0x00000000", result);
    }

    @Test
    @DisplayName("Test 3: Write erased by later erase")
    void test_3() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(WRITE, 10, "0x0000AAAA"));
        processor.process(new CmdData(ERASE, 9, "3"));
        String result = processor.process(new CmdData(READ, 10, null));
        assertEquals("0x00000000", result);
    }

    @Test
    @DisplayName("Test 4: Write not affected by erase")
    void test_4() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(WRITE, 10, "0x0000AAAA"));
        processor.process(new CmdData(ERASE, 9, "1"));
        String result = processor.process(new CmdData(READ, 10, null));
        assertEquals("0x0000AAAA", result);
    }

    @Test
    @DisplayName("Test 5: Erase before write, write survives")
    void test_5() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(ERASE, 0, "1"));
        processor.process(new CmdData(WRITE, 0, "0x0000ABCD"));
        String result = processor.process(new CmdData(READ, 0, null));
        assertEquals("0x0000ABCD", result);
    }

    @Test
    @DisplayName("Test 6: Merge two erases into one")
    void test_6() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(ERASE, 0, "5"));
        processor.process(new CmdData(ERASE, 5, "5"));
        String result = processor.process(new CmdData(READ, 4, null));
        assertEquals("0x00000000", result);
    }

    @Test
    @DisplayName("Test 7: Merge with split for >10 range")
    void test_7() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(ERASE, 0, "5"));
        processor.process(new CmdData(ERASE, 5, "6"));
        String result = processor.process(new CmdData(READ, 10, null));
        assertEquals("0x00000000", result);
    }

    @Test
    @DisplayName("Test 8: Separate erases, no merge")
    void test_8() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(ERASE, 0, "3"));
        processor.process(new CmdData(ERASE, 4, "3"));
        String result = processor.process(new CmdData(READ, 4, null));
        assertEquals("0x00000000", result);
    }

    @Test
    @DisplayName("Test 9: Boundary touch merge")
    void test_9() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(ERASE, 0, "3"));
        processor.process(new CmdData(ERASE, 3, "1"));
        String result = processor.process(new CmdData(READ, 3, null));
        assertEquals("0x00000000", result);
    }

    @Test
    @DisplayName("Test 10: Overlapping erases merge")
    void test_10() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(ERASE, 1, "5"));
        processor.process(new CmdData(ERASE, 3, "2"));
        String result = processor.process(new CmdData(READ, 4, null));
        assertEquals("0x00000000", result);
    }

    @Test
    @DisplayName("Test 11: Erase affects write")
    void test_11() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(WRITE, 5, "0x00001111"));
        processor.process(new CmdData(ERASE, 4, "3"));
        String result = processor.process(new CmdData(READ, 5, null));
        assertEquals("0x00000000", result);
    }

    @Test
    @DisplayName("Test 12: Simple write then read")
    void test_12() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(WRITE, 5, "0x00001111"));
        String result = processor.process(new CmdData(READ, 5, null));
        assertEquals("0x00001111", result);
    }

    @Test
    @DisplayName("Test 13: Direct erase then read")
    void test_13() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(ERASE, 4, "1"));
        String result = processor.process(new CmdData(READ, 4, null));
        assertEquals("0x00000000", result);
    }

    @Test
    @DisplayName("Test 14: Overwrite write")
    void test_14() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(WRITE, 4, "0x0000ABAB"));
        processor.process(new CmdData(WRITE, 4, "0x0000CDCD"));
        String result = processor.process(new CmdData(READ, 4, null));
        assertEquals("0x0000CDCD", result);
    }

    @Test
    @DisplayName("Test 15: Write after erase")
    void test_15() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(WRITE, 3, "0x00000AAA"));
        processor.process(new CmdData(ERASE, 2, "2"));
        processor.process(new CmdData(WRITE, 3, "0x00000BBB"));
        String result = processor.process(new CmdData(READ, 3, null));
        assertEquals("0x00000BBB", result);
    }

    @Test
    @DisplayName("Test 16: Write after erase survives")
    void test_16() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(WRITE, 1, "0x00000001"));
        processor.process(new CmdData(WRITE, 2, "0x00000002"));
        processor.process(new CmdData(ERASE, 1, "2"));
        processor.process(new CmdData(WRITE, 1, "0x00000003"));
        String result = processor.process(new CmdData(READ, 1, null));
        assertEquals("0x00000003", result);
    }

    @Test
    @DisplayName("Test 17: Write after erase unaffected")
    void test_17() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(ERASE, 0, "5"));
        processor.process(new CmdData(WRITE, 2, "0x00002222"));
        String result = processor.process(new CmdData(READ, 2, null));
        assertEquals("0x00002222", result);
    }

    @Test
    @DisplayName("Test 18: Early write erased")
    void test_18() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(WRITE, 0, "0x0000AAAA"));
        processor.process(new CmdData(ERASE, 0, "5"));
        processor.process(new CmdData(WRITE, 2, "0x0000CCCC"));
        String result = processor.process(new CmdData(READ, 0, null));
        assertEquals("0x00000000", result);
    }

    @Test
    @DisplayName("Test 19: Duplicate erase")
    void test_19() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(WRITE, 0, "0x00001111"));
        processor.process(new CmdData(WRITE, 1, "0x00002222"));
        processor.process(new CmdData(ERASE, 0, "2"));
        processor.process(new CmdData(ERASE, 0, "2"));
        String result = processor.process(new CmdData(READ, 1, null));
        assertEquals("0x00000000", result);
    }

    @Test
    @DisplayName("Test 20: Multiple merges")
    void test_20() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(ERASE, 0, "3"));
        processor.process(new CmdData(ERASE, 3, "3"));
        processor.process(new CmdData(ERASE, 6, "3"));
        String result = processor.process(new CmdData(READ, 4, null));
        assertEquals("0x00000000", result);
    }

    @Test
    @DisplayName("Test 21: Merge across boundary")
    void test_21() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(ERASE, 0, "10"));
        processor.process(new CmdData(ERASE, 10, "1"));
        String result = processor.process(new CmdData(READ, 10, null));
        assertEquals("0x00000000", result);
    }

    @Test
    @DisplayName("Test 22: Contained erase overlap")
    void test_22() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(ERASE, 0, "9"));
        processor.process(new CmdData(ERASE, 5, "2"));
        String result = processor.process(new CmdData(READ, 6, null));
        assertEquals("0x00000000", result);
    }

    @Test
    @DisplayName("Test 23: Full overlap")
    void test_23() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(ERASE, 0, "10"));
        processor.process(new CmdData(ERASE, 5, "5"));
        String result = processor.process(new CmdData(READ, 6, null));
        assertEquals("0x00000000", result);
    }

    @Test
    @DisplayName("Test 24: Gap between erases")
    void test_24() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(ERASE, 0, "5"));
        processor.process(new CmdData(ERASE, 6, "4"));
        String result = processor.process(new CmdData(READ, 6, null));
        assertEquals("0x00000000", result);
    }

    @Test
    @DisplayName("Test 25: No merge on 1 gap")
    void test_25() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(ERASE, 0, "3"));
        processor.process(new CmdData(ERASE, 4, "3"));
        String result = processor.process(new CmdData(READ, 4, null));
        assertEquals("0x00000000", result);
    }

    @Test
    @DisplayName("Test 26: Erase removes both writes")
    void test_26() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(WRITE, 1, "0x00000AAA"));
        processor.process(new CmdData(WRITE, 2, "0x00000BBB"));
        processor.process(new CmdData(ERASE, 1, "1"));
        processor.process(new CmdData(ERASE, 2, "1"));
        String result = processor.process(new CmdData(READ, 1, null));
        assertEquals("0x00000000", result);
    }

    @Test
    @DisplayName("Test 27: Write after erase not affected")
    void test_27() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(ERASE, 0, "3"));
        processor.process(new CmdData(WRITE, 1, "0x00000AAA"));
        String result = processor.process(new CmdData(READ, 1, null));
        assertEquals("0x00000AAA", result);
    }

    @Test
    @DisplayName("Test 28: Non-merge backward")
    void test_28() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(ERASE, 5, "1"));
        processor.process(new CmdData(ERASE, 4, "1"));
        String result = processor.process(new CmdData(READ, 5, null));
        assertEquals("0x00000000", result);
    }

    @Test
    @DisplayName("Test 29: Write overwrite test")
    void test_29() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(WRITE, 9, "0x0000DEAD"));
        processor.process(new CmdData(WRITE, 9, "0x0000BEEF"));
        String result = processor.process(new CmdData(READ, 9, null));
        assertEquals("0x0000BEEF", result);
    }

    @Test
    @DisplayName("Test 30: Earlier erase shadowed by later")
    void test_30() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(ERASE, 1, "5"));
        processor.process(new CmdData(ERASE, 0, "6"));
        String result = processor.process(new CmdData(READ, 1, null));
        assertEquals("0x00000000", result);
    }

    @Test
    @DisplayName("Test 31: Read empty block")
    void test_31() {
        BufferProcessor processor = new BufferProcessor();
        String result = processor.process(new CmdData(READ, 1, null));
        assertEquals("0x00000000", result);
        result = processor.process(new CmdData(READ, 5, null));
        assertEquals("0x00000000", result);
        result = processor.process(new CmdData(READ, 8, null));
        assertEquals("0x00000000", result);
    }

    @Test
    @DisplayName("Test 32: Buffer Verification Test 1: Ignore previous erase when overlapped")
    void bufferVerificationTest_1() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(ERASE, 1, "5"));
        processor.process(new CmdData(ERASE, 0, "6"));

        List<CmdData> expected = List.of(
                new CmdData(ERASE, 0, "6")
        );

        assertEquals(expected.size(), processor.getBuffer().size());
        for (int i = 0; i < expected.size(); i++) {
            CmdData e = expected.get(i);
            CmdData a = processor.getBuffer().get(i);
            assertEquals(e.getCommand(), a.getCommand());
            assertEquals(e.getLba(), a.getLba());
            assertEquals(e.getValue(), a.getValue());
        }
    }

    @Test
    @DisplayName("Test 33: Buffer Verification Test 2: Write ignored by erase")
    void bufferVerificationTest_2() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(WRITE, 2, "0xAAAA"));
        processor.process(new CmdData(ERASE, 2, "1"));

        List<CmdData> expected = List.of(
                new CmdData(ERASE, 2, "1")
        );

        assertEquals(expected.size(), processor.getBuffer().size());
        for (int i = 0; i < expected.size(); i++) {
            CmdData e = expected.get(i);
            CmdData a = processor.getBuffer().get(i);
            assertEquals(e.getCommand(), a.getCommand());
            assertEquals(e.getLba(), a.getLba());
            assertEquals(e.getValue(), a.getValue());
        }
    }

    @Test
    @DisplayName("Test 34: Buffer Verification Test 3: Multiple writes ignored by erase")
    void bufferVerificationTest_3() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(WRITE, 2, "0xAAAA"));
        processor.process(new CmdData(WRITE, 3, "0xBBBB"));
        processor.process(new CmdData(ERASE, 1, "4"));

        List<CmdData> expected = List.of(
                new CmdData(ERASE, 1, "4")
        );

        assertEquals(expected.size(), processor.getBuffer().size());
        for (int i = 0; i < expected.size(); i++) {
            CmdData e = expected.get(i);
            CmdData a = processor.getBuffer().get(i);
            assertEquals(e.getCommand(), a.getCommand());
            assertEquals(e.getLba(), a.getLba());
            assertEquals(e.getValue(), a.getValue());
        }
    }

    @Test
    @DisplayName("Test 35: Buffer Verification Test 4: Merge two adjacent erases")
    void bufferVerificationTest_4() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(ERASE, 0, "4"));
        processor.process(new CmdData(ERASE, 4, "3"));

        List<CmdData> expected = List.of(
                new CmdData(ERASE, 0, "7")
        );

        assertEquals(expected.size(), processor.getBuffer().size());
        for (int i = 0; i < expected.size(); i++) {
            CmdData e = expected.get(i);
            CmdData a = processor.getBuffer().get(i);
            assertEquals(e.getCommand(), a.getCommand());
            assertEquals(e.getLba(), a.getLba());
            assertEquals(e.getValue(), a.getValue());
        }
    }

    @Test
    @DisplayName("Test 36: Buffer Verification Test 5: Merged erase exceeds 10, split required")
    void bufferVerificationTest_5() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(ERASE, 0, "10"));
        processor.process(new CmdData(ERASE, 10, "5"));

        List<CmdData> expected = List.of(
                new CmdData(ERASE, 0, "10"),
                new CmdData(ERASE, 10, "5")
        );

        assertEquals(expected.size(), processor.getBuffer().size());
        for (int i = 0; i < expected.size(); i++) {
            CmdData e = expected.get(i);
            CmdData a = processor.getBuffer().get(i);
            assertEquals(e.getCommand(), a.getCommand());
            assertEquals(e.getLba(), a.getLba());
            assertEquals(e.getValue(), a.getValue());
        }
    }

    @Test
    @DisplayName("Test 37: Buffer Verification Test 6: Non-overlapping erases remain separate")
    void bufferVerificationTest_6() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(ERASE, 0, "3"));
        processor.process(new CmdData(ERASE, 5, "2"));

        List<CmdData> expected = List.of(
                new CmdData(ERASE, 0, "3"),
                new CmdData(ERASE, 5, "2")
        );

        assertEquals(expected.size(), processor.getBuffer().size());
        for (int i = 0; i < expected.size(); i++) {
            CmdData e = expected.get(i);
            CmdData a = processor.getBuffer().get(i);
            assertEquals(e.getCommand(), a.getCommand());
            assertEquals(e.getLba(), a.getLba());
            assertEquals(e.getValue(), a.getValue());
        }
    }

    @Test
    @DisplayName("Test 38: Buffer Verification Test 7: Boundary touching erases are merged")
    void bufferVerificationTest_7() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(ERASE, 0, "3"));
        processor.process(new CmdData(ERASE, 3, "1"));

        List<CmdData> expected = List.of(
                new CmdData(ERASE, 0, "4")
        );

        assertEquals(expected.size(), processor.getBuffer().size());
        for (int i = 0; i < expected.size(); i++) {
            CmdData e = expected.get(i);
            CmdData a = processor.getBuffer().get(i);
            assertEquals(e.getCommand(), a.getCommand());
            assertEquals(e.getLba(), a.getLba());
            assertEquals(e.getValue(), a.getValue());
        }
    }

    @Test
    @DisplayName("Test 39: Buffer Verification Test 8: Erase followed by write survives")
    void bufferVerificationTest_8() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(WRITE, 5, "0xAAAA"));
        processor.process(new CmdData(WRITE, 6, "0xBBBB"));
        processor.process(new CmdData(ERASE, 5, "2"));
        processor.process(new CmdData(WRITE, 5, "0xCCCC"));

        List<CmdData> expected = List.of(
                new CmdData(ERASE, 5, "2"),
                new CmdData(WRITE, 5, "0xCCCC")
        );

        assertEquals(expected.size(), processor.getBuffer().size());
        for (int i = 0; i < expected.size(); i++) {
            CmdData e = expected.get(i);
            CmdData a = processor.getBuffer().get(i);
            assertEquals(e.getCommand(), a.getCommand());
            assertEquals(e.getLba(), a.getLba());
            assertEquals(e.getValue(), a.getValue());
        }
    }

    @Test
    @DisplayName("Test 40: Buffer Verification Test 9: Intermediate write ignored by second erase")
    void bufferVerificationTest_9() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(ERASE, 1, "2"));
        processor.process(new CmdData(WRITE, 2, "0x1234"));
        processor.process(new CmdData(ERASE, 2, "1"));

        List<CmdData> expected = List.of(
                new CmdData(ERASE, 1, "2")
        );

        assertEquals(expected.size(), processor.getBuffer().size());
        for (int i = 0; i < expected.size(); i++) {
            CmdData e = expected.get(i);
            CmdData a = processor.getBuffer().get(i);
            assertEquals(e.getCommand(), a.getCommand());
            assertEquals(e.getLba(), a.getLba());
            assertEquals(e.getValue(), a.getValue());
        }
    }

    @Test
    @DisplayName("Test 41: Buffer Verification Test 10: Contained erase is ignored")
    void bufferVerificationTest_10() {
        BufferProcessor processor = new BufferProcessor();
        processor.process(new CmdData(ERASE, 0, "4"));
        processor.process(new CmdData(ERASE, 2, "2"));

        List<CmdData> expected = List.of(
                new CmdData(ERASE, 0, "4")
        );

        assertEquals(expected.size(), processor.getBuffer().size());
        for (int i = 0; i < expected.size(); i++) {
            CmdData e = expected.get(i);
            CmdData a = processor.getBuffer().get(i);
            assertEquals(e.getCommand(), a.getCommand());
            assertEquals(e.getLba(), a.getLba());
            assertEquals(e.getValue(), a.getValue());
        }
    }
}