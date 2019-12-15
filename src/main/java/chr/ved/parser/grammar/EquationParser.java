package chr.ved.parser.grammar;

public class EquationParser {
    private static EquationParser instance = null;
    private EquationParser(){}

    public static EquationParser getParser()
    {
        if (instance == null)
            instance = new EquationParser();

        return instance;
    }
}
