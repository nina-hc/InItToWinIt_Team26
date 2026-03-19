package catan;

public interface RuleEvaluator {

    double evaluate(Game game);
    void executeStrategy(Game game);

}
