//JVM ищет точку входа в программу, т.е. main метод
public class JvmComprehension {
    //Создается фрейм с названием метода в области памяти StackMemory и загружает class JvmComprehension в область памяти метаданные.
    //В этот фрей помещается помещается String[] args
    public static void main(String[] args) {
        //В созданный фрейм помещается тип, переменная и ее значение
        int i = 1;                      // 1
        //JVM передает запрос в ClassLoading на инициализацию класса Object и загрузки класса Object в область памяти "Metaspace".
        // Для этого, ClassLoading начинает поиск класса внутри себя направляя запрос Application ClassLoader. Application ClassLoader передает
        // запрос в Bootstrap ClassLoader через звено Platform ClassLoader. Bootstrap ClassLoader загружает класс Object в область
        // памяти Metaspace предварительно проведя Linking и Initialization. Создается объект в куче и помещает во фрейм main ссылку на объект в куче.
        // ... "о" как GCROOTS (связь с 1-м объектом)->Survivor0
        Object o = new Object();        // 2
        //В созданный фрейм помещается тип, переменная и ее значение
        // ... "ii" как GCROOTS (связь с 1-м объектом)->Survivor0
        Integer ii = 2;                 // 3
        //JVM ищет метод в программе и переходит на него
        printAll(o, i, ii);             // 4
        //Создается новый фрейм
        //создается новый экземпляр объекта
        // Во фрейм помещается ссылка со значением на созданный экземпляр объекта.
        // ... "finished" как GCROOTS (связь с 1-м объектом)->Survivor0
        System.out.println("finished"); // 7
    }

    //Создается новый фрейм в Stack Memory в который помещается три переменные. Так как класс Object ранее был инициализирован и лежит в Metaspace,
    // ClassLoading не запускается. Во фрейме создается ссылка на ранее созданный объект в куче, который мы передаем. Так же создается Объект Integer
    // и во фрейм помещается тип, переменная второго аргумента и ссылка на Integer. Так же создается указатель куда вернуться при выходе из этого метода.
    // ... "о" как GCROOTS (связь с 1-м объектом)->Survivor1 ... "ii" как GCROOTS (связь с 1-м объектом)->Survivor1
    private static void printAll(Object o, int i, Integer ii) {
        //В куче создается еще один объект в куче, а так же ссылка на объект и значение в Stack Memory
        // ... "userlessVar" как GCROOTS (связь с 1-м объектом)->Survivor0
        Integer uselessVar = 700;                   // 5
        //Так как класс System системный, то он уже есть в Metaspace. Создается новый фрейм printLn в который помещаются ссылки i, ii, o,
        // ссылающиеся на ранее созданные объекты в куче. о создает еще один объект в куче.
        // JVM выполняет метод и возращается обратно по ссылке выданной при переходе в метод.
        //"о" как GCROOTS (связь с 1-м объектом)->Survivor1 ... "ii" как GCROOTS (связь с 1-м объектом)->Survivor1
        System.out.println(o.toString() + i + ii);  // 6
    }
}
