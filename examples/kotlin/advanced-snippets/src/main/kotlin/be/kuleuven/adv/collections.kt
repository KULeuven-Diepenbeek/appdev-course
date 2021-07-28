package be.kuleuven.adv

import java.lang.StringBuilder

fun <T> joinToString(collection: Collection<T>, separator: String = ":", prefix: String = "[", suffix: String = "]"): String {
    val builder = StringBuilder(prefix)
    for((index, element) in collection.withIndex()) {
        if(index > 0) builder.append(separator)
        builder.append(element)
    }
    builder.append(suffix)
    return builder.toString()
}

data class Person(val name: String, val age: Int)

fun main(args: Array<String>) {
    val someCollection = mapOf("Jos" to Person("Jos", 20), "Lowie" to Person("Lowie", 56))

    println(joinToString(someCollection.values))
    println(joinToString(someCollection.values, ", ", prefix = "(", suffix = ")"))
}

// suppose we throw the above into a Java decompiler such as https://github.com/mstrobel/procyon
// what output would we get? Java doesn't have default values, nor "val", nor not-nullable references.
// ----- decopmiler output: ------
/*

//
// Decompiled by Procyon v0.6-prerelease
//

package be.kuleuven.adv;

import java.util.Map;
import kotlin.collections.MapsKt;
import kotlin.TuplesKt;
import kotlin.Pair;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import kotlin.Metadata;

@Metadata(mv = { 1, 5, 1 }, k = 2, xi = 48, d1 = { "\u0000 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\u001a8\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00012\b\b\u0002\u0010\u0006\u001a\u00020\u00012\b\b\u0002\u0010\u0007\u001a\u00020\u0001\u001a\u0019\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b¢\u0006\u0002\u0010\f¨\u0006\r" }, d2 = { "joinToString", "", "T", "collection", "", "separator", "prefix", "suffix", "main", "", "args", "", "([Ljava/lang/String;)V", "advanced-snippets" })
public final class CollectionsKt
{
    @NotNull
    public static final <T> String joinToString(@NotNull final Collection<? extends T> collection, @NotNull final String separator, @NotNull final String prefix, @NotNull final String suffix) {
        Intrinsics.checkNotNullParameter((Object)collection, "collection");
        Intrinsics.checkNotNullParameter((Object)separator, "separator");
        Intrinsics.checkNotNullParameter((Object)prefix, "prefix");
        Intrinsics.checkNotNullParameter((Object)suffix, "suffix");
        final StringBuilder builder = new StringBuilder(prefix);
        final Iterator<? extends T> iterator = collection.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            final int index = n;
            ++n;
            final Object element = iterator.next();
            if (index > 0) {
                builder.append(separator);
            }
            builder.append(element);
        }
        builder.append(suffix);
        final String string = builder.toString();
        Intrinsics.checkNotNullExpressionValue((Object)string, "builder.toString()");
        return string;
    }

    public static /* synthetic */ String joinToString$default(final Collection collection, String separator, String prefix, String suffix, final int n, final Object o) {
        if ((n & 0x2) != 0x0) {
            separator = ":";
        }
        if ((n & 0x4) != 0x0) {
            prefix = "[";
        }
        if ((n & 0x8) != 0x0) {
            suffix = "]";
        }
        return joinToString((Collection<?>)collection, separator, prefix, suffix);
    }

    public static final void main(@NotNull final String[] args) {
        Intrinsics.checkNotNullParameter((Object)args, "args");
        final Map someCollection = MapsKt.mapOf(new Pair[] { TuplesKt.to((Object)"Jos", (Object)new Person("Jos", 20)), TuplesKt.to((Object)"Lowie", (Object)new Person("Lowie", 56)) });
        System.out.println((Object)joinToString$default(someCollection.values(), null, null, null, 14, null));
        System.out.println((Object)joinToString(someCollection.values(), ", ", "(", ")"));
    }
}

*/
