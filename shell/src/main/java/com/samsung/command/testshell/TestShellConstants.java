package com.samsung.command.testshell;

public class TestShellConstants {
    public static String[] HELP_MESSAGE = new String[]{
            "제작자",
            "팀명: DeviceSolution",
            "팀원: 김영식, 박준경, 권희정, 권성민, 이상훈, 오시훈, 추준성",
            "",
            "명령어",
            "  write [LBA] [Value]     지정된 index에 value를 기록합니다. 예: write 3 0xAAAABBBB",
            "  read [LBA]              지정된 index의 값을 읽어옵니다. 예: read 3",
            "  erase [LBA] [Length]    지정된 LBA 부터 Length 길이만큼을 SSD에서 삭제합니다. 예 : erase 0 10",
            "  erase_range [LBA1] [LBA2]    지정된 범위의 데이터를 SSD에서 삭제합니다. 예 : erase_range 10 20",
            "  fullwrite  [Value]         전체 영역에 value를 기록합니다. 예: fullwrite 0xAAAABBBB",
            "  fullread                  전체 영역을 읽어옵니다.",
            "  flush                     SSD 버퍼를 비웁니다. 예 : flush",
            "  help                      사용 가능한 명령어를 출력합니다.",
            "  exit                      프로그램을 종료합니다.",
            "Copyright (c) 2025 DeviceSolution. All rights reserved.",
            "" };

    public static final int MAX_FILE_LENGTH = 100;
}
