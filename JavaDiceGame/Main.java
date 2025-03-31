import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    static Random r = new Random();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // initial Dice Roll.
        List<Integer> diceValues = rollDice(new ArrayList<>(), new ArrayList<>());
        boolean game = true;

        // Game loop.
        while (game) {

            System.out.println("You're dice are: " + diceValues);
            System.out.println("Press Enter to Score");
            System.out.println("Type \"ALL\" to re-roll all the dice.");
            System.out.println("List numbers (separated by spaces) to re-roll selected dice.");

//            String input just to avoid issues.
            String input = scanner.nextLine().trim();

            // Game Over (Score not yet implemented.)
            if (input.isEmpty()) {
                game = false;
                continue;
            }

            // Check for All input, no matter the case formatting.
            if (input.equalsIgnoreCase("all")) {
                // just resetting whole dice board.
                diceValues = rollDice(new ArrayList<>(), new ArrayList<>());
                continue;
            }

            // turn everything into Integers for easy processing.
            List<Integer> processedInput = Arrays.stream(input.split(" "))
                    .map(x -> x.substring(0, 1))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            diceValues = rollDice(processedInput, diceValues);

        }

    }

    public static List<Integer> rollDice(List<Integer> toReroll, List<Integer> toKeep) {

        // First roll
        if (toReroll.isEmpty()) {
            return Stream.generate(() -> r.nextInt(1, 7))
                    .limit(5)
                    .collect(Collectors.toList());
        }

        // Subsequent rolls
        List<Integer> input = new ArrayList<>(toKeep);

        // Iterator to remove only the first matching instance of each number in `toReroll`
        Iterator<Integer> it = input.iterator();
        for (int rerollValue : toReroll) {
            while (it.hasNext()) {
                if (it.next().equals(rerollValue)) {
                    it.remove();
                    break;
                }
            }
        }

        // add dice up to 5
        while (input.size() < 5) {
            input.add(r.nextInt(1, 7));
        }

        return input;
    }

}
//    public static List<Integer> rollDice(List<Integer> toReroll, List<Integer> toKeep){
//
//        // first roll
//        if (toReroll.isEmpty()){
//            return Stream.generate(() -> r.nextInt(1,7))
//                    .limit(5)
//                    .collect(Collectors.toList());
//        }
//
//        // subsequent rolls
//        List<Integer> input = toKeep;
//        // This one is the problem.
//
//        // Problem: Iterator needed.
//
//        for (int rerollI = 0; rerollI < toReroll.size(); rerollI++){
//            for (int keepI = 0; keepI < toKeep.size(); keepI++){
//                if (toKeep.get(keepI).equals(toReroll.get(rerollI))) {
//                    toKeep.remove(keepI);
//                    // Do not remove duplicates unless explicitly stated.
//                    keepI=toKeep.size();
//                }
//            }
//        }
////        toKeep.removeAll(toReroll);
//
//
//
//        for (int i = toKeep.size(); i < 5; i++){
//            input.add(r.nextInt(1,7));
//        }
//
//        return input;
//
//    }
