package lab4;

public class Config {


    public static void setModePrint(ModePrint mode) {
        modePrint = mode;
    }

    enum ModePrint {
        WITH_TYPES,
        MATH,
    }

    static ModePrint modePrint = ModePrint.WITH_TYPES;
}
