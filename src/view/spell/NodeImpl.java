package view.spell;

/**
 * Created by Steven on 1/9/14.
 */

public class NodeImpl implements Trie.Node {

    Trie.Node[] nodes;
    int value;

    public NodeImpl() {
        nodes = new NodeImpl[26];
        value = 0;
    }

    @Override
    public int getValue() {
        return value;
    }
}
