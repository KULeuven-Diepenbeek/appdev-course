package be.kuleuven.adv

fun main(args: Array<String>) {
    println("Starting a thread that ... well... does something. Yeah.")

    // note the decompiled version down here uses new Thread(LambdasKT::main$lambda-0)
    // this assumes JDK8 compliancy!
    val t = Thread {
        for (i in 1..10)
            println("hey for the ${i}th time")
    }

    t.run()
    t.join()

    println("Nice eh, we didn't need to create a Runnable implementation?")
}

/*
JDK8+ source:

@Metadata(mv = { 1, 5, 1 }, k = 2, xi = 48, d1 = { "\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0019\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005¨\u0006\u0006" }, d2 = { "main", "", "args", "", "", "([Ljava/lang/String;)V", "advanced-snippets" })
public final class LambdasKt
{
    public static final void main(@NotNull final String[] args) {
        Intrinsics.checkNotNullParameter((Object)args, "args");
        System.out.println((Object)"Starting a thread that ... well... does something. Yeah.");
        final Thread t = new Thread(LambdasKt::main$lambda-0);
        t.run();
        t.join();
        System.out.println((Object)"Nice eh, we didn't need to create a Runnable implementation?");
    }

    private static final void main$lambda-0() {
        int j = 1;
        do {
            final int i = j;
            ++j;
            System.out.println((Object)("hey for the " + i + "th time"));
        } while (j <= 10);
    }
}

JDK6+ source:

(see setup in build.gradle.kts)
@Metadata(mv = { 1, 5, 1 }, k = 2, xi = 48, d1 = { "\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0019\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005¨\u0006\u0006" }, d2 = { "main", "", "args", "", "", "([Ljava/lang/String;)V", "advanced-snippets" })
public final class LambdasKt
{
    public static final void main(@NotNull final String[] args) {
        Intrinsics.checkNotNullParameter((Object)args, "args");
        System.out.println((Object)"Starting a thread that ... well... does something. Yeah.");
        final Thread t = new Thread((Runnable)LambdasKt$main$t.LambdasKt$main$t$1.INSTANCE);
        t.run();
        t.join();
        System.out.println((Object)"Nice eh, we didn't need to create a Runnable implementation?");
    }
}

@Metadata(mv = { 1, 5, 1 }, k = 3, xi = 48, d1 = { "\u0000\u0006\n\u0000\n\u0002\u0010\u0002\u0010\u0000\u001a\u00020\u0001H\n" }, d2 = { "<anonymous>", "" })
static final class LambdasKt$main$t$1 implements Runnable {
    public static final LambdasKt$main$t$1 INSTANCE;

    @Override
    public final void run() {
        int j = 1;
        do {
            final int i = j;
            ++j;
            System.out.println((Object)("hey for the " + i + "th time"));
        } while (j <= 10);
    }

    static {
        LambdasKt$main$t$1.INSTANCE = new LambdasKt$main$t$1();
    }
}
 */
