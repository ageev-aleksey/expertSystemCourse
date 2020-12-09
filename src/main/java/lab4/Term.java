package lab4;
interface Term extends Cloneable{
    String name();
    Term clone();

//    static void setModePrint(Mode mode) {
//        modePrint = mode;
//    }
//
//    enum Mode {
//        WITH_TYPES,
//        MATH
//    }
//    Mode modePrint = Mode.WITH_TYPES;
}
