package be.kuleuven.adv

// note that these implementations generate four different class files: NameProvider, NickNameProvider, PoshNameProvider, and PropertiesKt
// see build/classes/kotlin

// interfaces can hold properties. There are just getter method definitions
// interfaces can hold default implementations. (JDK 8+, this is also legal in Java)
interface NameProvider {
    val name: String
    val email: String
        get() {
            return "$name@hotmail.com"
        }
}

// We have to provide the "getName()" method, but we're simply creating a backing field here
class NickNameProvider(override val name: String) : NameProvider

// Alternatively, implement the getter.
class PoshNameProvider() : NameProvider {
    override val name: String
        get() = "Prof. Dr. Genius"
}

fun main(args: Array<String>) {
    val myProf = PoshNameProvider()
    val me = NickNameProvider("Exterminator 2000")

    println("Reach me at ${me.email} - following a lecture of ${myProf.name}")
}

// The decompiled NameProvider will look like this:
/*
public interface NameProvider
{
    @NotNull
    String getName();

    @NotNull
    String getEmail();

    @Metadata(mv = { 1, 5, 1 }, k = 3, xi = 48)
    public static final class DefaultImpls
    {
        @NotNull
        public static String getEmail(@NotNull final NameProvider this) {
            Intrinsics.checkNotNullParameter((Object)this, "this");
            return Intrinsics.stringPlus(this.getName(), (Object)"@hotmail.com");
        }
    }
}
 */