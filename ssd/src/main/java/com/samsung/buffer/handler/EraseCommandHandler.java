package com.samsung.buffer.handler;


import com.samsung.CmdData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.samsung.CommandType.*;

public class EraseCommandHandler implements CommandHandler {
    private final List<CmdData> buffer;
    private final Map<Integer, String> memory;

    public EraseCommandHandler(List<CmdData> buffer, Map<Integer, String> memory) {
        this.buffer = buffer;
        this.memory = memory;
    }

    @Override
    public String handle(CmdData cmd) {
        int start = cmd.getLba();
        int length = Integer.parseInt(cmd.getValue());
        int end = start + length - 1;

        removeOverlappingCommands(start, end);
        eraseMemoryRange(start, end);

        List<CmdData> merged = mergeErases(getExistingErases(), cmd);
        buffer.removeIf(c -> c.getCommand().equals(ERASE));
        buffer.addAll(merged);

        return "void";
    }

    private void removeOverlappingCommands(int start, int end) {
        buffer.removeIf(prev -> {
            if (prev.getCommand().equals(WRITE)) {
                int lba = prev.getLba();
                return lba >= start && lba <= end;
            }
            if (prev.getCommand().equals(ERASE)) {
                int eStart = prev.getLba();
                int eEnd = eStart + Integer.parseInt(prev.getValue()) - 1;
                return start <= eStart && eEnd <= end;
            }
            return false;
        });
    }

    private void eraseMemoryRange(int start, int end) {
        for (int i = start; i <= end; i++) {
            memory.put(i, "0x00000000");
        }
    }

    private List<CmdData> getExistingErases() {
        List<CmdData> erases = new ArrayList<>();
        for (CmdData c : buffer) {
            if (c.getCommand().equals(ERASE)) {
                erases.add(c);
            }
        }
        return erases;
    }

    private List<CmdData> mergeErases(List<CmdData> erases, CmdData newErase) {
        List<CmdData> result = new ArrayList<>();
        erases.add(newErase);
        erases.sort(Comparator.comparingInt(CmdData::getLba));

        int i = 0;
        while (i < erases.size()) {
            int start = erases.get(i).getLba();
            int end = start + Integer.parseInt(erases.get(i).getValue()) - 1;
            i++;

            while (i < erases.size()) {
                int nextStart = erases.get(i).getLba();
                int nextEnd = nextStart + Integer.parseInt(erases.get(i).getValue()) - 1;
                if (nextStart <= end + 1) {
                    end = Math.max(end, nextEnd);
                    i++;
                } else break;
            }

            for (int s = start; s <= end; s += 10) {
                int len = Math.min(10, end - s + 1);
                result.add(new CmdData(ERASE, s, String.valueOf(len)));
            }
        }
        return result;
    }
}
