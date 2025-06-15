package com.samsung.buffer.handler;



import com.samsung.common.CmdData;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import static com.samsung.common.CommandType.ERASE;
import static com.samsung.common.CommandType.WRITE;
import static com.samsung.common.SSDConstants.MAX_ERASE_SIZE;


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

        for (int i = start; i <= end; i++) {
            memory.put(i, "0x00000000");
        }

        buffer.add(cmd);
        mergeAdjacentErases();
        return "void";
    }

    private void mergeAdjacentErases() {
        List<CmdData> merged = new ArrayList<>();
        ListIterator<CmdData> iter = buffer.listIterator();

        while (iter.hasNext()) {
            CmdData current = iter.next();
            if (!current.getCommand().equals(ERASE)) {
                merged.add(current);
                continue;
            }

            int start = current.getLba();
            int end = start + Integer.parseInt(current.getValue()) - 1;

            while (iter.hasNext()) {
                CmdData next = iter.next();
                if (!next.getCommand().equals(ERASE)) {
                    iter.previous(); // rollback
                    break;
                }

                int nextStart = next.getLba();
                int nextEnd = nextStart + Integer.parseInt(next.getValue()) - 1;

                if (nextStart <= end + 1) {
                    end = Math.max(end, nextEnd);
                } else {
                    iter.previous(); // rollback
                    break;
                }
            }

            for (int s = start; s <= end; s += MAX_ERASE_SIZE) {
                int len = Math.min(MAX_ERASE_SIZE, end - s + 1);
                merged.add(new CmdData(ERASE, s, String.valueOf(len)));
            }
        }

        buffer.clear();
        buffer.addAll(merged);
    }
}
