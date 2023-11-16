public class Node {
    char symbol;
    int freq;
    Node left = null, right = null;

    Node(char symbol_, int freq_) {
        symbol = symbol_;
        freq = freq_;
    }

    Node(char symbol_, int freq_, Node left_, Node right_) {
        symbol = symbol_;
        freq = freq_;
        left = left_;
        right = right_;
    }
}
