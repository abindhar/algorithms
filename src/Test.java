import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Node {
    String sentence;
    int times;
    Node(String s, int i){
        sentence = s;
        times = i;
    }
}

public class Test {
    public static void main(String[] args){
        // Testing space
        System.out.println("Hello");
        List<Node> list = new ArrayList<>();
        list.add(new Node("Hello", 20));
        list.add(new Node("World", 30));
        list.add(new Node(" ", 30));
        list.add(new Node("zzzz", 30));
        // Lambda function to sort using times of occurance
        Collections.sort(list, (a, b) -> {
            if (a.times==b.times){
                return a.sentence.compareTo(b.sentence);
            } else {
                return b.times-a.times;
            }
        });
        // Print top 3
        List<String> ans = new ArrayList<>();
        for (int i=0; i<Math.min(list.size(), 3); i++){
            ans.add(list.get(i).sentence);
        }
        System.out.println(ans);
    }
}
