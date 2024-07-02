fun <T> useInput(block: (Sequence<String>) -> T) =
    StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
    .callerClass
    .getResourceAsStream("input.txt")
    .bufferedReader()
    .useLines(block)

fun <T> id(t: T) = t