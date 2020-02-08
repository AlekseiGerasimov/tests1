public class Application {
    public static void main(String[] args) {
        String source = "раз,2, три,\n" +
                "елочка, гори!\n" +
                "три, 2, раз...\n" +
                "лысый дикобраз\n" +
                "***\n" +
                "\n";
        Parser parser = new Parser(source);
        parser.parse();
        System.out.println(parser);
    }
}
