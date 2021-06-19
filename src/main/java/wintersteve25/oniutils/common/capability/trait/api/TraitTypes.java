package wintersteve25.oniutils.common.capability.trait.api;

public final class TraitTypes {
    public static final int Twinkletoes = 1;
    public static final int Buff = 2;
    public static final int MoleHands = 3;
    public static final int GreaseMonkey = 4;
    public static final int QuickLearner = 5;
    public static final int InteriorDecorator = 6;
    public static final int Caregiver = 7;
    public static final int EarlyBird = 8;
    public static final int NightOwl = 9;
    public static final int Uncultured = 0;
    public static final int Anemic = -1;
    public static final int NoodleArms = -2;
    public static final int SlowLearner = -3;
    public static final int Yokel = -4;
    public static final int Pacifist = -5;
    public static final int Gastrophobia = -6;
    public static final int Squeamish = -7;
    public static final int IrritableBowel = -8;
    public static final int SmallBladder = -9;
    public static final int Biohazard = -10;
    public static final int BottomlessStomach = -11;
    public static final int MouthBreather = -12;
    public static final int Narcoleptic = -13;
    public static final int LoudSleeper = -14;
    public static final int Flatulence = -15;

    public static int getBottom() {
        return Flatulence;
    }

    public static int getTop() {
        return NightOwl;
    }

    public static int getGoodBottom() {
        return Twinkletoes;
    }

    public static int getGoodTop() {
        return NightOwl;
    }

    public static int getBadBottom() {
        return Flatulence;
    }

    public static int getBadTop() {
        return Uncultured;
    }
}
